package com.xs.jt.base.module.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.manager.IOperationLogManager;


@Controller
@RequestMapping(value = "/opeationLog")
@Modular(modelCode="OperationLog",modelName="日志管理",isEmpowered=false)
public class OperationLogController {
	
	
	@Resource(name = "operationLogManager")
	private IOperationLogManager operationLogManager;
	
	@UserOperation(code="getOperationLog",name="日志查询")
	@RequestMapping(value = "getOperationLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getOperationLog(Integer page, Integer rows, OperationLog operationLog) {
				
		return this.operationLogManager.getOperationLogs(page-1, rows, operationLog);
	}
	
	@UserOperation(code="getLoginOperationLog",name="登录日志查询")
	@RequestMapping(value = "getLoginOperationLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog) {
			
		
		return this.operationLogManager.getLoginOperationLogs(page-1, rows, operationLog);
	}

}
