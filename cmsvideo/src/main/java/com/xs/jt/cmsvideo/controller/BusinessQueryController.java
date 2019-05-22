package com.xs.jt.cmsvideo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.XmlCodeUtil;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNewResponse;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNewResponse;
import com.xs.jt.cmsvideo.util.BeanXMLUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Controller
@RequestMapping(value = "/business")
@Modular(modelCode = "business", modelName = "业务查询",jsjb= {Role.JSJB_YWBL})
public class BusinessQueryController {

	protected static Log log = LogFactory.getLog(BusinessQueryController.class);

//	@Autowired
//	private TmriJaxRpcOutService tmriJaxRpcOutService;

	@Autowired
	private ServletContext servletContext;

	@Resource(name = "TmriJaxRpcOutNewAccessServiceStub")
	private TmriJaxRpcOutNewAccessServiceStub tro;

	@Value("${stu.properties.jkxlh}")
	private String jkxlh;

	@Value("${stu.properties.xtlb}")
	private String xtlb;
	@Value("${stu.properties.dwmc}")
	private String dwmc;

	@Value("${stu.properties.dwjgdm}")
	private String dwjgdm;

	@Value("${stu.properties.zdbs}")
	private String zdbs;

	@Autowired
	private HttpSession session;

	// 18CH3
	@UserOperation(code = "getCheckNotPassInfo", name = "获取机动车查验复核不通过原因")
	@RequestMapping(value = "getCheckNotPassInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCheckNotPassInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CH3", param);
		} catch (Exception e) {
			log.error("获取机动车查验复核不通过原因异常", e);
			throw new ApplicationException("获取机动车查验复核不通过原因异常", e);
		}
		return map;
	}

	// 18CH5
	@UserOperation(code = "getPreregistInfo", name = "获取查验预登记信息")
	@RequestMapping(value = "getPreregistInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getPreregistInfo(@RequestParam Map param) {
		Map map = new HashMap();
		try {
			map = queryws("18CH5", param);
		} catch (Exception e) {
			log.error("获取查验预登记信息异常", e);
			throw new ApplicationException("获取查验预登记信息异常", e);
		}

		return map;
	}

	// 18CH6
	@UserOperation(code = "getGonggaoInfo", name = "获取机动车公告技术参数文本信息")
	@RequestMapping(value = "getGonggaoInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGonggaoInfo(@RequestParam Map param) {

		Map map = new HashMap();
		try {
			//map = queryws("18CH6", param);
			String yhbz = "";
			String yhxm = "";
			if (session != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					yhbz = user.getSfzh();
					yhxm = user.getYhxm();
				}
			}
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qoo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew();
			log.info(param);
			qoo.setJkid("18CH6");
			qoo.setXtlb(xtlb);
			qoo.setJkxlh(jkxlh);
			qoo.setZdbs(zdbs);
			qoo.setDwmc(dwmc);
			qoo.setDwjgdm(dwjgdm);
			qoo.setYhbz(yhbz);
			qoo.setYhxm(yhxm);
			Document xml = BeanXMLUtil.map2xml(param, "QueryCondition");
			qoo.setUTF8XmlDoc(xml.asXML());
			log.info("-------------------------QueryCondition:"+xml.asXML());
			QueryObjectOutNewResponse qoor = tro.queryObjectOutNew(qoo);

			String response = qoor.getQueryObjectOutNewReturn();
			//log.info("response:" + response);
			response = URLDecoder.decode(response, "utf-8");
			Document document = DocumentHelper.parseText(response);
			log.info("-------------------------result:"+document.asXML());
			
			JSON json = new XMLSerializer().read(document.asXML());
			//log.info("json:"+json);
			JSONObject jo = JSONObject.parseObject(json.toString());
			JSONObject headJson = jo.getJSONObject("head");
			if(headJson.getInteger("rownum")>0) {
				map.put("body", jo.getJSONObject("body"));
			}
			else {
				map.put("body", new Object());
			}
		} catch (Exception e) {
			log.error("获取机动车公告技术参数文本信息异常", e);
			throw new ApplicationException("获取机动车公告技术参数文本信息异常", e);
		}

		return map;
	}

	// 18CH7
	@UserOperation(code = "getGonggaoImageInfo", name = "获取机动车公告技术参数图片信息")
	@RequestMapping(value = "getGonggaoImageInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGonggaoImageInfo(@RequestParam Map param) {

		Map map = new HashMap();
		try {
			map = queryws("18CH7", param);
		} catch (Exception e) {
			log.error("获取机动车公告技术参数图片信息异常", e);
			throw new ApplicationException("获取机动车公告技术参数图片信息异常", e);
		}

		return map;
	}

	// 18CH8
	@UserOperation(code = "getRecheckInfo", name = "获取机动车查验抽查复核结果")
	@RequestMapping(value = "getRecheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRecheckInfo(@RequestParam Map param) {

		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CH8", param);
		} catch (Exception e) {
			log.error("获取机动车查验抽查复核结果异常", e);
			throw new ApplicationException("获取机动车查验抽查复核结果异常", e);
		}

		return map;
	}

	// 18CH9
	@UserOperation(code = "getViolationInfo", name = "获取机动车违规产品信息")
	@RequestMapping(value = "getViolationInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getViolationInfo(@RequestParam Map param) {

		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CH9", param);
		} catch (Exception e) {
			log.error("获取机动车违规产品信息异常", e);
			throw new ApplicationException("获取机动车违规产品信息异常", e);
		}

		return map;
	}

	// 18CHA
	@UserOperation(code = "getVehImage", name = "获取机动车标准照片等信息")
	@RequestMapping(value = "getVehImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVehImage(@RequestParam Map param) {
		Map map = new HashMap();
		try {
			map = queryws("18CHA", param);
		} catch (Exception e) {
			log.error("获取机动车标准照片等信息异常", e);
			throw new ApplicationException("获取机动车标准照片等信息异常", e);
		}

		return map;
	}

	// 18CF1
	@UserOperation(code = "getInspectorInfo", name = "获取查验员备案信息")
	@RequestMapping(value = "getInspectorInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getInspectorInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF1", param);
		} catch (Exception e) {
			log.error("获取查验员备案信息异常", e);
			throw new ApplicationException("获取查验员备案信息异常", e);
		}
		

		return map;
	}

	// 18CF2
	@UserOperation(code = "getCheckAreaInfo", name = "获取查验区备案信息")
	@RequestMapping(value = "getCheckAreaInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCheckAreaInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF2", param);
		} catch (Exception e) {
			log.error("获取查验区备案信息异常", e);
			throw new ApplicationException("获取查验区备案信息异常", e);
		}
	

		return map;
	}

	// 18CF3
	@UserOperation(code = "getSysBackupInfo", name = "获取查验智能终端系统备案信息")
	@RequestMapping(value = "getSysBackupInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSysBackupInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF3", param);
		} catch (Exception e) {
			log.error("获取查验智能终端系统备案信息异常", e);
			throw new ApplicationException("获取查验智能终端系统备案信息异常", e);
		}

		return map;
	}

	// 18CF4
	@UserOperation(code = "getAutoBackupInfo", name = "获取外廓尺寸自动查验装置备案信息")
	@RequestMapping(value = "getAutoBackupInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAutoBackupInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF4", param);
		} catch (Exception e) {
			log.error("获取外廓尺寸自动查验装置备案信息异常", e);
			throw new ApplicationException("获取外廓尺寸自动查验装置备案信息异常", e);
		}

		return map;
	}

	// 18CF5
	@UserOperation(code = "getZbzlBackupInfo", name = "获取整备质量自动测量仪备案信息")
	@RequestMapping(value = "getZbzlBackupInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getZbzlBackupInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF5", param);
		} catch (Exception e) {
			log.error("获取整备质量自动测量仪备案信息异常", e);
			throw new ApplicationException("获取整备质量自动测量仪备案信息异常", e);
		}

		return map;
	}

	// 18CF6
	@UserOperation(code = "getSmcyBackupInfo", name = "获取上门查验服务备案信息")
	@RequestMapping(value = "getSmcyBackupInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSmcyBackupInfo(@RequestParam Map param) {
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			map = queryws("18CF6", param);
		} catch (Exception e) {
			log.error("获取上门查验服务备案信息异常", e);
			throw new ApplicationException("获取上门查验服务备案信息异常", e);
		}

		return map;
	}

	/*
	 * // 18CF3
	 * 
	 * @UserOperation(code = "getPDAInfo", name = "获取查验智能终端系统备案信息")
	 * 
	 * @RequestMapping(value = "getPDAInfo", method = RequestMethod.POST)
	 * public @ResponseBody Map<String, Object> getPDAInfo(@RequestBody JSONObject
	 * param) {
	 * 
	 * return null; }
	 */

	public Map queryws(String jkid, Map param)
			throws RemoteException, UnsupportedEncodingException, DocumentException {
		Map map = new HashMap();
		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qoo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew();
		log.info(param);
		qoo.setJkid(jkid);
		qoo.setXtlb(xtlb);
		qoo.setJkxlh(jkxlh);
		qoo.setZdbs(zdbs);
		qoo.setDwmc(dwmc);
		qoo.setDwjgdm(dwjgdm);
		qoo.setYhbz(yhbz);
		qoo.setYhxm(yhxm);
		Document xml = BeanXMLUtil.map2xml(param, "QueryCondition");
		qoo.setUTF8XmlDoc(xml.asXML());
		log.info("-------------------------QueryCondition:"+xml.asXML());
		QueryObjectOutNewResponse qoor = tro.queryObjectOutNew(qoo);

		String response = qoor.getQueryObjectOutNewReturn();
		log.info("response:" + response);
		response = URLDecoder.decode(response, "utf-8");
		Document document = DocumentHelper.parseText(response);
		log.info("-------------------------result:"+document.asXML());
		
		JSON json = new XMLSerializer().read(document.asXML());
		//log.info("json:"+json);
		JSONObject jo = JSONObject.parseObject(json.toString());
		JSONObject headJson = jo.getJSONObject("head");
		if(headJson.getInteger("rownum")>0) {
			map.put("rows", jo.getJSONArray("body"));
		}
		else {
			map.put("rows", new ArrayList());
		}
		return map;
	}



	// 18CJ9
	@UserOperation(code = "writeYlrxx", name = "机动车预录入信息")
	@RequestMapping(value = "writeYlrxx", method = RequestMethod.POST)
	public @ResponseBody String writeYlrxx(@RequestBody Map param) {
		String resultStr = "";
		try {
			log.info(param);
			resultStr = writeData(param,"18CJ9");
			log.info("上传机动车预录入信息返回结果：" + resultStr);
		} catch (Exception e) {
			log.error("机动车预录入信息异常", e);
			throw new ApplicationException("机动车预录入信息异常", e);
		}

		return resultStr;
	}
	
	
	@UserOperation(code = "writeCyfhtgxx", name = "写入机动车查验复核通过信息获取回执")
	@RequestMapping(value = "writeCyfhtgxx", method = RequestMethod.POST)
	public @ResponseBody String writeCyfhtgxx(@RequestBody Map param) {
		String resultStr = "";
		try {
			log.info(param);
			resultStr = writeData(param,"18CJ3");
			log.info("写入机动车查验复核通过信息获取回执返回结果：" + resultStr);
		} catch (Exception e) {
			log.error("写入机动车查验复核通过信息获取回执异常", e);
			throw new ApplicationException("写入机动车查验复核通过信息获取回执异常", e);
		}
		return resultStr;
	}
	
	
	@UserOperation(code = "writeWdzbzl", name = "写入外廓尺寸、整备质量等测量结果")
	@RequestMapping(value = "writeWdzbzl", method = RequestMethod.POST)
	public @ResponseBody String writeWdzbzl(@RequestBody Map param) {
		String resultStr = "";
		try {
			log.info(param);
			resultStr = writeData(param,"18CGD");
			log.info("写入外廓尺寸、整备质量等测量结果返回结果：" + resultStr);
		} catch (Exception e) {
			log.error("写入外廓尺寸、整备质量等测量结果异常", e);
			throw new ApplicationException("写入外廓尺寸、整备质量等测量结果异常", e);
		}
		return resultStr;
	}
	
	@UserOperation(code = "writeCyspycxx", name = "写入机动车查验视频异常情况描述信息")
	@RequestMapping(value = "writeCyspycxx", method = RequestMethod.POST)
	public @ResponseBody String writeCyspycxx(@RequestBody Map param) {
		String resultStr = "";
		try {
			log.info(param);
			resultStr = writeData(param,"18CJ1");
			log.info("写入机动车查验视频异常情况描述信息返回结果：" + resultStr);
		} catch (Exception e) {
			log.error("写入机动车查验视频异常情况描述信息异常", e);
			throw new ApplicationException("写入机动车查验视频异常情况描述信息异常", e);
		}
		return resultStr;
	}
	
	public String writeData(Map jo,String jkid) throws Exception {
		String resultStr = "";
		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNew woo = new TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNew();
		woo.setJkid(jkid);
		woo.setXtlb(xtlb);
		woo.setJkxlh(jkxlh);
		woo.setZdbs(zdbs);
		woo.setDwmc(dwmc);
		woo.setDwjgdm(dwjgdm);
		woo.setYhbz(yhbz);
		woo.setYhxm(yhxm);
		Document xml = BeanXMLUtil.map2xml(jo, "vehcrpara");
		log.info("写入内容："+xml.asXML());
		String bo = xml.asXML();
		woo.setUTF8XmlDoc(bo);
		WriteObjectOutNewResponse wor = tro.writeObjectOutNew(woo);
		String response = wor.getWriteObjectOutNewReturn();
		response = URLDecoder.decode(response, "utf-8");
		Document document = DocumentHelper.parseText(response);

		Element root = document.getRootElement();
		Element head = root.element("head");
		Element code = head.element("code");
		Element message = head.element("message");
		resultStr = document.asXML();
		log.info("返回结果："+resultStr);
		return resultStr;
	}

	public static Element JSONConvertXML(Element e, JSONObject jo) {

		Set<String> keySet = jo.keySet();
		for (String key : keySet) {
			if (!"".equals(jo.getString(key))) {
				Element node = e.addElement(key.toLowerCase());
				node.setText(XmlCodeUtil.urlEncode(jo.getString(key)));
			}
		}
		return e;

	}

}
