package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehCheckInfo;
@Repository
public interface VehCheckInfoRepository extends JpaRepository<VehCheckInfo, Integer>,JpaSpecificationExecutor<VehCheckInfo>{
	
	
	@Query(value = "from VehCheckInfo where lsh = :lsh")
	VehCheckInfo findVehCheckInfoByLsh(@Param("lsh")String lsh);

}
