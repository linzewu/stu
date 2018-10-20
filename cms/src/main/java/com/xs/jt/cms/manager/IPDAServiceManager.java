package com.xs.jt.cms.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

public interface IPDAServiceManager {
	
	List<Map<String,Object>> findPcbStVehicle(@Param("bh")String bh);
	
	List<Map<String,Object>> findGongGaoListbyCLXH(String clxh);
	
	List<Map<String,Object>> getListOfDPGG(String dpid);
	
	List<Map<String,Object>> getDPGG(String bh);
	
	List<Map<String,Object>> getImplCarParam(String clxh);
	
	Map<String,Object> getGongGaoInfo(String clxh);

}
