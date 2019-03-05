package com.xs.jt.base.module.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BlackList;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.manager.IBlackListManager;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;

@Component("blackListJob")
public class BlackListJob {

	@Resource(name = "blackListManager")
	private IBlackListManager blackListManager;

	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;

	/**
	 * 每天0点删除所有黑名单
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ? ")
	private void deleteBlackList() throws Exception {
		blackListManager.deleteSystemBlackList();
	}

	@Scheduled(cron = "0 0/5 * * * ? ")
	private void unlockedBlackList() throws Exception {
		SecurityAuditPolicySetting set = securityAuditPolicySettingManager
				.getPolicyByCode(SecurityAuditPolicySetting.IP_LOCK);
		List<BlackList> list = blackListManager.getEnableList();
		
		if (set != null) {
			int clz = set.getClz() == null ? 0 : Integer.parseInt(set.getClz());
			for (BlackList black : list) {
				int hours = (int) (((new Date()).getTime() - black.getLastUpdateTime().getTime()) / (1000 * 60 * 60));
				if (black.getFailCount() >= clz && hours >= 1) {
					black.setEnableFlag("N");
					black.setFailCount(0);
					black.setLastUpdateTime(new Date());
					blackListManager.saveBlackList(black);
				}
			}
		}
	}

}
