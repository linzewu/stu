package com.xs.jt.veh.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.veh.entity.LimitStandard;
import com.xs.jt.veh.manager.ILimitStandardManager;

@Controller
@RequestMapping(value = "/limitStandard", produces = "application/json")
@Modular(modelCode = "limitStandard", modelName = "检测项目和标准限值", isEmpowered = false)
public class LimitStandardController {

	@Resource(name = "limitStandardManager")
	private ILimitStandardManager limitStandardManager;

	@UserOperation(code = "getAllLimitStandard", name = "获取所有检测项目和标准限值", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getAllLimitStandard", method = RequestMethod.POST)
	public @ResponseBody List<LimitStandard> getAllLimitStandard() {
		return limitStandardManager.getAllLimitStandard();
	}

}
