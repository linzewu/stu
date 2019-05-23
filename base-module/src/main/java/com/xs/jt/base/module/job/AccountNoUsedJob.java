package com.xs.jt.base.module.job;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;
import com.xs.jt.base.module.manager.ISecurityLogManager;
import com.xs.jt.base.module.manager.IUserManager;

@Component
public class AccountNoUsedJob {
	
	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	
	
	/**
	 * 每天0点检测
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ? ")
	private void checkNoUsed() throws Exception {
		SecurityAuditPolicySetting set = securityAuditPolicySettingManager
				.getPolicyByCode(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AWEEK);
		
		if(set != null) {
			List<User> list = userManager.getUsersByZjdlsj(7);
			for(User user:list) {
				SecurityLog securityLog = new SecurityLog();
				securityLog.setCreateUser(User.SYSTEM_USER);
				securityLog.setUpdateUser(User.SYSTEM_USER);
				securityLog.setUserName(user.getYhm());
				securityLog.setClbm(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AWEEK);
				securityLog.setClbmmc(set.getAqsjcllxmc());
				securityLog.setSignRed("N");
				securityLog.setContent("用户:"+user.getYhm()+"违反账户长期未使用安全审计策略设置");
				securityLog.setResult("用户:"+user.getYhm()+"超过一周未登录系统");
				securityLogManager.saveSecurityLog(securityLog);
			}
		}
		
		SecurityAuditPolicySetting setMonth = securityAuditPolicySettingManager
				.getPolicyByCode(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AMONTH);
		
		if(setMonth != null) {
			List<User> list = userManager.getUsersByZjdlsj(30);
			for(User user:list) {
				SecurityLog securityLog = new SecurityLog();
				securityLog.setCreateUser(User.SYSTEM_USER);
				securityLog.setUpdateUser(User.SYSTEM_USER);
				securityLog.setUserName(user.getYhm());
				securityLog.setClbm(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AMONTH);
				securityLog.setClbmmc(setMonth.getAqsjcllxmc());
				securityLog.setSignRed("N");
				securityLog.setContent("用户:"+user.getYhm()+"违反账户长期未使用安全审计策略设置");
				securityLog.setResult("用户:"+user.getYhm()+"超过一个月未登录系统");
				securityLogManager.saveSecurityLog(securityLog);
			}
		}
		
		SecurityAuditPolicySetting setYear = securityAuditPolicySettingManager
				.getPolicyByCode(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AYEAR);
		
		if(setYear != null) {
			List<User> list = userManager.getUsersByZjdlsj(365);
			for(User user:list) {
				SecurityLog securityLog = new SecurityLog();
				securityLog.setCreateUser(User.SYSTEM_USER);
				securityLog.setUpdateUser(User.SYSTEM_USER);
				securityLog.setUserName(user.getYhm());
				securityLog.setClbm(SecurityAuditPolicySetting.ACCOUNT_NOT_USED_AYEAR);
				securityLog.setClbmmc(setYear.getAqsjcllxmc());
				securityLog.setSignRed("N");
				securityLog.setContent("用户:"+user.getYhm()+"违反账户长期未使用安全审计策略设置");
				securityLog.setResult("用户:"+user.getYhm()+"超过一年未登录系统");
				securityLogManager.saveSecurityLog(securityLog);
			}
		}
	}

}
