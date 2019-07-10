package com.xs.jt.cmsvideo.controller;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.MapUtil;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNewResponse;
import com.xs.jt.cmsvideo.client.TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNewResponse;
import com.xs.jt.cmsvideo.entity.PreCarRegister;
import com.xs.jt.cmsvideo.manager.IPreCarRegisterManager;
import com.xs.jt.cmsvideo.util.BarcodeUtil;
import com.xs.jt.cmsvideo.util.BeanXMLUtil;
import com.xs.jt.cmsvideo.util.GenerateSeq;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Controller
@RequestMapping(value = "/preCarRegister")
@Modular(modelCode = "preCarRegister", modelName = "车辆预登记")
public class PreCarRegisterController {
	
	protected static Log log = LogFactory.getLog(PreCarRegisterController.class);
	
	@Resource(name = "TmriJaxRpcOutNewAccessServiceStub")
	private TmriJaxRpcOutNewAccessServiceStub tro;
	
	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;

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
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private ServletContext servletContext;
	
	@UserOperation(code = "getGongGaoInfo", name = "根据车辆型号,车辆品牌(中文)查询最新公告信息")
	@RequestMapping(value = "getGongGaoInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGongGaoInfo(@RequestParam Map param) {
		Map map = new HashMap();
		try {
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
				JSONObject bodyJo =jo.getJSONObject("body");
				Object vehObj = bodyJo.get("vehicle");
				if(vehObj instanceof JSONObject) {
					map.put("ggxx", bodyJo.getJSONObject("vehicle"));
				}else {
					JSONArray arr = bodyJo.getJSONArray("vehicle");
					JSONObject returnObj = null;
					for(int i=0;i<arr.size();i++) {
						JSONObject vehjo = arr.getJSONObject(i);
						if(i == 0) {
							returnObj = vehjo;
						}else {
							if(vehjo.getDate("ggrq").getTime() > returnObj.getDate("ggrq").getTime()) {
								returnObj = vehjo;
							}
						}
						
					}
					map.put("ggxx", returnObj);
				}
			}
			else {
				map.put("ggxx", new JSONObject());
			}
		} catch (Exception e) {
			log.error("获取机动车公告技术参数文本信息异常", e);
			throw new ApplicationException("获取机动车公告技术参数文本信息异常", e);
		}

		return map;
	}
	
	
	@UserOperation(code = "savePreCarRegister", name = "保存")
	@RequestMapping(value = "savePreCarRegister", method = RequestMethod.POST)
	public @ResponseBody Map savePreCarRegister(HttpSession session, @Valid PreCarRegister bcr, BindingResult result) throws Exception {
		if (!result.hasErrors()) {
			System.out.println("savePreCarRegister");
			User user = (User) session.getAttribute("user");
			String stationCode = (String) user.getBmdm();// ---------------
			bcr.setStationCode(stationCode);
			String lsh = null;
			if ("A".equals(bcr.getYwlx())) {
				lsh = getlsh();
				bcr.setLsh(lsh);
			}
			if (null == bcr.getDpid() || "".equals(bcr.getDpid().trim())) {
				bcr.setDpid(null);
			}
			Map<String,Object> paramdata =MapUtil.object2Map(bcr);
			paramdata.put("xh", "AAAAAAAAAAAAAA");
			paramdata.put("sfzmhm", bcr.getSfz());
			Document document = writeData(paramdata,"18CJ9");

			Element root = document.getRootElement();
			Element head = root.element("head");
			Element code = head.element("code");
			Element message = head.element("message");
			if("1".equals(code.getText())) {
				bcr = this.preCarRegisterManager.save(bcr); 
				if ("A".equals(bcr.getYwlx())) {
					Map<String,Object> data =MapUtil.object2Map(bcr);
					data.put("createtime", bcr.getCreateTime());
					data.put("lshCode", BarcodeUtil.generateInputStream(lsh));
					if(StringUtils.isEmpty(bcr.getHphm())) {
						data.put("hphm", bcr.getClsbdh());
					}
					String template = "template_pt_first.doc";
					Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
					com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, data,bpsMap);
					Sql2WordUtil.toCase(doc, cacheDir, "\\report\\template_ptc_01_"+lsh+".jpg");
				}
			}
			
			
			return ResultHandler.resultHandle(result, lsh, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.toErrorJSON("数据校验失败");
		}
	}
	
	public Document writeData(Map jo,String jkid) throws Exception {
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
		resultStr = document.asXML();
		log.info("返回结果："+resultStr);
		return document;
	}
	
	
	public String getlsh() throws InterruptedException {
		String lsh = "";
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR) - 1900;
		String month = (c.get(Calendar.MONTH)+1) < 10?("0"+(c.get(Calendar.MONTH)+1)):(c.get(Calendar.MONTH)+1)+"";
		String date = (c.get(Calendar.DATE)) < 10?("0"+(c.get(Calendar.DATE))):(c.get(Calendar.DATE))+"";
		String minute = (c.get(Calendar.MINUTE)) < 10?("0"+(c.get(Calendar.MINUTE))):(c.get(Calendar.MINUTE))+"";
		String second = (c.get(Calendar.SECOND)) < 10?("0"+(c.get(Calendar.SECOND))):(c.get(Calendar.SECOND))+"";		

		Random random = new Random();
		//int ends = random.nextInt(99);
		String sjs = GenerateSeq.getSeq();//String.format("%02d",ends);
		lsh = year+month+date+minute+second+sjs;
		log.info(lsh);
		return lsh;
	}

}
