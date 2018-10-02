package com.xs.jt.cms.controller;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutAccessServiceStub;
import com.xs.jt.cms.common.MatrixToImageWriter;
import com.xs.jt.cms.common.URLCodeUtil;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.manager.IPreCarRegisterManager;

@Controller
@RequestMapping(value = "/preCarRegister")
@Modular(modelCode = "preCarRegister", modelName = "车辆预登记")
public class PreCarRegisterController {

	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;

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
			TmriJaxRpcOutAccessServiceStub trias = new TmriJaxRpcOutAccessServiceStub();
			TmriJaxRpcOutAccessServiceStub.QueryObjectOut qo = new TmriJaxRpcOutAccessServiceStub.QueryObjectOut();

			qo.setJkid("01C24");
			qo.setJkxlh(
					"7F1C0909010517040815E3FF83F5F3E28BCC8F9B818DE7EA88DFD19EB8C7D894B9B9BCE0BFD8D6D0D0C4A3A8D0C5CFA2BCE0B9DCCFB5CDB3A3A9");
			qo.setUTF8XmlDoc("<root><QueryCondition></QueryCondition></root>");
			qo.setXtlb("01");

			String returnXML = trias.queryObjectOut(qo).getQueryObjectOutReturn();

			Document doc = DocumentHelper.parseText(returnXML);
			Element root = doc.getRootElement();
			lsh = root.element("body").element("veh").element("lsh").getText();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	@UserOperation(code = "getCarInfoByCarNumber", name = "获取基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> getCarInfoByCarNumber(String hpzl,String hphm) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutAccessServiceStub trias = new TmriJaxRpcOutAccessServiceStub();
			TmriJaxRpcOutAccessServiceStub.QueryObjectOut qo = new TmriJaxRpcOutAccessServiceStub.QueryObjectOut();
			
			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null
					|| "".equals(hphm.trim())) {
				return dataMap;
			}

			qo.setJkid("01C21");
			qo.setJkxlh("7F1C0909010517040815E3FF83F5F3E28BCC8F9B818DE7EA88DFD19EB8C7D894B9B9BCE0BFD8D6D0D0C4A3A8D0C5CFA2BCE0B9DCCFB5CDB3A3A9");
			qo.setUTF8XmlDoc("<root><QueryCondition><hphm>" + hphm
					+ "</hphm><hpzl>" + hpzl
					+ "</hpzl></QueryCondition></root>");
			qo.setXtlb("01");

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
				//pw.print(JSONObject.fromObject(dataMap));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}

}
