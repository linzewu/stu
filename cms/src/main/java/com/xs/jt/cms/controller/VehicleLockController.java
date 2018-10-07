package com.xs.jt.cms.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.manager.IVehicleLockManager;

@Controller
@RequestMapping(value = "/vehicleLock")
@Modular(modelCode = "vehicleLock", modelName = "机动车业务锁定管理")
public class VehicleLockController {
	@Autowired
	private IVehicleLockManager vehicleLockManager; 
	@Autowired
	private HttpSession session;
	
	@UserOperation(code="getVehicleLocks",name="查询机动车业务锁定")
	@RequestMapping(value = "getVehicleLocks", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getVehicleLock(Integer page, Integer rows, VehicleLock vehicleLock) {
		return vehicleLockManager.getVehicleLocks(page-1, rows, vehicleLock);
	}
	
	@UserOperation(code="save",name="保存机动车业务锁定信息")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map save(VehicleLock vehicleLock) {
		User user = (User) session.getAttribute("user");
		vehicleLock.setSdr(user.getYhm());
		vehicleLock.setSdsj(new Date());
		vehicleLock.setSdzt("1");
		vehicleLock.setSdfs(VehicleLock.SDFS_BYHAND);
		vehicleLock = this.vehicleLockManager.save(vehicleLock);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功",vehicleLock);
	}
	
	@UserOperation(code="unLock",name="解锁机动车业务锁定信息")
	@RequestMapping(value = "unLock", method = RequestMethod.POST)
	public @ResponseBody Map unLock(VehicleLock vehicleLock) {
		User user = (User) session.getAttribute("user");
		vehicleLock.setJsr(user.getYhm());
		vehicleLock.setJssj(new Date());
		vehicleLock.setSdzt("0");
		vehicleLock = this.vehicleLockManager.save(vehicleLock);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "解锁成功",vehicleLock);
	}

}
