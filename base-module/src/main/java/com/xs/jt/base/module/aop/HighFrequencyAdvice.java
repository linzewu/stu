package com.xs.jt.base.module.aop;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.HighFrequencyException;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.ISecurityLogManager;

@Aspect
@Component
public class HighFrequencyAdvice {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@Resource(name="minHF")
	private Map<String,Integer> minHF;
	
	@Resource(name="hourHF")
	private Map<String,Integer> hourHF;
	
	@Resource(name="dayHF")
	private Map<String,Integer> dayHF;
	
	@Resource(name="hfMap")
	private Map<String,Integer> hfMap;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	
	@Pointcut("execution(* com.xs.jt..*.controller.*.*(..))")
	@Order(0)
	private void controllerAspect() {
		
	}
	
	/**
	 * 方法开始执行
	 * @throws Throwable 
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		String ip =getIpAdrress();
		
		Integer minCount =  minHF.get(ip);
		Integer hourCount =  hourHF.get(ip);
		Integer dayHFCount=dayHF.get(ip);
		
		Integer minHFNumber = hfMap.get("minHF");
		Integer hourHFNumber = hfMap.get("hourHF");
		Integer dayHFNumber = hfMap.get("dayHF");
		
		if(minCount!=null&&minCount>minHFNumber) {
			
			SecurityLog securityLog = new SecurityLog();
			securityLog.setCreateUser(User.SYSTEM_USER);
			securityLog.setUpdateUser(User.SYSTEM_USER);
			securityLog.setIpAddr(Common.getIpAdrress(request));
			securityLog.setClbmmc("高频访问");
			securityLog.setClbm(SecurityAuditPolicySetting.VISIT_NUMBER_ONEMINUTE);
			securityLog.setSignRed("Y");
			securityLog.setContent("高频访问，单前访问超过分钟高频阈值，拒绝访问！");
			securityLog.setResult("拒绝访问");
			securityLogManager.saveSecurityLog(securityLog);
			
			session.invalidate();
			throw new HighFrequencyException("高频访问，单前访问超过分钟高频阈值，拒绝访问！");
		}else if(hourCount!=null&&hourCount>hourHFNumber) {
			SecurityLog securityLog = new SecurityLog();
			securityLog.setCreateUser(User.SYSTEM_USER);
			securityLog.setUpdateUser(User.SYSTEM_USER);
			securityLog.setIpAddr(Common.getIpAdrress(request));
			securityLog.setClbmmc("高频访问");
			securityLog.setClbm(SecurityAuditPolicySetting.VISIT_NUMBER_ONEHOUR);
			securityLog.setSignRed("Y");
			securityLog.setContent("高频访问，单前访问超过小时高频阈值，拒绝访问！");
			securityLog.setResult("拒绝访问");
			securityLogManager.saveSecurityLog(securityLog);
			
			session.invalidate();
			throw new HighFrequencyException("高频访问，单前访问超过小时高频阈值，拒绝访问！");
		}else if(dayHFCount!=null&&dayHFCount>dayHFNumber) {
			SecurityLog securityLog = new SecurityLog();
			securityLog.setCreateUser(User.SYSTEM_USER);
			securityLog.setUpdateUser(User.SYSTEM_USER);
			securityLog.setIpAddr(Common.getIpAdrress(request));
			securityLog.setClbmmc("高频访问");
			securityLog.setClbm(SecurityAuditPolicySetting.VISIT_NUMBER_ONEDAY);
			securityLog.setSignRed("Y");
			securityLog.setContent("高频访问，单前访问超过每天高频阈值，拒绝访问！");
			securityLog.setResult("拒绝访问");
			securityLogManager.saveSecurityLog(securityLog);
			
			session.invalidate();
			throw new HighFrequencyException("高频访问，单前访问超过每天高频阈值，拒绝访问！");
		}
		
		minHF.put(ip, (minCount==null?0:minCount)+1);
		hourHF.put(ip, (hourCount==null?0:hourCount)+1);
		dayHF.put(ip,(dayHFCount==null?0:dayHFCount)+1);
		
		return pjp.proceed();
		
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
