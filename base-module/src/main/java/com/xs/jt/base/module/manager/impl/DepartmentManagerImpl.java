package com.xs.jt.base.module.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.DepartmentRepository;
import com.xs.jt.base.module.entity.Department;
import com.xs.jt.base.module.manager.IDepartmentManager;
import com.xs.jt.base.module.util.SortTools;

@Service("departmentManager")
public class DepartmentManagerImpl implements IDepartmentManager {
	
	
	@Autowired
	private DepartmentRepository departmentRepository;

	
	public List<Department> getDepts() {
		return this.departmentRepository.findAll(SortTools.basicSort("asc", "seq"));//depts;
	}

	
	public Department saveDept(Department dept) {
		return this.departmentRepository.save(dept);
	}

	
	public void deleteDept(Department dept) {
		
		Department d = getdeptByCode(dept.getBmdm());
		if(d!=null&&d.getId()!=null){
			this.departmentRepository.delete(d);
		}
		
	}

	
	public Department getdeptByCode(String bmdm) {
		
//		List<Department> depts =(List<Department>) this.hibernateTemplate.find("from Department where bmdm=?", bmdm);
//		
//		if(depts!=null&&!depts.isEmpty()){
//			return depts.get(0);
//		}
//		return null;
		return this.departmentRepository.findByBmdm(bmdm);
	}
	
	
	public List<String> getSubCodes(String bmdm) {
		
		List<String> codes=new ArrayList<String>();
		codes.add(bmdm);
		List<Department> depts = departmentRepository.findAll();//(List<Department>) this.hibernateTemplate.find("from Department");
		
		List<String> subCode = subCodes(depts,findSubDepts(depts,bmdm));
		
		codes.addAll(subCode);
		
		return codes;
	}
	
	private List<Department> findSubDepts(List<Department> depts,String bmdm){
		
		List<Department> subDepts=new ArrayList<Department>();
		
		for(Department dept : depts){
			if(bmdm.equals(dept.getSjbmdm())){
				subDepts.add(dept);
			}
		}
		
		return subDepts;
	}
	
	private List<String> subCodes(List<Department> allDepts,List<Department> subDepts){
		
		List<String> codes=new ArrayList<String>();
		
		for(Department dept : subDepts){
			codes.add(dept.getBmdm());
			List<Department> ds = findSubDepts(allDepts,dept.getBmdm());
			List<String> subCodes = subCodes(allDepts,ds);
			if(subCodes!=null){
				codes.addAll(subCodes);
			}
		}
		return codes;
	}
	
	
	
	

}
