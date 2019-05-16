package com.xs.jt.srms.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.srms.entity.StoreRoom;

@Controller
@RequestMapping(value = "/driverFiles")
@Modular(modelCode = "driverFiles", modelName = "驾驶人档案管理")
public class DriverFilesController {
	
	@UserOperation(code = "getDriverFilesList", name = "驾驶人档案查询")
	@RequestMapping(value = "getDriverFilesList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDriverFilesList(Integer page, Integer rows, StoreRoom storeRoom) {
		return null;
	}

}
