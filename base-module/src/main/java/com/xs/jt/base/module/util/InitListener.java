package com.xs.jt.base.module.util;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xs.jt.base.module.common.DSACoder;
import com.xs.jt.base.module.common.InitServerCommonPowerUtil;
import com.xs.jt.base.module.common.InitServerCommonUtil;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.PowerPoint;
import com.xs.jt.base.module.manager.IBaseParamsManager;



/**
 * Application Lifecycle Listener implementation class InitListener
 * 
 */
@WebListener
public class InitListener implements ServletContextListener {

	protected static Log log = LogFactory.getLog(InitListener.class);

	private WebApplicationContext wac;

	private ServletContext servletContext;
	
	
	/**
	 * Default constructor.
	 */
	public InitListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {

		try {
			System.out.println("*************************");
			servletContext = contextEvent.getServletContext();
			wac = WebApplicationContextUtils.getWebApplicationContext(contextEvent.getServletContext());
			InitServerCommonPowerUtil initServerCommonPowerUtil = (InitServerCommonPowerUtil) wac.getBean("initServerCommonPowerUtil");
			List<PowerPoint> powerPoints = initServerCommonPowerUtil.initPower(new String[] {"com.xs.jt.base.module.controller"});
			servletContext.setAttribute("powerPoints", powerPoints);

			// 加载参数表
			IBaseParamsManager baseParamsManager = (IBaseParamsManager) wac.getBean("baseParamsManager");
			List<BaseParams> bps = baseParamsManager.getBaseParams();
			servletContext.setAttribute("bps", bps);
			
			InitServerCommonUtil initServerCommonUtil= (InitServerCommonUtil) wac.getBean("initServerCommonUtil");
			System.out.println("*********************************************");
			//初始化部门
			initServerCommonUtil.initRootDepartment();
			
			initServerCommonUtil.initAdminRole();
			
			initServerCommonUtil.initAdmin();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		
	}

}
