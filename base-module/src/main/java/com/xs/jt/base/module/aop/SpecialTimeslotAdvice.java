package com.xs.jt.base.module.aop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.xs.jt.base.module.common.SpecialTimeslotException;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.ISecurityLogManager;
import com.xs.jt.base.module.manager.impl.SecurityAuditPolicySettingManagerImpl;


@Aspect
@Component
public class SpecialTimeslotAdvice {
	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	@Autowired
	private SecurityAuditPolicySettingManagerImpl sapsImpl;
	
	@Pointcut("execution(* com.xs.jt..*.controller.*.*(..))")
	@Order(0)
	private void controllerAspect() {
		
	}
	
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		SecurityAuditPolicySetting saps = sapsImpl.getPolicyByCode(SecurityAuditPolicySetting.SPECIAL_TIMESLOT_VISIT);
		if(saps != null) {
			String timeClz =saps.getClz();
			if(!StringUtils.isEmpty(timeClz)) {
				String[] times =  timeClz.split(",");
				
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date currentDate=Calendar.getInstance().getTime();
				
				for(String time:times) {
					String[] timeStr =time.split("-");
					String startTime =timeStr[0];
					String endTime =timeStr[1];
					String[] startHHMM = startTime.split(":");
					String startHH=startHHMM[0];
					String startMM=startHHMM[1];
					Calendar startCalendar = Calendar.getInstance();
					startCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startHH));
					startCalendar.set(Calendar.MINUTE, Integer.valueOf(startMM));
					String[] endHHMM = endTime.split(":");
					String endHH=endHHMM[0];
					String endMM=endHHMM[1];
					
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endHH));
					endCalendar.set(Calendar.MINUTE, Integer.valueOf(endMM));
					if(currentDate.getTime()>startCalendar.getTimeInMillis()&&currentDate.getTime()<endCalendar.getTimeInMillis()) {
						
						SecurityLog securityLog = new SecurityLog();
						securityLog.setCreateUser(User.SYSTEM_USER);
						securityLog.setUpdateUser(User.SYSTEM_USER);
						securityLog.setIpAddr(Common.getIpAdrress(request));
						securityLog.setClbmmc("规定时段外访");
						securityLog.setClbm(SecurityAuditPolicySetting.SPECIAL_TIMESLOT_VISIT);
						securityLog.setSignRed("N");
						securityLog.setContent("规定时段外访问系统，拒绝访问。");
						securityLog.setResult("拒绝访问");
						securityLogManager.saveSecurityLog(securityLog);
						session.invalidate();
						throw new SpecialTimeslotException("规定时段外访问系统，拒绝访问。");
					}
					
				}
			}
		}
		 
		 
		
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
