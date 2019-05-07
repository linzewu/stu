package com.xs.jt.base.module.aop;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.annotation.CheckBit;
import com.xs.jt.base.module.entity.BaseEntity;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.ICoreFunctionManager;
import com.xs.jt.base.module.manager.IOperationLogManager;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;
import com.xs.jt.base.module.manager.ISecurityLogManager;

import net.sf.json.JSONObject;

@Component
@Aspect
public class CheckBitAopAfter {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ICoreFunctionManager coreFunctionManager;
	
	@Autowired
	private IOperationLogManager operationLogManager;

	@Autowired
	private ServletContext servletContext;
	
	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	/**
	 * 方法结束执行
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@AfterReturning(returning="rvt",pointcut="execution(* com.xs.jt.*.*.dao.*.find*(..)) || execution(* com.xs.jt.*.dao.*.find*(..)) || execution(* com.xs.jt.*.*.dao.*.get*(..)) || execution(* com.xs.jt.*.dao.*.get*(..))")
	public void after(JoinPoint joinPoint,Object rvt) throws NoSuchMethodException, SecurityException {
		if(rvt instanceof Page) {
			Page page = (Page)rvt;			
			if(page.getContent() instanceof List) {
				List array = (List)page.getContent();
				for(Object o : array) {
					if(o instanceof BaseEntity&&isCheckBitAnnotation(o)) {
						BaseEntity be = (BaseEntity)o;
						be.checkBit();
						recordLog(joinPoint, be);
					}
				}
			}
		}
		else {
			if(rvt instanceof BaseEntity&&isCheckBitAnnotation(rvt)) {
				BaseEntity be = (BaseEntity)rvt;
				be.checkBit();
				recordLog(joinPoint, be);
			}
		}
	}

	private void recordLog(JoinPoint joinPoint, BaseEntity be) throws NoSuchMethodException {
		if(!be.isCheckBitOk()) {
			SecurityAuditPolicySetting set = securityAuditPolicySettingManager.getPolicyByCode(SecurityAuditPolicySetting.DATA_TAMPERINT);
			if(set!=null) {
				if("0".equals(set.getSfkq())) {
					//写入安全日志
					SecurityLog securityLog = new SecurityLog();
					securityLog.setCreateUser(User.SYSTEM_USER);
					securityLog.setUpdateUser(User.SYSTEM_USER);
					securityLog.setClbm(SecurityAuditPolicySetting.DATA_TAMPERINT);
					securityLog.setIpAddr(getIpAdrress());
					securityLog.setSignRed("Y");
					securityLog.setContent("数据非法篡改,篡改数据："+JSONObject.fromObject(be).toString());
					securityLogManager.saveSecurityLog(securityLog);
				}
			}
			
		}
	}
	
	private boolean isCheckBitAnnotation(Object o) {
		
		boolean flag =false;
		
		CheckBit checkBit = o.getClass().getAnnotation(CheckBit.class);
		
		if(checkBit!=null) {
			flag=true;
		}
		
		return flag;
		
	}
	
	private String getIpAdrress() {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				return XFor.substring(0, index);
			} else {
				return XFor;
			}
		}
		XFor = Xip;
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			return XFor;
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		return XFor;
	}
	

}
