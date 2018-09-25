package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.OperationLog;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog,Integer>,JpaSpecificationExecutor<OperationLog>{

}
