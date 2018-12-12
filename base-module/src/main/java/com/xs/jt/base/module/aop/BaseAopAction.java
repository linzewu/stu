package com.xs.jt.base.module.aop;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xs.jt.base.module.entity.BaseEntity;
import com.xs.jt.base.module.entity.User;

@Aspect
@Component
public class BaseAopAction {

	private static Logger logger = LoggerFactory.getLogger(BaseAopAction.class);

	@Autowired
	private HttpSession session;

	@Pointcut("execution(* com.xs.jt..*.dao.*.save*(..))")
	private void daoAspect() {
	}

	/**
	 * 方法开始执行
	 */
	@Before("daoAspect()")
	public void doBefore(JoinPoint joinPoint) {
	}

	/**
	 * 方法结束执行
	 */
	@After("daoAspect()")
	public void after(JoinPoint joinPoint) {
	}

	/**
	 * 方法结束执行后的操作
	 */
	@AfterReturning("daoAspect()")
	public void doAfter(JoinPoint joinPoint) {
	}

	/**
	 * 方法有异常时的操作
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@AfterThrowing(pointcut = "daoAspect()", throwing = "e")
	public void doAfterThrow(JoinPoint joinPoint, Throwable e) throws NoSuchMethodException, SecurityException {
		logger.error("设置创建人修改人信息异常", e);
	}

	/**
	 * 方法执行
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	@Around("daoAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String yhm = "—— ——";
		if(RequestContextHolder.getRequestAttributes() != null) {
			// 获取当前登陆用户信息
			User loginUser = (User) session.getAttribute("user");
			
			if (loginUser != null) {
				yhm = loginUser.getYhm();
			}
		}
		
		// 拦截的方法参数
		Object[] args = pjp.getArgs();
		if (args != null && args.length > 0) {
			for (int c = 0; c < args.length; c++) {
				if (args[c].getClass().getSuperclass() == BaseEntity.class) {
					// 获取obj类的字节文件对象
					Class cls = args[c].getClass().getSuperclass();
					// 获取该类的成员变量
					Field createTime = cls.getDeclaredField("createTime");

					// 取消语言访问检查
					createTime.setAccessible(true);

					// 获取该类的成员变量
					Field createUser = cls.getDeclaredField("createUser");

					// 取消语言访问检查
					createUser.setAccessible(true);
					
					// 获取该类的成员变量
					Field updateUser = cls.getDeclaredField("updateUser");

					// 取消语言访问检查
					updateUser.setAccessible(true);
					updateUser.set(args[c], yhm);
					
					// 获取该类的成员变量
					Field updateTime = cls.getDeclaredField("updateTime");

					// 取消语言访问检查
					updateTime.setAccessible(true);
					updateTime.set(args[c], sdf.format(new Date()));
					
					if (createUser.get(args[c]) == null) {
						// 给变量赋值
						createUser.set(args[c], yhm);
					}
					if (createTime.get(args[c]) == null) {
						// 给变量赋值
						createTime.set(args[c], sdf.format(new Date()));
					}
					
				}
			}
		}
		Object object = pjp.proceed();
		return object;
	}

}
