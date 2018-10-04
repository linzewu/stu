package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.util.PageInfo;


public interface IBaseParamsManager {
	
	public List<BaseParams> getBaseParams();
	
	public BaseParams save(BaseParams baseParams);
	
	public void delete(Integer id);
	
	public Map<String,Object> getBaseParams(Integer page, Integer rows,BaseParams param);
	
	public List<BaseParams> getBaseParamsByType(String type);
	

}
