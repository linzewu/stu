package com.xs.jt.base.module.manager;

import java.util.Map;

import com.xs.jt.base.module.entity.SecurityLog;



public interface ISecurityLogManager {
	
//	public List<SecurityLog> getSecurityLog(Integer page, Integer rows, SecurityLog securityLog);
//	
//	public Integer getSecurityLogCount(Integer page, Integer rows, SecurityLog securityLog);
	
	public void saveSecurityLog(SecurityLog securityLog);
	
	public Map<String,Object> getSecurityLogs(Integer page, Integer rows,SecurityLog securityLog);

}
