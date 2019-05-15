package com.xs.jt.base.module.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.SecurityLog;
@Repository
public interface SecurityLogRepository extends JpaRepository<SecurityLog,Integer>,JpaSpecificationExecutor<SecurityLog>{
	@Query(value = "from SecurityLog where createTime between :operationDateBegin and :operationDateEnd")
	public List<SecurityLog> getSecurityLogList(@Param("operationDateBegin")String operationDateBegin,@Param("operationDateEnd")String operationDateEnd);

}
