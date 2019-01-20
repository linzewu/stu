package com.xs.jt.veh.manager;

import org.dom4j.DocumentException;

import com.xs.jt.veh.entity.CheckLog;

public interface ICheckLogManager {

	public String getCheckItem(String jylsh, String type) throws DocumentException;

	public CheckLog getLoginCheckLog(String jylsh);
	
	public void saveCheckLog(CheckLog checkLog);

}
