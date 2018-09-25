package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.base.module.entity.Role;


public interface IRoleManager {
	
//	public List<Role> getRoleList(Role role,Integer page,Integer rows);
//	
//	public Integer getRoleCount(Role role);
	
	public Role getRole(String roleName);
	
	public Role add(Role role);
	
	public Role save(Role role) throws Exception;
	
	public void delete(Integer id) throws Exception;
	
	public Role getSystemRole();
	public Role getRoleById(Integer id);
	//public void addMenuToMap(Map<String, String> menus, String ids);
	//public void addMenuToMap(Map<String, String> menus, Object[] ms);
	//public List<Power> transformationPowers(Map<String, String> menus, List<Power> powers);
	//public  Map<String,ModulePower> transformationModules(Map<String, String> menus, List<ModulePower> modules);
	//public void addMenuToPower(Map<String, String> menus, List<Power> power);
	public List<Role> getAllRoles();
	
	public Map<String,Object> getRoles(Integer page, Integer rows,Role role);
	
	public int getRoleCount();
}
