package com.xs.jt.cms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.MapUtil;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutService;
import com.xs.jt.cms.common.BarcodeUtil;
import com.xs.jt.cms.common.CommonUtil;
import com.xs.jt.cms.common.MatrixToImageWriter;
import com.xs.jt.cms.common.URLCodeUtil;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.manager.IPreCarRegisterManager;
import com.xs.jt.cms.manager.IVehicleLockManager;

@Controller
@RequestMapping(value = "/preCarRegister")
@Modular(modelCode = "preCarRegister", modelName = "车辆预登记")
public class PreCarRegisterController {
	
	private static Logger logger = LoggerFactory.getLogger(PreCarRegisterController.class);

	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;
	
	@Autowired
	private IPDAServiceManager pDAServiceManager;
	
	@Autowired
	private TmriJaxRpcOutService tmriJaxRpcOutService;
	
	@Value("${stu.properties.glbm}")
	private String glbm;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private IVehicleLockManager vehicleLockManager;

	@UserOperation(code = "savePreCarRegister", name = "保存")
	@RequestMapping(value = "savePreCarRegister", method = RequestMethod.POST)
	public @ResponseBody Map savePreCarRegister(HttpSession session, @Valid PreCarRegister bcr, BindingResult result) throws Exception {
		if (!result.hasErrors()) {
			List<VehicleLock> list = vehicleLockManager.findLockVehicle(bcr.getClsbdh());
			if (list != null && list.size() > 0) {
				return ResultHandler.toErrorJSON("车辆已被锁定，不能保存");
			}
			User user = (User) session.getAttribute("user");
			String stationCode = (String) user.getBmdm();// ---------------
			bcr.setStationCode(stationCode);
			String lsh = null;
			if ("A".equals(bcr.getYwlx())) {
				lsh = getlsh();
				//lsh = "123456789101111512";
				bcr.setLsh(lsh);
			}
			if (null == bcr.getDpid() || "".equals(bcr.getDpid().trim())) {
				bcr.setDpid(null);
			}
			bcr = this.preCarRegisterManager.save(bcr); 
			if ("A".equals(bcr.getYwlx())) {
				Map<String,Object> data =MapUtil.object2Map(bcr);
				data.put("createtime", bcr.getCreateTime());
				data.put("lshCode", BarcodeUtil.generateInputStream(lsh));
				if(StringUtils.isEmpty(bcr.getHphm())) {
					data.put("hphm", bcr.getClsbdh());
				}
				String template = "template_pt_first.doc";
				if ("Y".equals(bcr.getVeh_sfxc())) {
					template = "template_pt_xc.doc";
					CommonUtil.setXczl(bcr, data);
				}
				Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
				com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, data,bpsMap);
				Sql2WordUtil.toCase(doc, cacheDir, "\\report\\template_ptc_01_"+lsh+".jpg");
			}
			return ResultHandler.resultHandle(result, lsh, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.toErrorJSON("数据校验失败");
		}
	}

	
	
	@UserOperation(code="getCarList",name="查询已登记信息")
	@RequestMapping(value = "getCarList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getCarList(Integer page, Integer rows, PreCarRegister preCarRegister) {			
		
		return preCarRegisterManager.getPreCarRegisters(page-1, rows, preCarRegister);
	}

	private String getlsh() {
		String lsh = null;
		try {
			TmriJaxRpcOutNewAccessServiceStub trias =tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qo = tmriJaxRpcOutService.createQueryObjectOut();
			qo.setJkid("01C24");
			qo.setUTF8XmlDoc("<root><QueryCondition><glbm>"+glbm+"</glbm></QueryCondition></root>");
			String returnXML = trias.queryObjectOutNew(qo).getQueryObjectOutNewReturn();
			Document doc = DocumentHelper.parseText(returnXML);
			Element root = doc.getRootElement();
			lsh = root.element("body").element("veh").element("lsh").getText();
		} catch (Exception e) {
			logger.error("获取平台流水号异常",e);
			throw new  ApplicationException("获取平台流水号异常",e);
		}
		return lsh;
	}

