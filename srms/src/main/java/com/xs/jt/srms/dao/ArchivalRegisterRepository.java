package com.xs.jt.srms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.ArchivalRegister;
@Repository
public interface ArchivalRegisterRepository extends JpaRepository<ArchivalRegister, Integer>,JpaSpecificationExecutor<ArchivalRegister>{
	
	@Query(value = "select count(id) from TM_ARCHIVAL_REGISTER t where t.bar_code = :barCode and (sysdate - to_date(t.create_time,'yyyy-mm-dd hh24-mi-ss')) <=:days  ",nativeQuery=true)
	public Integer findMultiArchivalRegister(@Param("barCode")String barCode,@Param("days")int days);

}
