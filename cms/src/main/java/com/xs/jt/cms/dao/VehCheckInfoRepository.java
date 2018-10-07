package com.xs.jt.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehCheckInfo;
@Repository
public interface VehCheckInfoRepository extends JpaRepository<VehCheckInfo, Integer>,JpaSpecificationExecutor<VehCheckInfo>{
	
	
	@Query(value = "from VehCheckInfo where lsh = :lsh")
	List<VehCheckInfo> findVehCheckInfoByLsh(@Param("lsh")String lsh);
	
	@Query(value = "select max(cycs) from VehCheckInfo where lsh = :lsh")
	Integer findMaxCsByLsh(@Param("lsh")String lsh);
	
	@Query(value = "select TOP 1 v.* from TM_VHE_CHECKINFO v where lsh = :lsh  order by cycs desc",nativeQuery = true)
	VehCheckInfo findBhgVehCheckInfoByLsh(@Param("lsh")String lsh);
	
	@Query(value = "select TOP 1 v.* from TM_VHE_CHECKINFO v where hphm = :hphm and hpzl=:hpzl  order by cycs desc",nativeQuery = true)
	VehCheckInfo findBhgVehCheckInfoByHphmHpzl(@Param("hphm")String hphm,@Param("hpzl")String hpzl);

}
