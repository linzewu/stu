package com.xs.jt.cms.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.xs.jt.cms.entity.VehCheckInfo;

public interface IVehCheckInfoManager {
	
	public VehCheckInfo save(VehCheckInfo vehCheckInfo);
	
	public List<VehCheckInfo> findPoliceCheckInfoByLsh(String lsh);
	
	public Map<String,Object> getVehCheckInfoList(Integer page, Integer rows,VehCheckInfo vehCheckInfo);

	public String getVehCheckReport(Integer id) throws Exception;
	
	public VehCheckInfo findBhgVehCheckInfoByLsh(String lsh);
	
	public VehCheckInfo findBhgVehCheckInfoByHphmHpzl(String hphm,String hpzl);
	
	public Integer findMaxCsByLsh(String lsh);
	
	public VehCheckInfo findVehCheckInfoByLshAndCycs(String lsh,int cycs);
	
	
	
}
