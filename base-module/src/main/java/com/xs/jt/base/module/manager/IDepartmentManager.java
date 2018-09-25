package com.xs.jt.base.module.manager;

import java.util.List;

import com.xs.jt.base.module.entity.Department;


public interface IDepartmentManager {
	
	public List<Department> getDepts();
	
	public Department saveDept(Department dept);
	
	public void deleteDept(Department dept);
	
	public Department getdeptByCode(String bmdm);

	public List<String> getSubCodes(String bmdm);
	
}
