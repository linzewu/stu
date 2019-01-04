package com.xs.jt.base.module.manager;

import java.util.Map;

import org.dom4j.DocumentException;

import com.xs.jt.base.module.entity.OperationLog;


public interface IOperationLogManager {
	
//	public List<OperationLog> getOperationLog(Integer page, Integer rows, OperationLog operationLog);
//	
//	public Integer getOperationLogCount(Integer page, Integer rows, OperationLog operationLog);
	
	public void saveOperationLog(OperationLog operationLog);
	
//	public List<OperationLog> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog);
//	
//	public Integer getLoginOperationLogCount(Integer page, Integer rows, OperationLog operationLog) ;
	
	public Map<String,Object> getOperationLogs(Integer page, Integer rows,OperationLog operationLog);
	
	public Map<String,Object> getLoginOperationLogs(Integer page, Integer rows,OperationLog operationLog);

	public void addExceLog(String resultStr,String condition,String operationType,String actionUrl,String jkid,String module)throws DocumentException;
}
