package com.xs.jt.cmsvideo.manager;

import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.xs.jt.cmsvideo.entity.PdaInfo;

public interface IPdaInfoManager {
	
	public Map<String,Object> getPdaInfoList(Integer page, Integer rows,PdaInfo pdaInfo);
	
	public void savePdaInfo(PdaInfo pdaInfo);
	
	public void deletePdaInfo(PdaInfo pdaInfo);
	
	public PdaInfo getPdaInfoBySerialCode(String serialCode);

}
