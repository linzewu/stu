package com.xs.jt.base.module.manager;

import java.util.List;

import com.xs.jt.base.module.entity.CoreFunction;



public interface ICoreFunctionManager {
	
	public List<CoreFunction> getAllCoreFunction(int status);
	
	public void deleteAllCoreFunction(int status) ;
	
	public void save(List <CoreFunction> funs);

}
