package com.xs.jt.base.module.manager.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.CoreFunctionRepository;
import com.xs.jt.base.module.entity.CoreFunction;
import com.xs.jt.base.module.manager.ICoreFunctionManager;

@Service("coreFunctionManager")
public class CoreFunctionManagerImpl implements ICoreFunctionManager {

	//@Resource(name = "sysHibernateTemplate")
	//private HibernateTemplate hibernateTemplate;
	@Autowired
	private CoreFunctionRepository coreFunctionRepository;
	
	public List<CoreFunction> getAllCoreFunction(int status) {
		//return (List<CoreFunction>)this.hibernateTemplate.find(" from CoreFunction where status = ?", status);
		return this.coreFunctionRepository.findCoreFunctionByStatus(status);
	}

	
	public void deleteAllCoreFunction(int status) {
		this.coreFunctionRepository.deleteCoreFunction(status);

	}
	
	public void save(List<CoreFunction> funs) {
		for(CoreFunction fun:funs) {
			this.coreFunctionRepository.save(fun);
		}
		
	}
	
	

}
