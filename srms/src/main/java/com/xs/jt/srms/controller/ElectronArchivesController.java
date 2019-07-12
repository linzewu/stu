package com.xs.jt.srms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.srms.manager.IElectronArchivesManager;
import com.xs.jt.srms.manager.IVehFlowManager;
import com.xs.jt.srms.manager.IVehImgManager;
import com.xs.jt.srms.vehimg.entity.VehImg;
@Controller
@RequestMapping(value = "/electronArchives")
@Modular(modelCode = "electronArchives", modelName = "机动车电子档案管理")
public class ElectronArchivesController {
	
	@Autowired
	private IElectronArchivesManager electronArchivesManager;
	@Autowired
	private IVehFlowManager vehFlowManager;
	@Autowired
	private IVehImgManager vehImgManager;
	
	@UserOperation(code = "getVehCarInfosList", name = "机动车电子档案查询")
	@RequestMapping(value = "getVehCarInfosList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVehCarInfosList(Integer page, Integer rows, @RequestParam Map map) {
		return electronArchivesManager.getVehCarInfosList(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()), map);
	}
	
	@UserOperation(code = "getVehFlowList", name = "机动车电子档案流水信息", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getVehFlowList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getVehFlowList(String clsbdh) {
		List<Map<String,Object>> list = vehFlowManager.getVehFlowByClsbdh(clsbdh);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "查询机动车电子档案流水信息成功！", list);
	}
	
	@UserOperation(code = "getPhotosByLsh", name = "查看电子档案照片", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getPhotosByLsh", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getPhotosByLsh(String lsh) {
		List<VehImg> photos = this.vehImgManager.getVehImgByLsh(lsh);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "查看电子档案照片成功", photos);
	}

}
