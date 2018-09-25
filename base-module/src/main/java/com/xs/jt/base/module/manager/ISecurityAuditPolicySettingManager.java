package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;


public interface ISecurityAuditPolicySettingManager {
	
//	public List<SecurityAuditPolicySetting> getList(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting) ;
//
//	public Integer getListCount(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting);
	
	public void updateSecurityAuditPolicySetting(List<SecurityAuditPolicySetting> list);
	
	public SecurityAuditPolicySetting getPolicyByCode(String aqsjclbm);
	
	public Map<String,Object> getSecurityAuditPolicySettings(Integer page, Integer rows,SecurityAuditPolicySetting setting);
}
