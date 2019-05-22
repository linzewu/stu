package com.xs.jt.hwvideo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.hwvideo.entity.VehInfo;
@Repository
public interface VehInfoRepository extends JpaRepository<VehInfo, Integer>,JpaSpecificationExecutor<VehInfo>{

	@Query(value=" from VehInfo where hphm=:hphm and status!=3 ")
	public VehInfo getVehInfoOfcc(@Param("hphm")String hphm);
	
	
	@Query(value=" from VehInfo where status=1 ")
	public List<VehInfo> getOutVehInfos();
	
	
	
	
	
}
