package com.xs.jt.base.module.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.base.module.manager.IRoleManager;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private IRoleManager roleManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 验证权限
		if (this.hasPermission(handler, request)) {
			return true;
		}
		// null == request.getHeader("x-requested-with") TODO 暂时用这个来判断是否为ajax请求
		// 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
		// response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
		Map map = new HashMap();
		map.put("state", 403);
		map.put("message", "无权限");
		map.put("session", request.getSession().getId());
		ObjectMapper mapper = new ObjectMapper();
		String message = mapper.writeValueAsString(map);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(message);
		return false;

	}

	private boolean hasPermission(Object handler, HttpServletRequest request) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 获取方法上的注解
			UserOperation userOperation = handlerMethod.getMethod().getAnnotation(UserOperation.class);
			// 如果标记了注解，则判断权限
			if (userOperation != null) {
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
				if (CommonUserOperationEnum.Default == userOperation.userOperationEnum() && userOperation.isMain()) {
					Modular modular = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Modular.class);
					String power = modular.modelCode() + "." + userOperation.code();
					// redis或数据库 中获取该用户的权限信息 并判断是否有权限

					String js = user.getJs();
					// 权限
					StringBuffer roleqx = new StringBuffer("");
					if (js != null) {
						Role role = roleManager.getRoleById(Integer.valueOf(js));
						if (role != null) {
							if (StringUtils.isNotEmpty(role.getJsqx())) {
								roleqx.append(role.getJsqx());
							}
						}
					}
					System.out.println("hanlar:" + roleqx.toString());
					return roleqx.toString().indexOf(power) > -1;
				} else if (CommonUserOperationEnum.AllLoginUser == userOperation.userOperationEnum()) {
					if (user == null) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
