package com.xs.jt.base.module.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IOperationLogManager;


/**
 * Application Lifecycle Listener implementation class UserSeesionListener
 *
 */

@WebListener
public class UserSeesionListener implements HttpSessionListener, HttpSessionAttributeListener{

	@Autowired
	private IOperationLogManager operationLogManager;
	@Autowired
	private HttpServletRequest request;
    /**
     * Default constructor. 
     */
    public UserSeesionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    @SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent se)  {
    	HttpSession currentSeesion  =se.getSession();
    	User user = (User) currentSeesion.getAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
    	if(user!=null) {
    		currentSeesion.removeAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
    		Map<String,HttpSession> userSessionList = (Map<String, HttpSession>) currentSeesion.getServletContext().getAttribute("userSessionList");
    		if(userSessionList!=null) {
        		userSessionList.remove(user.getYhm());
        	}
    	}
    }

	@SuppressWarnings("unchecked")
	public void attributeAdded(HttpSessionBindingEvent event) {
		if(event.getName().equals(Constant.ConstantKey.USER_SESSIO_NKEY)) {
			
			HttpSession currentSession =event.getSession();
			ServletContext servletContext =currentSession.getServletContext();
			
			User user = (User) currentSession.getAttribute(event.getName());
			String userName = user.getYhm();
			//同一用户不能在多个IP登陆
			Map<String,HttpSession> map = (Map<String,HttpSession>)servletContext.getAttribute("userSessionList");
			if(map == null) {
				map = new HashMap<String,HttpSession>();
				servletContext.setAttribute("userSessionList", map);
			}
			if(map.containsKey(userName)) {
				HttpSession sess = map.get(userName);
				if(!sess.getId().equals(currentSession.getId())) {
					//会话销毁
					sess.invalidate();
					map.put(userName, currentSession);
					saveLog(userName);
				}
			}else {
				map.put(userName, currentSession);
			}
		}
		
	}

	
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		
	}
	
	private void saveLog(String yhm) {
		OperationLog log = new OperationLog();
		// 获取当前登陆用户信息
		log.setOperationUser(yhm);
		log.setOperationCondition("");
		log.setModule("注销");
		log.setOperationType("异地登陆");
		log.setIpAddr(Common.getIpAdrress(request));
		log.setContent("用户"+yhm+"异地登陆，下线");
		log.setOperationResult(OperationLog.OPERATION_RESULT_SUCCESS);
		log.setStatus(1);
		log.setOperationDate(new Date());
		operationLogManager.saveOperationLog(log);
	}

	
	
    
    
    
	
}
