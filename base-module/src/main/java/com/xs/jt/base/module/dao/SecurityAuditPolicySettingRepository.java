package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
@Repository
public interface SecurityAuditPolicySettingRepository extends JpaRepository<SecurityAuditPolicySetting,Integer>,JpaSpecificationExecutor<SecurityAuditPolicySetting>{
	@Query(value = "from SecurityAuditPolicySetting where aqsjclbm=:aqsjclbm AND sfkq = '0'")
	SecurityAuditPolicySetting findByAqsjclbm(@Param("aqsjclbm")String aqsjclbm);
}
