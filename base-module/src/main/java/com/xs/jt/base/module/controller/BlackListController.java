package com.xs.jt.base.module.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.RecordLog;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BlackList;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBlackListManager;
import com.xs.jt.base.module.manager.ISecurityLogManager;


@Controller
@RequestMapping(value = "/blackList",produces="application/json")
@Modular(modelCode="blackList",modelName="黑名单管理",isEmpowered=false)
public class BlackListController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name = "blackListManager")
	private IBlackListManager blackListManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	@UserOperation(code="getBlackList",name="查询黑名单")
	@RequestMapping(value = "getBlackList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getBlackList(Integer page, Integer rows, BlackList black) {				
		
		return blackListManager.getBlackLists(page-1, rows, black);
	}
	
	@UserOperation(code="save",name="保存黑名单")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveBlackList(HttpSession session,@Valid BlackList blackList, BindingResult result) {
		if (!result.hasErrors()) {
			User user = (User)session.getAttribute("user");
			blackList.setCreateBy(user.getYhm());
			blackList.setLastUpdateTime(new Date());
			blackList.setFailCount(0);
			blackList.setEnableFlag("Y");
			this.blackListManager.saveBlackList(blackList);
			return  ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result,null ,null);
		}
	}
	
	@RecordLog
	@UserOperation(code="delete",name="删除黑名单")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(BlackList blackList,HttpSession session){
		User user = (User)session.getAttribute("user");
		this.blackListManager.deleteBlackList(blackList);
		//写入安全日志
		SecurityLog securityLog = new SecurityLog();
		securityLog.setCreateUser(User.SYSTEM_USER);
		securityLog.setUpdateUser(User.SYSTEM_USER);
		securityLog.setClbm(SecurityAuditPolicySetting.IP_LOCK);
		securityLog.setIpAddr(Common.getIpAdrress(request));
		securityLog.setContent("用户："+user.getYhm()+" 删除黑名单管理IP："+blackList.getIp());
		securityLogManager.saveSecurityLog(securityLog);
	}

}
