package com.xs.jt.cms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutService;
import com.xs.jt.cms.common.MatrixToImageWriter;
import com.xs.jt.cms.common.URLCodeUtil;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.manager.IPreCarRegisterManager;

@Controller
@RequestMapping(value = "/preCarRegister")
@Modular(modelCode = "preCarRegister", modelName = "车辆预登记")
public class PreCarRegisterController {
	
	private static Logger logger = LoggerFactory.getLogger(PreCarRegisterController.class);

	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;
	
	private IPDAServiceManager pDAServiceManager;
	
	private TmriJaxRpcOutService tmriJaxRpcOutService;
	
	@Value("${stu.properties.glbm}")
	private String glbm;

	@UserOperation(code = "savePreCarRegister", name = "保存")
	@RequestMapping(value = "savePreCarRegister", method = RequestMethod.POST)
	public @ResponseBody Map savePreCarRegister(HttpSession session, @Valid PreCarRegister bcr, BindingResult result) {
		if (!result.hasErrors()) {
			User user = (User) session.getAttribute("user");

			//Map userMap = (Map) request.getSession().getAttribute("user");
			String stationCode = (String) user.getBmdm();// ---------------

			bcr.setStationCode(stationCode);

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

			String lsh = null;

			if ("A".equals(bcr.getYwlx())) {
				lsh = getlsh();
				bcr.setLsh(lsh);
			}
			sb.append("|");
			sb.append(bcr.getLsh());
			sb.append("|");
			sb.append(bcr.getHphm());
			sb.append("|");

			if (null == bcr.getDpid() || "".equals(bcr.getDpid().trim())) {
				bcr.setDpid(null);
			}

			sb.append(bcr.getDpid());

			PreCarRegister register = this.preCarRegisterManager.save(bcr); 

			System.out.println(sb);
			String path = System.getProperty("2code");
			create2Code(path, sb.toString(), String.valueOf(register.getId()));
			if ("A".equals(bcr.getYwlx())) {
				createLSHCode(path,String.valueOf(register.getId()), lsh);
			}

//			respondData.put(BaseManagerAction.SID, id);
//			respondData.put(BaseManagerAction.STATE, BaseManagerAction.STATE_SUCCESS);
//			pw.print(respondData);
			return ResultHandler.resultHandle(result, null, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
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
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
			qo.setJkid("01C24");
			qo.setUTF8XmlDoc("<root><QueryCondition><glbm>"+glbm+"</glbm></QueryCondition></root>");
			String returnXML = trias.queryObjectOut(qo).getQueryObjectOutReturn();
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

	private void createLSHCode(String path,String fileName, String lsh) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		try {
			BitMatrix bitMatrix = toBarCodeMatrix(lsh, 10, 20);
			File file1 = new File(path, fileName + "code39.jpg");
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * JBarcode localJBarcode = new JBarcode(Code39Encoder.getInstance(),
		 * WideRatioCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
		 * 
		 * localJBarcode.setShowCheckDigit(false); BufferedImage localBufferedImage =
		 * localJBarcode.createBarcode(lsh);
		 * OneBarcodeUtil.saveToJPEG(localBufferedImage, fileName + "code39.jpg");
		 */

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
		return ResultHandler.resultHandle(result, null, Constant.ConstantMessage.SAVE_SUCCESS);
	}
	
	@UserOperation(code = "getCarInfoByCarNumber", name = "获取机动车基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getCarInfoByCarNumber(String hpzl,String hphm) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();

			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null
					|| "".equals(hphm.trim())) {
				return dataMap;
			}

			qo.setJkid("01C21");
			qo.setUTF8XmlDoc("<root><QueryCondition><hphm>" + hphm
					+ "</hphm><hpzl>" + hpzl
					+ "</hpzl></QueryCondition></root>");

			String returnXML = trias.queryObjectOut(qo)
					.getQueryObjectOutReturn();
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
	public @ResponseBody Map getGongGaoInfoByGgbh(String ggbh) {
		Map map = new HashMap();
		if (ggbh == null || "".equals(ggbh.trim())) {
			return map;
		}

		List<Map<String, Object>> list = pDAServiceManager.findPcbStVehicle(ggbh);

		Map<String, Object> rMap = (Map) list.get(0);

		Map returnMap = new HashMap();

		for (String key : rMap.keySet()) {
			returnMap.put(key.toLowerCase(), rMap.get(key));
		}

		return returnMap;

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
	
	

}
