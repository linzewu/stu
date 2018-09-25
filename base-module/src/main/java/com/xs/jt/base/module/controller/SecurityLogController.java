package com.xs.jt.base.module.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.dao.SecurityLogRepository;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.manager.ISecurityLogManager;



@Controller
@RequestMapping(value = "/securityLog")
@Modular(modelCode="SecurityLog",modelName="安全日志管理",isEmpowered=false)
public class SecurityLogController {
	
	@Resource(name = "securityLogManager")
	private ISecurityLogManager securityLogManager;
	
	@UserOperation(code="getSecurityLog",name="安全日志查询")
	@RequestMapping(value = "getSecurityLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSecurityLog(Integer page, Integer rows, SecurityLog securityLog) {		
//		List<SecurityLog> vcps = securityLogManager.getSecurityLog(page, rows, securityLog);
//		
//		Integer total = securityLogManager.getSecurityLogCount(page, rows, securityLog);
//		
//		Map<String,Object> data =new HashMap<String,Object>();
//		
//		data.put("rows", vcps);
//		data.put("total", total);	
		
		return securityLogManager.getSecurityLogs(page-1, rows, securityLog);
	}

}
