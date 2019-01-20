package com.xs.jt.veh.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.veh.entity.VehCheckLogin;

public interface IDeviceCheckJudegManager {

	/**
	 * 生成报告单
	 */
	public void createDeviceCheckJudeg(VehCheckLogin vehCheckLogin);

	public Integer createRoadCheckJudeg(VehCheckLogin vehCheckLogin, Integer xh);
	
	public List<Map<String,Object>> getDeviceCheckJudeg(String jylsh);

}
