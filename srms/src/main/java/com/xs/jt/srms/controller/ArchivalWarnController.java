package com.xs.jt.srms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.srms.entity.ArchivalWarn;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.manager.IArchivalWarnManager;

@Controller
@RequestMapping(value = "/archivalWarn")
@Modular(modelCode = "archivalWarn", modelName = "业务预警")
public class ArchivalWarnController {
	
	@Autowired
	private IArchivalWarnManager archivalWarnManager;
	
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@UserOperation(code = "getArchivalWarnList", name = "查询多次办理业务预警档案")
	@RequestMapping(value = "getArchivalWarnList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchivalWarnList(Integer page, Integer rows, ArchivalWarn archivalWarn) {
		return archivalWarnManager.getArchivalWarnList(page - 1, rows, archivalWarn);
	}
	
	/**
	 * 查询超过三个工作日未入库档案
	 * @return
	 */
	@UserOperation(code = "findCheckOutLong", name = "查询超过三个工作日未入库档案")
	@RequestMapping(value = "findCheckOutLong", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> findCheckOutLong(){
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> list = archivalCaseManager.findCheckOutLong();
		data.put("rows", list);
		return data;
	}

}