	public void create2Code(String path, String content, String fileName) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix;
		try {
			bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
			File file1 = new File(path, fileName + ".jpg");
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createLSHCode(String path,String fileName, String lsh) {
		try {
			BitMatrix bitMatrix = toBarCodeMatrix(lsh, 10, 20);
			File file1 = new File(path, fileName + ".jpg");
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BitMatrix toBarCodeMatrix(String str, Integer width, Integer height) throws WriterException {

		if (width == null || width < 200) {
			width = 200;
		}
		if (height == null || height < 50) {
			height = 50;
		}
		// 文字编码
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
		return bitMatrix;
	}
	
	@UserOperation(code = "updateRegister", name = "更新")
	@RequestMapping(value = "updateRegister", method = RequestMethod.POST)
	public @ResponseBody Map updateRegister(HttpSession session, @Valid PreCarRegister bcr, BindingResult result) {
		StringBuilder sb = new StringBuilder("");
		
		sb.append(bcr.getClxh());
		sb.append("|");
		sb.append(bcr.getClsbdh());
		sb.append("|");
		sb.append(bcr.getHdzk());
		sb.append("|");
		sb.append(bcr.getCsys());
		sb.append("|");
		sb.append(bcr.getCllx());
		sb.append("|");
		sb.append(bcr.getHpzl());
		sb.append("|");
		sb.append(bcr.getYwlx());
		sb.append("|");
		sb.append(bcr.getGgbh());
		sb.append("|");
		sb.append(bcr.getSyxz());
		sb.append("|");
		sb.append(bcr.getFdjh());
		sb.append("|");
		sb.append(bcr.getQlj());
		sb.append("|");
		sb.append(bcr.getHlj());
		sb.append("|");
		sb.append(bcr.getZj());
		sb.append("|");
		sb.append(bcr.getLsh());
		sb.append("|");
//		if(null==bcr.getHphm()||"".equals(bcr.getHphm().trim())){
//			bcr.setHphm(null);
//		}
		sb.append(bcr.getHphm());
		sb.append("|");
		if(null==bcr.getDpid()||"".equals(bcr.getDpid().trim())){
			bcr.setDpid(null);
		}
		
		sb.append(bcr.getDpid());
		
		this.preCarRegisterManager.save(bcr);
		System.out.println(sb.toString());
		
		String path = System.getProperty("2code");
		create2Code(path,sb.toString(), String.valueOf(bcr.getId()));
		return ResultHandler.resultHandle(result, bcr.getLsh(), Constant.ConstantMessage.SAVE_SUCCESS);
	}
	
	@UserOperation(code = "getCarInfoByCarNumber", name = "获取机动车基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getCarInfoByCarNumber(String hpzl,String hphm) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qo = tmriJaxRpcOutService.createQueryObjectOut();

			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null
					|| "".equals(hphm.trim())) {
				return dataMap;
			}

			qo.setJkid("01C21");
			qo.setUTF8XmlDoc("<root><QueryCondition><hphm>" + hphm
					+ "</hphm><hpzl>" + hpzl
					+ "</hpzl></QueryCondition></root>");

			String returnXML = trias.queryObjectOutNew(qo)
					.getQueryObjectOutNewReturn();
			String xml = URLCodeUtil.urlDecode(returnXML);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element dataElecmet = root.element("body").element("veh");

			if (dataElecmet != null) {
				
				for (Object o : dataElecmet.elements()) {
					Element element = (Element) o;
					String key = element.getName();
					String value = element.getText();
					dataMap.put(key, value);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	@UserOperation(code = "getGongGaoInfoByGgbh", name = "获取公告信息")
	@RequestMapping(value = "getGongGaoInfoByGgbh", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getGongGaoInfoByGgbh(String ggbh) {
		Map<String,Object> map = new HashMap<String,Object>();
		if (ggbh == null || "".equals(ggbh.trim())) {
			return map;
		}

		List<Map<String, Object>> list = pDAServiceManager.findPcbStVehicle(ggbh);
		

		Map<String, Object> rMap = (Map) list.get(0);

		Map returnMap = new HashMap();

		for (String key : rMap.keySet()) {
			returnMap.put(key.toLowerCase(), rMap.get(key));
		}

		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "获取公告信息成功", returnMap);//returnMap;

	}
	
	@UserOperation(code = "getGongGaoListbyCLXH", name = "获取公告日期列表")
	@RequestMapping(value = "getGongGaoListbyCLXH", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getGongGaoListbyCLXH(String clxh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (clxh != null) {
			list = pDAServiceManager.findGongGaoListbyCLXH(clxh);

		}
		return list;
	}
	
	@UserOperation(code = "getImplCarParam", name = "获取进口车辆信息")
	@RequestMapping(value = "getImplCarParam", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getImplCarParam(String clxh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (clxh != null) {
			list = pDAServiceManager.getImplCarParam(clxh);

		}
		return list;
	}
	
	@UserOperation(code = "printCarInfo", name = "打印查验单")
	@RequestMapping(value = "printCarInfo", method = RequestMethod.POST)
	public @ResponseBody Map printCarInfo(String lsh) {
		try {
			
			if(StringUtils.isEmpty(lsh)) {
				return ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "流水号不能为空", lsh);
			}
			
			File imgFile = new File(cacheDir+"\\report\\template_ptc_01_"+lsh+".jpg");
			if(!imgFile.exists()) {
				PreCarRegister bcr = this.preCarRegisterManager.findPreCarRegisterByLsh(lsh);
				Map<String,Object> data =MapUtil.object2Map(bcr);
				data.put("lshCode", BarcodeUtil.generateInputStream(bcr.getLsh()));
				if(StringUtils.isEmpty(bcr.getHphm())) {
					data.put("hphm", bcr.getClsbdh());
				}
				
				Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
				String template = "template_pt_first.doc";
				if ("Y".equals(bcr.getVeh_sfxc())) {
					template = "template_pt_xc.doc";
					CommonUtil.setXczl(bcr, data);
				}
				com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, data,bpsMap);
				Sql2WordUtil.toCase(doc, cacheDir, "\\report\\template_ptc_01_"+bcr.getLsh()+".jpg");
				
			}
		}catch(Exception e) {
			logger.error("打印查验单异常",e);
			throw new  ApplicationException("打印查验单异常",e);
		}
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "打印查验单成功", lsh);
	}
	
	@UserOperation(code = "getGongGaoInfo", name = "根据车辆型号查询第一条公告信息")
	@RequestMapping(value = "getGongGaoInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGongGaoInfo(String clxh) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = this.pDAServiceManager.getGongGaoInfo(clxh);
//		map.put("BH", "001");
//		map.put("CLLX", "K18");
		return map;
	}
	
	

}
