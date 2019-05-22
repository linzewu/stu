package com.xs.jt.base.module.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;


@Controller
@RequestMapping(value = "/securityAuditPolicySetting")
@Modular(modelCode="securityAuditPolicySetting",modelName="安全策略",isEmpowered=false,jsjb= {Role.JSJB_STGL,Role.JSJB_AQGL})
public class SecurityAuditPolicySettingController {	
	
	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;
	
	@UserOperation(code="getPolicySettingList",name="查询安全策略")
	@RequestMapping(value = "getPolicySettingList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getPolicySettingList(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting) {		
		
		return securityAuditPolicySettingManager.getSecurityAuditPolicySettings(page-1, rows, securityAuditPolicySetting);
	}
	
	@UserOperation(code="save",name="编辑安全策略")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map save(@RequestBody SecurityAuditPolicySetting svo, BindingResult result) {
		
		if (!result.hasErrors()) {
			this.securityAuditPolicySettingManager.updateSecurityAuditPolicySetting(svo.getUpdateList());
			return  ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result,null ,null);
		}
	}

}
