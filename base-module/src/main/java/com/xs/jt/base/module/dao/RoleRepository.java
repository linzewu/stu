package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role>{
	
	@Query(value = "From Role where jsmc=:jsmc")
	Role findByJsmc(@Param("jsmc")String jsmc);

}
