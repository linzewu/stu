package com.xs.jt.veh.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.veh.entity.VehCheckLogin;

public interface IExternalCheckJudgeManager {

	public void createExternalCheckJudge(VehCheckLogin vehCheckLogin);
	
	public List<Map<String,Object>> getExternalCheckJudge(String jylsh);

}
