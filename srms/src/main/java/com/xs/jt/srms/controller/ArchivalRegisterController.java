package com.xs.jt.srms.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;
import com.xs.jt.srms.manager.IArchivalRegisterManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/archivalRegister")
@Modular(modelCode = "archivalRegister", modelName = "档案出入库登记")
public class ArchivalRegisterController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IArchivalRegisterManager archivalRegisterManager;
	
	@Transactional
	@UserOperation(code="archivalCheckOut",name="档案出库")
	@RequestMapping(value = "archivalCheckOut", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String archivalCheckOut(ArchivalCase archivalCase){		
		this.archivalRegisterManager.archivalCheckOut(archivalCase);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@Transactional
	@UserOperation(code="archivalCheckIn",name="档案入库")
	@RequestMapping(value = "archivalCheckIn", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String archivalCheckIn(ArchivalCase archivalCase){		
		this.archivalRegisterManager.archivalCheckIn(archivalCase);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@UserOperation(code = "getArchivalRegisterList", name = "查询档案出入库信息")
	@RequestMapping(value = "getArchivalRegisterList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalRegisterList(Integer page, Integer rows, ArchivalRegister archivalRegister) {
		return archivalRegisterManager.getArchivalRegisterList(page - 1, rows, archivalRegister);
	}
	
	@UserOperation(code = "getArchivalRegistersByHandleUser", name = "查询当前用户入库档案信息")
	@RequestMapping(value = "getArchivalRegistersByHandleUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalRegistersByHandleUser(Integer page, Integer rows, ArchivalRegister archivalRegister) {
		User user = (User) session.getAttribute("user");
		archivalRegister.setHandleUser(user.getYhm());
		archivalRegister.setZt(ArchivalCase.ZT_RK);
		return archivalRegisterManager.getArchivalRegisterList(page - 1, rows, archivalRegister);
	}

}
