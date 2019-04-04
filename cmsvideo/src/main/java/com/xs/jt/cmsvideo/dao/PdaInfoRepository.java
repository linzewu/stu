package com.xs.jt.cmsvideo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.PdaInfo;

@Repository
public interface PdaInfoRepository extends JpaRepository<PdaInfo, Integer>,JpaSpecificationExecutor<PdaInfo>{
	
	@Query(value=" from PdaInfo where serialCode=:serialCode ")
	public PdaInfo getPdaInfoBySerialCode(@Param("serialCode")String serialCode);

}
