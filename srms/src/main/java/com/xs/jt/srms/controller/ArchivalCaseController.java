package com.xs.jt.srms.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.manager.IArchivalCaseManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/archivalCase")
@Modular(modelCode = "archivalCase", modelName = "档案管理")
public class ArchivalCaseController {
	
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@UserOperation(code = "getArchivalCaseList", name = "查询未使用档案信息")
	@RequestMapping(value = "getArchivalCaseList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_WSY);
		return archivalCaseManager.getUsedArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getAllArchivalCaseList", name = "查询所有档案信息")
	@RequestMapping(value = "getAllArchivalCaseList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		return archivalCaseManager.getUsedArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getArchivalCaseCheckInList", name = "查询档案已入库信息")
	@RequestMapping(value = "getArchivalCaseCheckInList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseCheckInList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_RK);
		return archivalCaseManager.getArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	@UserOperation(code = "getArchivalCaseCheckOutList", name = "查询档案出库信息")
	@RequestMapping(value = "getArchivalCaseCheckOutList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalCaseCheckOutList(Integer page, Integer rows, ArchivalCase archivalCase) {
		archivalCase.setZt(ArchivalCase.ZT_CK);
		return archivalCaseManager.getArchivalCaseList(page - 1, rows, archivalCase);
	}
	
	
	@Transactional
	@UserOperation(code="newCarArchivalCheckIn",name="新车档案入库")
	@RequestMapping(value = "newCarArchivalCheckIn", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String newCarArchivalCheckIn(ArchivalCase archivalCase){		
		this.archivalCaseManager.newCarArchivalCheckIn(archivalCase);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@Transactional
	@UserOperation(code="UsedCarArchivalCheckIn",name="在用车档案入库")
	@RequestMapping(value = "UsedCarArchivalCheckIn", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String UsedCarArchivalCheckIn(ArchivalCase archivalCase){		
		this.archivalCaseManager.UsedCarArchivalCheckIn(archivalCase);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	
	@UserOperation(code="findUseArchivalCase",name="查询档案架中是否有档案格在使用")
	@RequestMapping(value = "findUseArchivalCase", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findUseArchivalCase(ArchivalCase archivalCase){
		String flag = "false";
		List<ArchivalCase> useCase = this.archivalCaseManager.findUseArchivalCase(ArchivalCase.ZT_WSY, archivalCase.getRackNo());
		if(useCase != null && useCase.size() > 0) {
			flag = "true";	
		}		
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, flag);	
	}
	
	
	@UserOperation(code = "findCheckOutLong", name = "查询超过三个工作日未入库档案")
	@RequestMapping(value = "findCheckOutLong", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findCheckOutLong(){
		DecimalFormat   df   =new   DecimalFormat("0.00");  
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> list = archivalCaseManager.findCheckOutLong();
		data.put("rows", list);
		return data;
	}
	

}
