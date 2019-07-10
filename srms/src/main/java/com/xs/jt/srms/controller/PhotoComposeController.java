package com.xs.jt.srms.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.srms.manager.IVehFlowManager;
import com.xs.jt.srms.manager.IVehImgManager;
import com.xs.jt.srms.vehimg.entity.VehImg;

@Controller
@RequestMapping(value = "/photoCompose")
public class PhotoComposeController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private IVehImgManager vehImgManager;
	
	@Autowired
	private IVehFlowManager vehFlowManager;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private HttpServletRequest request;	
	
	
	@RequestMapping(value = "toHctp", method = RequestMethod.POST)
	public String toHctp(@RequestParam Map param,Model model) throws Exception {
		Iterator it = param.keySet().iterator();
		while(it.hasNext()){
		    String paramName = (String) it.next();
		    String paramValue = URLDecoder.decode(param.get(paramName).toString(),"UTF-8");
		    param.put(paramName, paramValue);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		
        param.put("currDate", sdf.format(new Date()));
		String templateName = param.get("tempName").toString();//"注册申请表";
		String template = templateName+".doc";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil2(template, param);
		String fileName = UUID.randomUUID().toString()+".jpg";// templateName+"_"+param.get("clsbdh")+".jpg";
		//Sql2WordUtil.toCase(doc, "", fileName);
		Sql2WordUtil.toCase(doc, cacheDir, "\\qmtp\\"+fileName);
		//param.put("bgdImage", Base64.encode(image2byte(Sql2WordUtil.getCacheDir()+fileName)));
		model.addAttribute("bgdImage", fileName);
		model.addAttribute("tempName", templateName);
		model.addAttribute("reqParam", param);
		return "qmtphc";
	}
	
	@RequestMapping(value = "composeImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> composeImage(@RequestParam Map param) throws Exception{
//		Map<String, List<BaseParams>> bps = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
//		if(bps == null) {
//			bps = convertBaseParam2Map();
//		}
		Iterator it = param.keySet().iterator();
		while(it.hasNext()){
		    String paramName = (String) it.next();
		    if(!"base64Img".equals(paramName)) {
		    	String paramValue = URLDecoder.decode(param.get(paramName).toString(),"UTF-8");
			    param.put(paramName, paramValue);
		    }		    
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		
        param.put("currDate", sdf.format(new Date()));
		
		byte[] zp = Base64Utils.decodeFromString(param.get("base64Img").toString());//Base64.decode(param.get("base64Img").toString());
		param.put("qmzp",new ByteArrayInputStream(zp));
		String templateName = param.get("mbmc").toString();//"注册申请表";
		String template = templateName+".doc";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil2(template, param);
		String fileName = UUID.randomUUID().toString()+".jpg";
		//Sql2WordUtil.toCase(doc, "", fileName);
		Sql2WordUtil.toCase(doc, cacheDir, "\\qmtp\\"+fileName);
		
		List<Map<String,Object>> flowList = vehFlowManager.getCarInfoByLsh(param.get("lsh").toString());
		
		
		VehImg vehimg = new VehImg();
		
		vehimg.setZplx("123");
		if(!CollectionUtils.isEmpty(flowList)) {
			Map<String,Object> map = flowList.get(0);
			vehimg.setLsh(map.get("LSH").toString());
			vehimg.setHphm(map.get("HPHM").toString());
			vehimg.setHpzl(map.get("HPZL").toString());
			vehimg.setClsbdh(map.get("CLSBDH").toString());
			vehimg.setYwlx(map.get("YWLX").toString());
		}
		
		this.vehImgManager.updateVehImgZpztOfzplx(vehimg.getLsh(), vehimg.getZplx());
		byte[] imgByte = image2byte(cacheDir+"\\qmtp\\"+fileName);//Base64Utils.decodeFromString(base64Img);//Base64.decode(base64Img);
		vehimg.setZp(imgByte);
		vehimg.setPssj(new Date());
		vehimg.setZpzt("0");
		this.vehImgManager.saveVehImg(vehimg);
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "生成图片成功！", fileName);	
	}
	
	//图片到byte数组
	  public byte[] image2byte(String path)throws Exception{
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (Exception ex1) {
	      throw ex1;
	    }
	    return data;
	  }
	  
//	  public List<Map<String,Object>> getVehFlowByLsh(String lsh){
//		  String sql = "select c.* from veh_flow c where lsh=?";
//		  //return jdbcTemplate.queryForList(sql, lsh);
//		  return null;
//	  }
	
//	public Map<String, List<BaseParams>> convertBaseParam2Map() {
//
//		Map<String, List<BaseParams>> mapParam = new HashMap<String, List<BaseParams>>();
//
//		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
//		if (bps == null) {
//			bps = baseParamsManager.getBaseParams();
//			servletContext.setAttribute("bps",bps );
//		}
//
//		for (BaseParams param : bps) {
//			String key = param.getType();
//			List<BaseParams> typeList = mapParam.get(key);
//			if (typeList == null) {
//				typeList = new ArrayList<BaseParams>();
//				typeList.add(param);
//				mapParam.put(key, typeList);
//			} else {
//				typeList.add(param);
//			}
//		}
//		servletContext.setAttribute("bpsMap", mapParam);
//		return mapParam;
//	}

}
