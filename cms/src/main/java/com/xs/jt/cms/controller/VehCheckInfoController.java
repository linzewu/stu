package com.xs.jt.cms.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.MapUtil;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.cms.common.BarcodeUtil;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.entity.VehiclePhotos;
import com.xs.jt.cms.manager.IVehCheckInfoManager;
import com.xs.jt.cms.manager.IVehiclePhotosManager;

@Controller
@RequestMapping(value = "/vheCheckInfo")
@Modular(modelCode = "vheCheckInfo", modelName = "车辆查验信息")
public class VehCheckInfoController {
	private static Logger logger = LoggerFactory.getLogger(VehCheckInfoController.class);

	@Value("${stu.cache.dir}")
	private String cacheDir;

	@Autowired
	private IVehCheckInfoManager vehCheckInfoManager;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private IVehiclePhotosManager vehiclePhotosManager;

	@UserOperation(code = "getCheckInfoList", name = "查询查验列表")
	@RequestMapping(value = "getCheckInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCheckInfoList(Integer page, Integer rows, VehCheckInfo vehCheckInfo) {

		return vehCheckInfoManager.getVehCheckInfoList(page - 1, rows, vehCheckInfo);
	}

	@UserOperation(code = "getVehCheckReport", name = "查询查验报表")
	@RequestMapping(value = "getVehCheckReport", method = RequestMethod.POST)
	public @ResponseBody String getVehCheckReport(Integer id) throws Exception {

		return vehCheckInfoManager.getVehCheckReport(id);
	}

	@UserOperation(code = "printCheckInfo", name = "打印查验单")
	@RequestMapping(value = "printCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map printCheckInfo(VehCheckInfo checkInfo) {
		try {
			if (StringUtils.isEmpty(checkInfo.getLsh())) {
				return ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "流水号不能为空", checkInfo.getLsh());
			}
			String template = "template_pt_qd";
			printing(checkInfo,template);

		} catch (Exception e) {
			logger.error("打印查验单异常", e);
			throw new ApplicationException("打印查验单异常", e);
		}
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "打印查验单成功", checkInfo.getLsh());
	}
	
	@UserOperation(code = "tdCheckInfo", name = "套打查验单")
	@RequestMapping(value = "tdCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map tdCheckInfo(VehCheckInfo checkInfo) {
		try {
			if (StringUtils.isEmpty(checkInfo.getLsh())) {
				return ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "流水号不能为空", checkInfo.getLsh());
			}
			String template = "template_pt_td";
			printing(checkInfo,template);

		} catch (Exception e) {
			logger.error("套打查验单异常", e);
			throw new ApplicationException("套打查验单异常", e);
		}
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "套打查验单成功", checkInfo.getLsh());
	}
	
	private void printing(VehCheckInfo checkInfo,String template) {
		try {			

			VehCheckInfo vci = vehCheckInfoManager.findVehCheckInfoByLshAndCycs(checkInfo.getLsh(),
					checkInfo.getCycs());
			Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext
					.getAttribute("bpsMap");

			Map<String, Object> data = MapUtil.object2Map(vci);
			transformData(data, bpsMap);
			data.put("lshCode", BarcodeUtil.generateInputStream(vci.getLsh()));
			if (StringUtils.isEmpty(vci.getHphm())) {
				data.put("hphm", vci.getClsbdh());
			}
			//车前斜视45度
			VehiclePhotos beforeImg = vehiclePhotosManager.findPhotosByLshAndZpzlAndJccs(vci.getLsh(),"11",vci.getCycs());
			if(beforeImg != null) {
				data.put("jdczp",new ByteArrayInputStream(beforeImg.getPhoto()));
			}
			//车辆识别代号
			VehiclePhotos sbdhImg = vehiclePhotosManager.findPhotosByLshAndZpzlAndJccs(vci.getLsh(),"30",vci.getCycs());
			if(sbdhImg != null) {
				data.put("clsbdhzp",new ByteArrayInputStream(sbdhImg.getPhoto()));
			}

			com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template+".doc", data, bpsMap);
			Sql2WordUtil.toCase(doc, cacheDir, "\\report\\"+template+"_01_" + vci.getLsh() + ".jpg");
		}catch(Exception e) {
			throw new ApplicationException(e);
		}
	}

	public Map<String, Object> transformData(Map<String, Object> data, Map<String, List<BaseParams>> bpsMap) {
		List<BaseParams> cyjg = bpsMap.get("cyjg");
		List<BaseParams> jyjg = bpsMap.get("jyjg");
		Map<String, String> jgMap = new HashMap<String, String>();
		for (BaseParams bp : cyjg) {
			jgMap.put(bp.getParamValue(), bp.getParamName());
		}
		for (String key : data.keySet()) {
			if (key.startsWith("jy") && Common.isInteger(key.substring(2))) {
				String value = jgMap.get(data.get(key));
				data.put(key, value);
			} else if ("cyjg".equals(key)) {
				for (BaseParams bp : jyjg) {
					if (bp.getParamValue().equals(data.get(key))) {
						data.put(key, bp.getParamName());
					}
				}
			}
		}
		return data;
	}

}
