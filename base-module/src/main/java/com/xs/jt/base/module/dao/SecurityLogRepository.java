package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.SecurityLog;
@Repository
public interface SecurityLogRepository extends JpaRepository<SecurityLog,Integer>,JpaSpecificationExecutor<SecurityLog>{

}
