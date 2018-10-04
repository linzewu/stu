package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.PoliceCheckInfo;
@Repository
public interface PoliceCheckInfoRepository extends JpaRepository<PoliceCheckInfo, Integer>{
	
	
	@Query(value = "from PoliceCheckInfo where lsh = :lsh")
	PoliceCheckInfo findPoliceCheckInfoByLsh(@Param("lsh")String lsh);

}
