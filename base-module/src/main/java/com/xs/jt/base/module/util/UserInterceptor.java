package com.xs.jt.base.module.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.entity.User;

@Component
public class UserInterceptor implements HandlerInterceptor {

	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}

	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User user =(User)request.getSession().getAttribute("user");
		if(user==null){
			Map map =new HashMap();
			map.put("state", 600);
			map.put("message", "会话失效:");
			map.put("session", request.getSession().getId());
			 ObjectMapper mapper = new ObjectMapper();
			 String message = mapper.writeValueAsString(map);
			 response.setCharacterEncoding("UTF-8");			 
			 response.setContentType("application/json;charset=utf-8");
			 response.getWriter().write(message);
			return false;
		}else{
			return true;
		}
	}

}
