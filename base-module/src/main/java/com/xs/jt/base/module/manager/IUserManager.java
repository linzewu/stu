package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;


public interface IUserManager {
	
//	public List<User> getUsers(User user,Integer page,Integer rows);
//	
//	public Integer getUserCount(User user);
	
	public User getUser(String userName);
	
	public User getUser(Integer id);
	
	public User saveUser(User user);
	
	public void deleteUser(User user);
	
	public boolean isExistUserName(User user);
	
//	public void initAdmin();
	
	public User login(String userName);
	
	public User queryUser(User user);
	
	public Map<String,Object> getUsers(Integer page, Integer rows,User user);
	
	public int getUserCount();
	
	

}
