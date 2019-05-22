package com.xs.jt.hwvideo.manager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xs.jt.hwvideo.dao.VehInfoRepository;
import com.xs.jt.hwvideo.entity.VehInfo;
import com.xs.jt.hwvideo.util.HCNetSDK;
import com.xs.jt.hwvideo.util.HCNetSDK.NET_DVR_TIME;
import com.xs.jt.hwvideo.util.HKVisionUtil;
import com.xs.jt.hwvideo.util.PlayUtil;
import com.xs.jt.hwvideo.util.WeightContainer;

@Service
public class WeighManager {
	
	@Autowired
	private WeightContainer weightContainer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HKVisionUtil hkUtil;
	
	@Value("${xs.hwvideo.sc.url}")
	private String url;
	
	@Value("${xs.hwvideo.sc.upload.url}")
	private String uploadUrl;
	
	@Value("${xs.hwvideo.sc.token}")
	private String token;
	
	@Value("${xs.hwvideo.sc.deviceNo}")
	private String deviceNo;
	
	@Autowired
	private PlayUtil playUtil;
	
	@Resource(name="inVehMap")
	private Map<String,JSONObject> inVehMap;
	
	@Resource(name="currentInDatas")
	private JSONArray currentInDatas;
	
	@Autowired
	private VehInfoRepository vehInfoRepository;
	
	public void inVeh(JSONObject jsonObject) {
		String hphm = jsonObject.getString("vehicleNo");
		currentInDatas.clear();
		VehInfo vehInfo=new VehInfo();
		vehInfo.setHphm(hphm);
		vehInfo.setJsonData(jsonObject.toJSONString());
		vehInfo.setStatus(1);
		vehInfo.setJckssj(new Date());
		vehInfo.setBillNo(jsonObject.getString("billNo"));
		vehInfo.setInVideoStatus(0);
		vehInfo.setOutVideoStatus(0);
		vehInfoRepository.save(vehInfo);
	
	}
	
	@Async
	public void outVeh(JSONObject jsonObject) {
		currentInDatas.clear();
		VehInfo vehInfo=vehInfoRepository.findById(jsonObject.getInteger("vehInfoId")).get();
		vehInfo.setStatus(2);
		vehInfoRepository.save(vehInfo);
		
	}
	
	@Async
	public void cccz(VehInfo vehInfo) throws Exception {
		String strData = vehInfo.getJsonData();
		JSONObject jsonObject=JSONObject.parseObject(strData);
		
		String hphm = jsonObject.getString("vehicleNo");
		String billNo =jsonObject.getString("billNo");
		String recordId =getRecordId(hphm,"OUT",billNo);
		playUtil.play(hphm+"，开始称重，请行驶到地磅称重。",2);
		Float weight = cz();
		vehInfo.setOutWeightDate(new Date());
		playUtil.play(hphm+"称重完成，重量"+weight+"公斤，请打开闸机出场。",2);
		vehInfo.setOutRecordId(recordId);
		vehInfo.setStatus(3);
		vehInfo.setOutWeight(weight);
		vehInfo.setCcjssj(new Date());
		vehInfoRepository.save(vehInfo);
		uploadWeigh(vehInfo,"OUT");
	}
	
	
	@Async
	public void jccz(VehInfo vehInfo) throws Exception {
		String strData = vehInfo.getJsonData();
		JSONObject jsonObject=JSONObject.parseObject(strData);
		String hphm = jsonObject.getString("vehicleNo");
		String billNo =jsonObject.getString("billNo");
		String recordId =getRecordId(hphm,"IN",billNo);
		playUtil.play(hphm+"，请行驶到地磅称重。",2);
		Float weight = cz();
		vehInfo.setInWeightDate(new Date());
		playUtil.play(hphm+"称重完成，重量"+weight+"公斤，请打开内闸机进场！",2);
		vehInfo.setInWeight(weight);
		vehInfo.setJcjssj(new Date());
		vehInfo.setInRecordId(recordId);
		vehInfoRepository.save(vehInfo);
		uploadWeigh(vehInfo,"IN");
	}
	
	
	public String getRecordId(String hphm,String type,String billNo) {
		
		JSONObject param=new JSONObject();
		param.put("token", token);
		param.put("msgType", "sd_vw_supplement");
		JSONObject msgBody=new JSONObject();
		msgBody.put("deviceNo",deviceNo);
		msgBody.put("vehicleNo",hphm);
		msgBody.put("type", type);
		msgBody.put("billNo", billNo);
		param.put("msgBody", msgBody);
		JSONObject jo = restTemplate.postForObject(url, param, JSONObject.class);
		
		if(jo.getInteger("status")==1) {
			return jo.getJSONObject("data").getString("recordId");
		}else {
			return null;
		}
	}
	
	
   public void uploadWeigh(VehInfo vehInfo,String type) throws Exception {
		
		JSONObject param=new JSONObject();
		String strData = vehInfo.getJsonData();
		JSONObject jsonData =JSONObject.parseObject(strData);
		param.put("token", token);
		param.put("msgType", "sd_vw_save");
		JSONObject msgBody=new JSONObject();
		msgBody.put("deviceNo",deviceNo);
		msgBody.put("vehicleNo",vehInfo.getHphm());
		msgBody.put("type", type);
		msgBody.put("billNo", jsonData.getString("billNo"));
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("IN".equals(type)) {
			msgBody.put("type",type);
			msgBody.put("weight", vehInfo.getInWeight());
			msgBody.put("weightTime", sdf.format(vehInfo.getInWeightDate()));
			msgBody.put("recordId", vehInfo.getInRecordId());
			
		}
		if("OUT".equals(type)) {
			msgBody.put("type", type);
			msgBody.put("weight", vehInfo.getOutWeight());
			msgBody.put("weightTime", sdf.format(vehInfo.getOutWeightDate()));
			msgBody.put("recordId", vehInfo.getOutRecordId());
		}
		
		param.put("msgBody", msgBody);
		JSONObject jo = restTemplate.postForObject(url, param, JSONObject.class);
		
		if(jo.getInteger("status")==1) {
			return;
		}else {
			playUtil.play("调用二级平台上传接口异常。请联系管理员！",1);
			throw new Exception("调用二级平台上传接口异常。请联系管理员！");
		}
	}
   
