package com.xs.jt.base.module.aop;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.DataBaseWhiteListExctption;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.manager.IOperationLogManager;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;
import com.xs.jt.base.module.manager.ISecurityLogManager;

@Aspect
@Component
@Order(1)
public class DataBasePowerAopAction {
	@Autowired
	private IBaseParamsManager baseParamsManager;
	
	private static Logger logger = LoggerFactory.getLogger(DataBasePowerAopAction.class);

	@Autowired
	private HttpSession session;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private IOperationLogManager operationLogManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;
	
	@Autowired
	private HttpServletRequest request;

	@Pointcut("execution(* com.xs.jt..*.controller.*.*(..))")
	private void controllerAspect() {
	}

	/**
	 * 方法开始执行
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
	}

	/**
	 * 方法结束执行
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) {
	}

	/**
	 * 方法结束执行后的操作
	 */
	@AfterReturning("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
	}


	/**
	 * 方法执行
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		List<BaseParams> bps = baseParamsManager.getBaseParamsByType("jcsjkqx");
		if(!CollectionUtils.isEmpty(bps)) {
			BaseParams bp = bps.get(0);
			//开启 开关
			if("Y".equals(bp.getParamValue())) {
				List<String> ips = getServerIp();
				String insql ="";
				for(String ip:ips) {
					insql = "".equals(insql)?"?":(insql+",?");
				}
				//entityManager.clear();
				Query query = entityManager.createNativeQuery("select count(1) from master.dbo.WhiteList where ip in ("+insql+")");
				for(int i =0;i<ips.size();i++) {
					query.setParameter(i+1, ips.get(i));
				}
				Integer o = Integer.parseInt(query.getSingleResult().toString());
				if(o == 0) {
					saveLog();
					logger.info("服务器IP地址不在数据库白名单中，不能访问数据库");
					session.invalidate();
					throw new DataBaseWhiteListExctption("服务器IP地址不在数据库白名单中，拒绝访问！");
				}
				
				
			}
		}
		
		Object object = pjp.proceed();
		return object;
	}
	
	private void saveLog() {
		OperationLog log = new OperationLog();
		// 获取当前登陆用户信息
//		log.setOperationUser(yhm);
		log.setOperationCondition("");
		log.setModule("程序退出");
		log.setOperationType("程序退出");
		log.setIpAddr(Common.getIpAdrress(request));
		log.setContent("服务器IP地址不在数据库白名单中，不能访问数据库");
		log.setOperationResult(OperationLog.OPERATION_RESULT_ERROR);
		log.setFailMsg("服务器IP地址不在数据库白名单中");
		log.setStatus(1);
		log.setOperationDate(new Date());
		operationLogManager.saveOperationLog(log);
		
		SecurityAuditPolicySetting saps = this.securityAuditPolicySettingManager.getPolicyByCode(SecurityAuditPolicySetting.DATABASE_WHITELIST);
		SecurityLog securityLog = new SecurityLog();
		securityLog.setCreateUser(User.SYSTEM_USER);
		securityLog.setUpdateUser(User.SYSTEM_USER);
		securityLog.setClbm(SecurityAuditPolicySetting.DATABASE_WHITELIST);
		if(saps != null) {
			securityLog.setClbmmc(saps.getAqsjcllxmc());
		}
		securityLog.setIpAddr(Common.getIpAdrress(request));
		securityLog.setSignRed("Y");
		securityLog.setContent("服务器IP地址不在数据库白名单中！");
		securityLog.setResult("拒绝访问");
		securityLogManager.saveSecurityLog(securityLog);
		
	}
	
	/**
     * 获取服务器IP地址
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String>  getServerIp() throws Exception{
    	List<String> list = new ArrayList<String>();
    	String localip = null;// 本地IP，如果没有配置外网IP则返回它  
        String netip = null;// 外网IP  
        try {  
            Enumeration netInterfaces = NetworkInterface  
                    .getNetworkInterfaces();  
            InetAddress ip = null;  
//            boolean finded = false;// 是否找到外网IP  
            while (netInterfaces.hasMoreElements() ) {  //&& !finded
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();  
                Enumeration address = ni.getInetAddresses();  
                while (address.hasMoreElements()) {  
                    ip = (InetAddress) address.nextElement();  
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()  
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP  
                        netip = ip.getHostAddress();  
                        System.out.println("外网IP"+netip);
//                        finded = true;  
//                        break; 
                        list.add(netip);
                    } else if (ip.isSiteLocalAddress()  
                            && !ip.isLoopbackAddress()  
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP  
                        localip = ip.getHostAddress();  
                        System.out.println("内网IP"+localip);
                        list.add(localip);
                    }  
                }  
            }  
        } catch (SocketException e) {  
            logger.error("获取服务器IP失败！",e);
        }  
        return list; 
    }
}


