package com.xs.jt.veh.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.base.module.manager.IRoleManager;
import com.xs.jt.base.module.manager.IUserManager;
@RequestMapping(value = "/user")
@RestController
@Modular(modelCode="user",modelName="用户管理",isEmpowered=false)
public class UserExpandController{
	
	@Resource(name = "userManager")
	private IUserManager userManager;
	
	@Resource(name = "roleManager")
	private IRoleManager roleManager;
	
	@UserOperation(code="getAllRole",name="获取所有角色",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getAllRole", method = RequestMethod.POST)
	public @ResponseBody List<Role> getAllRole() {
		return roleManager.getAllRoles();
	}
	
	@UserOperation(code="getRolesByUser",name="获取当前用户角色",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getRolesByUser", method = RequestMethod.POST)
	public @ResponseBody Role getRolesByUser(HttpSession session) {
		User user = (User)session.getAttribute("user");
		return roleManager.getRoleById(Integer.parseInt(user.getJs()));
	}
	
	@UserOperation(code="getUsers",name="获取用户列表")
	@RequestMapping(value = "getAllUsers", method = RequestMethod.POST)
	public @ResponseBody List getUsers() {
		return userManager.getUsersExceptAdmin();
	}

}