   @Async
   public JSONObject uploadFile(String filePath,String recordId) {
	   
	   FileSystemResource resource = new FileSystemResource(new File(filePath+"\\"+recordId+".mp4"));  
	   MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();  
	   param.add("files", resource);
	   param.add("recordId", recordId);
	   JSONObject jo = restTemplate.postForObject(uploadUrl, param, JSONObject.class);
	   return jo;
   }
   
   @Async
   public JSONObject uploadPhoto(String filePath,String recordId) {
	   
	   FileSystemResource resource = new FileSystemResource(new File(filePath+"\\"+recordId+".jpg"));  
	   MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();  
	   param.add("files", resource);
	   param.add("recordId", recordId);
	   JSONObject jo = restTemplate.postForObject(uploadUrl, param, JSONObject.class);
	   return jo;
   }
   
   public void dowloandVideo(String id,Date beginDate,Date endDate) throws Exception {
	   
	   NET_DVR_TIME lpStartTime = new HCNetSDK.NET_DVR_TIME();
	   convert(beginDate, lpStartTime);
	   NET_DVR_TIME lpStopTime = new HCNetSDK.NET_DVR_TIME();
	   convert(endDate, lpStopTime);
	   hkUtil.downLoad(lpStartTime, lpStopTime, id);
	   
   }
   
   
   private void convert(Date date, NET_DVR_TIME lpStartTime) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		lpStartTime.dwYear = now.get(Calendar.YEAR);
		lpStartTime.dwMonth = (now.get(Calendar.MONTH) + 1);
		lpStartTime.dwDay = now.get(Calendar.DAY_OF_MONTH);
		lpStartTime.dwHour = now.get(Calendar.HOUR_OF_DAY);
		lpStartTime.dwMinute = now.get(Calendar.MINUTE);
		lpStartTime.dwSecond = now.get(Calendar.SECOND);
	}
   
   
   public Float cz() throws InterruptedException {
	   while(weightContainer.getWeight()<5) {
		   Thread.sleep(200);
	   }
	   Float f =weightContainer.getWeight();
	   int i=0;
	   while(i<5*5) {
		   Thread.sleep(200);
		   if(Math.abs(f-weightContainer.getWeight())<2) {
			   i++;
		   }else {
			   i=0;
		   }
		   f=weightContainer.getWeight();
	   }
	   return f;
   }
   

}
