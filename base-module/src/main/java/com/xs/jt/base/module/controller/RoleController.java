package com.xs.jt.base.module.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.PowerPoint;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.base.module.manager.IRoleManager;


@Controller
@RequestMapping(value = "/role")
//@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_ROLE,href="/dzyxh/page/system/RoleManager.html",icoUrl="/dzyxh/images/group_48.png",modeIndex=4,appIndex=3)
@Modular(modelCode="role",modelName="角色权限管理",isEmpowered=false)
public class RoleController {
	
	protected static Log log = LogFactory.getLog(RoleController.class);

	@Resource(name = "roleManager")
	private IRoleManager roleManager;
	
	@Autowired
	private ServletContext servletContext;

	//@FunctionAnnotation(name = "角色查询")
	@UserOperation(code="getRoles",name="查询角色")
	@RequestMapping(value = "getRoles", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRoles(Role role, Integer page, Integer rows) {
//		List<Role> roles = roleManager.getRoleList(role, page, rows);
//		Integer count = roleManager.getRoleCount(role);
//		return ResultHandler.toMyJSON(roles, count);
		return roleManager.getRoles(page-1, rows, role);
	}

	//@FunctionAnnotation(name = "角色查询")
	@RequestMapping(value = "getRole", method = RequestMethod.POST)
	public @ResponseBody Role getRole(String jsmc) {
		Role role = roleManager.getRole(jsmc);
		return role;
	}
	//@FunctionAnnotation(name = "权限菜单")
	/**@RequestMapping(value = "menu.json", method = RequestMethod.POST)
	public @ResponseBody String getRoleMenu(HttpServletRequest request) throws JsonProcessingException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
		ServletContext sc = request.getSession().getServletContext();
		List<ModulePower> modules = (List<ModulePower>) sc.getAttribute("modules");
		String js=user.getJs();
		String roleqx=null;
		if(js!=null){
			Role role=roleManager.getRoleById(Integer.valueOf(js));
			if(role!=null){
				roleqx=role.getJsqx();
			}
		}
		Map<String,String> menus=new HashMap<String,String>();
		if(user.getYhm().equals(Constant.admin)){
			List<Power> powers =(List<Power>)sc.getAttribute("powers");
			roleManager.addMenuToPower(menus, powers);
		}else{
			roleManager.addMenuToMap(menus, roleqx);
		}
		if(user.getQx()!=null){
			JSONArray jss=JSONArray.fromObject(user.getQx());
			roleManager.addMenuToMap(menus, jss.toArray());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		log.info(objectMapper.writeValueAsString(roleManager.transformationModules(menus, modules)));
		return  objectMapper.writeValueAsString(roleManager.transformationModules(menus, modules));
	}**/
	
	//@FunctionAnnotation(name = "权限列表查询")
	/**@RequestMapping(value = "getPowers", method = RequestMethod.POST)
	public @ResponseBody List<Power> getPowers(HttpServletRequest request) {

		ServletContext sc = request.getSession().getServletContext();
		List<Power> powers = (List<Power>) sc.getAttribute("powers");
		return powers;
	}**/

	//@FunctionAnnotation(name = "角色新增")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(Role role, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			if(role.getJslx()==0){
				throw new Exception("无法新增系统超级管理员");
			}
			role = roleManager.add(role);
			return ResultHandler.resultHandle(result, role, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	//@FunctionAnnotation(name = "角色编辑")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(Role role, BindingResult result) throws Exception {
		if (!result.hasErrors()) {
			role = roleManager.save(role);
			return ResultHandler.resultHandle(result, role, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	//@FunctionAnnotation(name = "角色删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@RequestParam Integer id) throws Exception {
		roleManager.delete(id);
		return ResultHandler.toSuccessJSON("角色删除成功");
	}
	
	@RequestMapping(value = "getPowerPoints", method = RequestMethod.POST)
	public @ResponseBody List<PowerPoint> getPowerPoints() {
		List<PowerPoint> powerPoints = (List<PowerPoint>) servletContext.getAttribute("powerPoints");
		return powerPoints;
	}
	
	@RequestMapping(value = "getRolePower", method = RequestMethod.POST)
	public @ResponseBody String getRolePower(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
		String js=user.getJs();
		StringBuffer roleqx=new StringBuffer("");
		if (js != null) {
			Role role = roleManager.getRoleById(Integer.valueOf(js));
			if (role != null) {
				if (StringUtils.isNotEmpty(role.getJsqx())) {
					roleqx.append(role.getJsqx());
				}
			}
			System.out.println(role.getJsmc());
		}
		System.out.println(roleqx.toString());
		return roleqx.toString();
	}
	
	@UserOperation(code="getAllRole",name="获取所有角色",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getAllRole", method = RequestMethod.POST)
	public @ResponseBody List<Role> getAllRole() {
		return roleManager.getAllRoles();
	}

}
