package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.base.module.entity.BlackList;


public interface IBlackListManager {
	
	public void saveBlackList(BlackList blackList);
	
	public void deleteBlackList(BlackList blackList);
	
	public void deleteSystemBlackList();
	
	public BlackList getBlackListByIp(String ip);
	
	public boolean checkIpIsBan(String ip);
	
//	public List<BlackList> getList(Integer page, Integer rows, BlackList blackList);
//	
//	public Integer getListCount(Integer page, Integer rows, BlackList blackList);
	
	public List<BlackList> getEnableList();
	
	public Map<String,Object> getBlackLists(Integer page, Integer rows,BlackList blackList);

}
