package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.srms.entity.ArchivalCase;

public interface IArchivalCaseManager {
	
	public Map<String, Object> getArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase);
	
	public Map<String, Object> getUsedArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase);

	public void saveArchivalCase(ArchivalCase archivalCase);

	public void deleteArchivalCase(ArchivalCase archivalCase);
	
	public boolean newCarArchivalCheckIn(ArchivalCase archivalCase);
	
	public boolean UsedCarArchivalCheckIn(ArchivalCase archivalCase);
	
	public List<ArchivalCase> findUseArchivalCase(String zt,String rackNo);
	
	public List<Map<String,Object>> findCheckOutLong();
	

}
