package com.xs.jt.hwvideo.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.hwvideo.entity.VehInfo;
import com.xs.jt.hwvideo.manager.SimpleRead;
import com.xs.jt.hwvideo.manager.VehInfoManager;
import com.xs.jt.hwvideo.manager.WeighManager;
import com.xs.jt.hwvideo.util.CharUtil;
import com.xs.jt.hwvideo.util.PlayUtil;
import com.xs.jt.hwvideo.util.WeightContainer;

@RestController
@RequestMapping(value = "/weigh")
@Modular(modelCode = "hwmange", modelName = "危废管理")
public class WeighController {
	
	protected static Log logger = LogFactory.getLog(WeighController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SimpleRead simpleRead;
	
	@Autowired
	private WeightContainer weightContainer;
	
	@Autowired
	private WeighManager weighManager;
	@Autowired
	private VehInfoManager vehInfoManager;
	
	@Value("${xs.hwvideo.sc.url}")
	private String url;
	
	@Value("${xs.hwvideo.sc.token}")
	private String token;
	
	@Value("${xs.hwvideo.sc.deviceNo}")
	private String deviceNo;
	
	@Resource(name="currentInDatas")
	private JSONArray currentInDatas;
	
	@Resource(name="currentOutDatas")
	private JSONArray currentOutDatas;
	
	@Resource(name="inVehMap")
	private Map<String,JSONObject> inVehMap;
	
	@Value("${xs.hwvideo.sc.ipType}")
	private String ipTypeConfig;
	
	@Autowired
	private PlayUtil playUtil;
	
	@Value("${hk.picPath}")
	private String picPath;
	
	
	@RequestMapping(value = "/scanVeh2", method = RequestMethod.POST)
	public  @ResponseBody JSONObject scan2(HttpServletRequest request) throws Exception{
		Enumeration<String> param = request.getParameterNames();
		while(param.hasMoreElements()) {
			String key =param.nextElement();
			System.out.println(key);
			System.out.println(request.getParameter(key));
		}
		
		JSONObject json=new JSONObject();
		JSONObject alarmInfoPlate =new JSONObject();
		JSONObject result=new JSONObject();
		JSONObject plateResult=new JSONObject();
		plateResult.put("license", request.getParameter("car_plate"));
		result.put("PlateResult", plateResult);
		alarmInfoPlate.put("result",result);
		json.put("AlarmInfoPlate",alarmInfoPlate);
		
//		BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));  
//		StringBuilder responseStrBuilder = new StringBuilder();  
//		String inputStr;  
//		while ((inputStr = streamReader.readLine()) != null) {
//			responseStrBuilder.append(inputStr);
//		}
		scan(json);
		JSONObject jo =new JSONObject();
		jo.put("status",200);
		jo.put("is_paid",true);
		jo.put("verified",true);
		return jo;
	}

	

	@RequestMapping(value = "/scanVeh", method = RequestMethod.POST,consumes="application/json")
	public @ResponseBody String scan(@RequestBody JSONObject jsonObject) throws Exception {
		
		JSONObject alarmInfoPlate = jsonObject.getJSONObject("AlarmInfoPlate");
		JSONObject respJson =new JSONObject();
		respJson.put("info","no");
		respJson.put("content","...");
		respJson.put("is_pay","true");
		JSONObject respData =new JSONObject();
		respData.put("Response_AlarmInfoPlate",respJson);
			
		String type="IN";
		String typeMessage=null;
		
		JSONObject plateResult = getPlateResult(jsonObject);
		String hphm =plateResult.getString("license");
		VehInfo vehInfo = vehInfoManager.getVehInfoOfcc(hphm);
		
		if(vehInfo==null) {
			type="IN";
		}else {
			type="OUT";
		}
		
		if(!StringUtils.isEmpty(hphm)) {
			JSONObject param=new JSONObject();
			param.put("token", token);
			param.put("msgType", "sd_vw_waitConfirm");
			JSONObject msgBody=new JSONObject();
			msgBody.put("deviceNo",deviceNo);
			msgBody.put("vehicleNo",hphm);
			msgBody.put("type", type);
			param.put("msgBody", msgBody);
			JSONObject jo = restTemplate.postForObject(url, param, JSONObject.class);
			if(jo.getInteger("status")==0&&vehInfo==null) {
				playUtil.play(hphm + "暂无派车单！",3);
			}else {
				JSONArray datas =  jo.getJSONArray("data");
				if(vehInfo!=null) {
					if(vehInfo.getStatus()==0) {
						playUtil.play("车辆"+hphm+"，请行驶到地磅称重！。",3);
						respJson.put("info","ok");
						return respData.toJSONString();
					}else if(vehInfo.getStatus()==2) {
						playUtil.play("车辆"+hphm+"，请行驶到地磅称重！。",3);
						respJson.put("info","ok");
						return respData.toJSONString();
					}else if(vehInfo.getStatus()==1) {
						typeMessage="出场";
						playUtil.play("车辆"+typeMessage+"，"+hphm+"，请确认。",3);
						int i =0;
						while(vehInfo.getStatus()==1&&i<=60*3) {
							vehInfo = vehInfoManager.getVehInfoByHPHM(hphm);
							Thread.sleep(1000);
							i++;
						}
						if(vehInfo.getStatus()==2) {
							weighManager.cccz(vehInfo);
							vehInfo.setCckssj(new Date());
							vehInfoManager.save(vehInfo);
							respJson.put("info","ok");
							return respData.toJSONString();
						}
					}
				}else {
					typeMessage="进场";
					currentInDatas.clear();
					currentInDatas.addAll(datas);
					playUtil.play("车辆"+typeMessage+"，"+hphm + "有"+datas.size()+"个派车单，请确认派车单。",2);
					int i =0;
					while(vehInfo==null&&i<=60*3) {
						vehInfo = vehInfoManager.getVehInfoOfcc(hphm);
						Thread.sleep(1000);
						i++;
					}
					if(vehInfo!=null) {
						weighManager.jccz(vehInfo);
						respJson.put("info","ok");
						return respData.toJSONString();
					}
				}
			}
		}
		return respData.toJSONString();
	}
	
	
	@UserOperation(code = "confingVeh", name = "确认派车单")
	@RequestMapping(value = "/confingVeh", method = RequestMethod.POST)
	public void confingVeh(@RequestBody JSONObject jsonObject) {
		String type = jsonObject.getString("type");
		String hphm = jsonObject.getString("vehicleNo");
		
		JSONObject param=new JSONObject();
		param.put("token", token);
		param.put("msgType", "sd_vw_confirm");
		JSONObject msgBody=new JSONObject();
		msgBody.put("deviceNo",deviceNo);
		msgBody.put("vehicleNo",hphm);
		msgBody.put("type", type);
		msgBody.put("billId", jsonObject.getString("billId"));
		param.put("msgBody", msgBody);
		JSONObject jo = restTemplate.postForObject(url, param, JSONObject.class);
		if(jo.getInteger("status")==1) {
			
			jsonObject.put("recordId", jo.getJSONObject("data").getString("recordId"));
			System.out.println("recordId====="+jo.getJSONObject("data").getString("recordId"));
			if("IN".equals(type)) {
				weighManager.inVeh(jsonObject);
			}else if("OUT".equals(type)) {
				weighManager.outVeh(jsonObject);
			}
		}
	}
	
	
	
	@UserOperation(code = "getConfingVehList", name = "获取派车单")
	@RequestMapping(value = "/getConfingVehList",method=RequestMethod.POST)
	public @ResponseBody JSONArray getConfingVehList(String type) {
		if("IN".equals(type)) {
			return currentInDatas;
		}else if("OUT".equals(type)) {
			return currentOutDatas;
		}
		return null;
	}
	
	
	
	@UserOperation(code = "getOutVehList", name = "获取出厂车辆")
	@RequestMapping(value = "/getOutVehList",method=RequestMethod.POST)
	public @ResponseBody JSONArray getConfingVehList() {
		JSONArray ja=new JSONArray();
		List<VehInfo> datas = this.vehInfoManager.getOutVehInfos();
		for( VehInfo vehInfo:datas) {
			JSONObject jo =JSONObject.parseObject(vehInfo.getJsonData());
			jo.put("vehInfoId", vehInfo.getId());
			ja.add(jo);
		}
		return ja;
	}
	
	@UserOperation(code = "getCompleteData", name = "获取已完成数据")
	@RequestMapping(value = "/getCompleteData",method=RequestMethod.POST)
	public @ResponseBody List<VehInfo> getCompleteData(){
		
		return vehInfoManager.getCompleteData(null);
		
	}
	
	@UserOperation(code = "uploadFiles", name = "数据上传")
	@RequestMapping(value = "/uploadFiles",method=RequestMethod.POST)
	public @ResponseBody JSONObject uploadFiles(@RequestBody JSONObject params) throws Exception{
		
		String uploadType=params.getString("uploadType");
		String[] types = uploadType.split(",");
		String inRecordId=params.getString("inRecordId");
		String outRecordId=params.getString("outRecordId");
		logger.info("========================uploadType:"+uploadType);
		for(String type:types) {
			if("1".equals(type)) {
				weighManager.uploadPhoto(picPath, inRecordId);
			}
			if("2".equals(type)) {
				 weighManager.uploadPhoto(picPath, outRecordId);
			}
			if("3".equals(type)) {
				 weighManager.uploadFile(picPath, inRecordId);
			}
			if("4".equals(type)) {
				 weighManager.uploadFile(picPath, outRecordId);
			}
		}
		JSONObject jo =new JSONObject();
		jo.put("status", 200);
		
		return jo;
		
	}
	
	@UserOperation(code = "openCom", name = "打开串口")
	@RequestMapping(value = "/openCom",method=RequestMethod.POST)
	public  @ResponseBody boolean open() throws Exception{
		if(!simpleRead.isOpen()) {
			simpleRead.open();
		}else {
			simpleRead.close();
		}
		return simpleRead.isOpen();
	}
	
	@UserOperation(code = "sendMessage", name = "发送消息")
	@RequestMapping(value = "/sendMessage",method=RequestMethod.POST)
	public  @ResponseBody boolean sendMessage(@RequestParam("message") String message) throws Exception{
		if(!simpleRead.isOpen()) {
			return false;
		}else {
			simpleRead.sendMessage(CharUtil.hexStringToByte(message));
			return true;
		}
		
	}
	
	
	@UserOperation(code = "getComInfo", name = "获取地磅串口信息")
	@RequestMapping(value = "/getComInfo",method=RequestMethod.POST)
	public  @ResponseBody Map<String,Object> getComInfo() throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("com", simpleRead.getCom());
		map.put("rate", simpleRead.getRate());
		map.put("databits", simpleRead.getDatabits());
		map.put("parity", simpleRead.getParity());
		map.put("stopbits", simpleRead.getStopbits());
		map.put("statu", simpleRead.isOpen());
		return map;
	}
	
	
	@UserOperation(code = "getWeight", name = "获取称重数据")
	@RequestMapping(value = "/getWeight",method=RequestMethod.POST)
	public  @ResponseBody Float getWeight() throws Exception{
		return weightContainer.getWeight();
	}
	
	
	
	private JSONObject getPlateResult(JSONObject jsonObject) {
		JSONObject alarmInfoPlate =  jsonObject.getJSONObject("AlarmInfoPlate");
		if(alarmInfoPlate==null) {
			return null;
		}
		JSONObject result = alarmInfoPlate.getJSONObject("result");
		if(result==null) {
			return null;
		}
		JSONObject plateResult = result.getJSONObject("PlateResult");
		return plateResult;
	}
	
	
	
	

	

}
