package com.xs.jt.base.module.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.entity.BaseEntity;
import com.xs.jt.base.module.entity.CoreFunction;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.ICoreFunctionManager;
import com.xs.jt.base.module.manager.IOperationLogManager;

import net.sf.json.JSONObject;



@Aspect
@Component
public class LogAopAction {



	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IOperationLogManager operationLogManager;
	@Autowired
	private ICoreFunctionManager coreFunctionManager;
	
	@Autowired
	private ServletContext servletContext;

	@Pointcut("execution(* com.xs.jt.base.module.controller.*.*(..))")
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
	 * 方法有异常时的操作
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@AfterThrowing(pointcut="controllerAspect()",throwing="e")
	public void doAfterThrow(JoinPoint joinPoint,Throwable e) throws NoSuchMethodException, SecurityException {
		OperationLog log = getLog(joinPoint);
		log.setOperationResult(OperationLog.OPERATION_RESULT_ERROR);
		log.setStatus(1);
		log.setFailMsg(e.getMessage());
		operationLogManager.saveOperationLog(log);
	}

	/**
	 * 方法执行
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		Date beginDate=new Date();
        OperationLog log =getLog(pjp);
        Object object = pjp.proceed();
        if(log!=null) {
			log.setOperationResult(OperationLog.OPERATION_RESULT_SUCCESS);
			log.setStatus(1);
			Date endtime = new Date();
			log.setActionTime(endtime.getTime()-beginDate.getTime());
	        log.setOperationDate(beginDate);
	        operationLogManager.saveOperationLog(log);
        }
		return object;
	}
	
	
	private OperationLog getLog(JoinPoint pjp) throws NoSuchMethodException, SecurityException {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		List<CoreFunction> coreList = (List<CoreFunction>)servletContext.getAttribute("coreFunctionList");
		if(coreList == null) {
			//获取核心功能
			coreList = this.coreFunctionManager.getAllCoreFunction(0);
			if(coreList == null || coreList.size() == 0) {
				coreList = new ArrayList<CoreFunction>();
			}
			servletContext.setAttribute("coreFunctionList", coreList);
		}
		
		OperationLog log =new OperationLog();
		// 日志实体对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 获取当前登陆用户信息
		User loginUser = (User) session.getAttribute("user");
		if (loginUser == null) {
			log.setOperationUser("—— ——");
		} else {
			log.setOperationUser(loginUser.getYhm());
		}
		// 拦截的实体类，就是当前正在执行的controller
		Object target = pjp.getTarget();
		// 拦截的方法名称。当前正在执行的方法
		String methodName = pjp.getSignature().getName();
		// 拦截的方法参数
		Object[] args = pjp.getArgs();
		StringBuffer sbStr = new StringBuffer("");
		if (args != null && args.length > 0) {
			for(int c=0;c<args.length;c++) {
				if(args[c].getClass().getSuperclass() == BaseEntity.class || args[c].getClass() == LinkedHashMap.class) {
					sbStr.append("参数"+(c+1)+"="+JSONObject.fromObject(args[c]).toString()+",");
				}else if(args[c].getClass() == String.class || args[c].getClass() == Integer.class) {
					sbStr.append("参数"+(c+1)+"="+args[c]+",");
				}
            //args[0] = "改变后的参数1";
			}
        }
		log.setOperationCondition(sbStr.toString());
		
		// 拦截的放参数类型

		MethodSignature msig = (MethodSignature) pjp.getSignature();

		Class[] parameterTypes = msig.getMethod().getParameterTypes();
		Object object = null;
		Class targetClass =target.getClass();
		Method method = targetClass.getMethod(methodName, parameterTypes);
		String functionP = "";
		if(targetClass.isAnnotationPresent(Modular.class)) {
			Modular modular=(Modular) targetClass.getAnnotation(Modular.class);
			log.setModule(modular.modelName());
			functionP = modular.modelCode();
		}

		if (method.isAnnotationPresent(UserOperation.class)) {
			UserOperation userOperation = method.getAnnotation(UserOperation.class);
			log.setOperationType(userOperation.name());
			log.setIpAddr(getIpAdrress());
			log.setActionUrl(request.getRequestURI());
			log.setContent("用户"+log.getOperationUser()+"在"+sdf.format(new Date())+"时间,IP为"+log.getIpAddr()+"操作了"+userOperation.name());
			functionP = functionP+"."+userOperation.code();
			for(CoreFunction cf:coreList) {
				if(functionP.equals(cf.getFunctionPoint())) {
					log.setCoreFunction("Y");
					break;
				}
			}
			return log;
		}
		return null;
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
