package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

public interface IVehFlowManager {
	
	public List<Map<String,Object>> getVehFlowByClsbdh(String clsbdh);
	
	public List<Map<String,Object>> getCarInfoByLsh(String lsh);

}
