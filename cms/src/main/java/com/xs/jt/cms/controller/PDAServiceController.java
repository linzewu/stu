package com.xs.jt.cms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutService;
import com.xs.jt.cms.common.CommonUtil;
import com.xs.jt.cms.common.URLCodeUtil;
import com.xs.jt.cms.entity.MotorVehiclePhotos;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.manager.IMotorVehicleBusinessLockManager;
import com.xs.jt.cms.manager.IMotorVehiclePhotosManager;
import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.manager.IVehCheckInfoManager;
import com.xs.jt.cms.manager.IPreCarRegisterManager;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "/pdaService")
@Modular(modelCode = "pdaService", modelName = "PDA对外接口")
public class PDAServiceController {

	@Autowired
	private IVehCheckInfoManager policeCheckInfoManager;
	@Autowired
	private IPDAServiceManager pDAServiceManager;
	@Autowired
	private IMotorVehiclePhotosManager motorVehiclePhotosManager;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private IMotorVehicleBusinessLockManager motorVehicleBusinessLockManager;
	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;

	@Autowired
	private TmriJaxRpcOutService tmriJaxRpcOutService;


	@RequestMapping(value = "addPoliceCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addPoliceCheckInfo(VehCheckInfo policeCheckInfo, BindingResult result)
			throws Exception {

		if (!result.hasErrors()) {
			policeCheckInfoManager.save(policeCheckInfo);
			return ResultHandler.resultHandle(result, policeCheckInfo, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}
	@RequestMapping(value = "findPreCarRegisterByLsh", method = RequestMethod.POST)
	public @ResponseBody PreCarRegister findPreCarRegisterByLsh(String lsh){
		PreCarRegister carInfo = preCarRegisterManager.findPreCarRegisterByLsh(lsh);
		if(carInfo != null){
			carInfo.setSdzt(motorVehicleBusinessLockManager.findMotorVehicleBusinessLockByClsbdh(carInfo.getClsbdh()));
		}
		return carInfo;
	}

	@UserOperation(code = "getCarInfoByCarNumber", name = "获取基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getCarInfoByCarNumber(String hpzl, String hphm) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null || "".equals(hphm.trim())) {
				return dataMap;
			}
			qo.setJkid("01C21");
			qo.setUTF8XmlDoc(
					"<root><QueryCondition><hphm>" + hphm + "</hphm><hpzl>" + hpzl + "</hpzl></QueryCondition></root>");

			String returnXML = trias.queryObjectOut(qo).getQueryObjectOutReturn();
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
					if(key.equals("clsbdh")){
						boolean sdzt = motorVehicleBusinessLockManager.findMotorVehicleBusinessLockByClsbdh(value);
						dataMap.put("sdzt", String.valueOf(sdzt));
					}
				}
			}

		} catch (Exception e) {
			throw new  ApplicationException("获取基础信息异常",e);
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

	@UserOperation(code = "getGongGaoListbyCLXH", name = "获取公告列表")
	@RequestMapping(value = "getGongGaoListbyCLXH", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getGongGaoListbyCLXH(String clxh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (clxh != null) {
			list = pDAServiceManager.findGongGaoListbyCLXH(clxh);

		}
		return list;
	}

	private void convertData(List list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Object o : list) {
			Map map = (Map) o;
			Date date1 = (Date) map.get("GGRQ");
			Date date2 = (Date) map.get("GGSXRQ");
			Date date3 = (Date) map.get("TZSCRQ");
			if (date1 != null) {
				map.put("GGRQ", sdf.format(date1));
			}
			if (date2 != null) {
				map.put("GGSXRQ", sdf.format(date2));
			}
			if (date3 != null) {
				map.put("TZSCRQ", sdf.format(date3));
			}
			if (map.get("ZXXS") != null) {
				map.put("ZXXS", CommonUtil.convertCode("zxxs", map.get("ZXXS").toString(), request));
			}
			if (map.get("RLZL") != null) {
				map.put("RLZL", CommonUtil.convertCode("rlzl", map.get("RLZL").toString(), request));
			}
		}
	}

	// 获取地盘公告
	@UserOperation(code = "getListOfDPGG", name = "获取地盘公告列表")
	@RequestMapping(value = "getListOfDPGG", method = RequestMethod.POST)
	public List<Map<String, Object>> getListOfDPGG(String dpid) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(dpid)) {
			list = pDAServiceManager.getListOfDPGG(dpid);
			convertData(list);
		}
		return list;

	}

	// 获取地盘公告
	@UserOperation(code = "getDPGG", name = "获取地盘公告信息")
	@RequestMapping(value = "getDPGG", method = RequestMethod.POST)
	public List<Map<String, Object>> getDPGG(String bh) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(bh)) {
			list = pDAServiceManager.getDPGG(bh);

			convertData(list);
		}
		return list;

	}

	@UserOperation(code = "uploadFile", name = "上传图片")
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadFile(MotorVehiclePhotos motorVehiclePhotos, MultipartFile file,
			@ApiIgnore() BindingResult result) throws Exception {
		motorVehiclePhotos.setPhoto(file.getBytes());
		motorVehiclePhotosManager.save(motorVehiclePhotos);
		return ResultHandler.resultHandle(result, motorVehiclePhotos, Constant.ConstantMessage.SAVE_SUCCESS);
	}
	
	@RequestMapping(value = "findLockMotorVehicleByClsbdh", method = RequestMethod.POST)
	public @ResponseBody boolean findLockMotorVehicleByClsbdh(String clsbdh)
			throws Exception {

		return motorVehicleBusinessLockManager.findMotorVehicleBusinessLockByClsbdh(clsbdh);
	}

}
