package com.xs.jt.cms.manager;

import java.util.Map;

import com.xs.jt.cms.entity.VehCheckInfo;

public interface IVehCheckInfoManager {
	
	public VehCheckInfo save(VehCheckInfo vehCheckInfo);
	
	public VehCheckInfo findPoliceCheckInfoByLsh(String lsh);
	
	public Map<String,Object> getVehCheckInfoList(Integer page, Integer rows,VehCheckInfo vehCheckInfo);

}
