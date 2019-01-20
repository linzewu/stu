package com.xs.jt.veh.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.entity.DeviceMotion;
import com.xs.jt.veh.manager.IDeviceMotionManager;

@Controller
@RequestMapping(value = "/motion")
public class MotionController {

	@Resource(name = "deviceMotionManager")
	private IDeviceMotionManager deviceMotionManager;

//	@RequestMapping(value = "getMotions", method = RequestMethod.POST)
//	public @ResponseBody Map getUsers(User user, PageInfo pageInfo) {
//		Map json = ResultHandler.toMyJSON(deviceMotionManager.getMotions(),0);
//		return json;
//	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveUser(DeviceMotion deviceMotion, BindingResult result) {

		DeviceMotion dm = this.deviceMotionManager.saveDeviceMotion(deviceMotion);

		return ResultHandler.resultHandle(result, dm, Constant.ConstantMessage.SAVE_SUCCESS);

	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(DeviceMotion deviceMotion) {
		this.deviceMotionManager.deleteDeviceMotion(deviceMotion);
	}

}
