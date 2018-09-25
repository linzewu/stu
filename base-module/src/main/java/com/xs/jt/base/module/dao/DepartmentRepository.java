package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer>{
	
	@Query(value="from Department where bmdm=:bmdm")
	Department findByBmdm(@Param("bmdm")String bmdm);

}
