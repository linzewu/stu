package com.xs.jt.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.manager.IVehCheckInfoManager;

@Controller
@RequestMapping(value = "/vheCheckInfo")
@Modular(modelCode = "vheCheckInfo", modelName = "车辆查验信息")
public class VehCheckInfoController {
	
	@Autowired
	private IVehCheckInfoManager vehCheckInfoManager;
	
	
	@UserOperation(code="getCheckInfoList",name="查询查验列表")
	@RequestMapping(value = "getCarList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getCarList(Integer page, Integer rows, VehCheckInfo vehCheckInfo) {			
		
		return vehCheckInfoManager.getVehCheckInfoList(page-1, rows, vehCheckInfo);
	}
	
	@UserOperation(code="getVehCheckReport",name="查询查验报表")
	@RequestMapping(value = "getVehCheckReport", method = RequestMethod.POST)
	public @ResponseBody String getVehCheckReport(Integer id) throws Exception {
		
		return vehCheckInfoManager.getVehCheckReport(id);
	}

	
	
}
