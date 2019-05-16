package com.xs.jt.srms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.manager.IArchivalRegisterManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/archivalCase")
@Modular(modelCode = "archivalCase", modelName = "档案管理")
public class ArchivalCaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ArchivalCaseController.class);
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IArchivalRegisterManager archivalRegisterManager;
	
	@UserOperation(code = "getArchivalCaseList", name = "档案查询")
	@RequestMapping(value = "getArchivalCaseList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_WSY);
		return archivalCaseManager.getUsedArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getAllArchivalCaseList", name = "档案架使用情况查询")
	@RequestMapping(value = "getAllArchivalCaseList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		return archivalCaseManager.getUsedArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getArchivalCaseCheckInList", name = "查询已入库档案信息")
	@RequestMapping(value = "getArchivalCaseCheckInList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseCheckInList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_RK);
		return archivalCaseManager.getArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getArchivalCaseCheckOutList", name = "查询已出库档案信息")
	@RequestMapping(value = "getArchivalCaseCheckOutList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseCheckOutList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_CK);
		return archivalCaseManager.getArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	
	@UserOperation(code="findUseArchivalCase",name="查询档案架中是否有档案格在使用",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "findUseArchivalCase", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findUseArchivalCase(ArchivalCase archivalCase){
		String flag = "false";
		List<ArchivalCase> useCase = this.archivalCaseManager.findUseArchivalCase(ArchivalCase.ZT_WSY, archivalCase.getRackNo());
		if(useCase != null && useCase.size() > 0) {
			flag = "true";	
		}		
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, flag);	
	}
	
	
	@UserOperation(code = "findCarInfoByBarCode", name = "根据条码查询车辆信息",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "findCarInfoByBarCode", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findCarInfoByBarCode(String barCode){
		List<Map<String,Object>> list = archivalCaseManager.getCarInfoByBarcode(barCode);
		Map<String, Object> data = new HashMap<String, Object>();
		if(!CollectionUtils.isEmpty(list)) {
			data = list.get(0);
		}
		
//		data.put("hphm", "UC6123");
//		data.put("hpzl", "02");
//		data.put("clsbdh", "LGBH52E27HY017233");
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "根据条码查询车辆信息成功", data);
	}
	

	
	////
	@UserOperation(code = "getArchivalRegistersByHandleUser", name = "查询当前用户入库档案信息",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getArchivalRegistersByHandleUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalRegistersByHandleUser(Integer page, Integer rows, ArchivalRegister archivalRegister) {
		User user = (User) session.getAttribute("user");
		archivalRegister.setHandleUser(user.getYhm());
		archivalRegister.setZt(ArchivalCase.ZT_RK);
		return archivalRegisterManager.getArchivalRegisterList(page - 1, rows, archivalRegister);
	}
	
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
	
	@UserOperation(code = "getArchivalRegisterList", name = "出入库记录")
	@RequestMapping(value = "getArchivalRegisterList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalRegisterList(Integer page, Integer rows, ArchivalRegister archivalRegister) {
		return archivalRegisterManager.getArchivalRegisterList(page - 1, rows, archivalRegister);
	}
	
	

}
