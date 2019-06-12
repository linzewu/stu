package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.xs.jt.srms.entity.ArchivalCase;

public interface IArchivalCaseManager {
	
	public Map<String, Object> getArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase);
	
	public Map<String, Object> getUsedArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase);

	public void saveArchivalCase(ArchivalCase archivalCase);

	public void deleteArchivalCase(ArchivalCase archivalCase);
	
	public ArchivalCase newCarArchivalCheckIn(ArchivalCase archivalCase);
	
	public ArchivalCase UsedCarArchivalCheckIn(ArchivalCase archivalCase);
	
	public List<ArchivalCase> findUseArchivalCase(String zt,String rackNo);
	
	public List<Map<String,Object>> findCheckOutLong();
	
	public boolean archivalCaseAdjust(ArchivalCase archivalCase);
	
	public List<Map<String,Object>> getCarInfoByBarcode(String barcode);
	
	public List<ArchivalCase> getArchivalCaseByClsbdh(String clsbdh);
	
	public List<Map> getNoUsedByArchivesNoAndRackNo(String archivesNo,String rackNo);
	

}
