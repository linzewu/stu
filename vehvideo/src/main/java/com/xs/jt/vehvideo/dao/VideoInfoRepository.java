package com.xs.jt.vehvideo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.vehvideo.entity.VideoInfo;
@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, Integer>, JpaSpecificationExecutor<VideoInfo> {

	@Query(value=" from VideoInfo where zt=:zt and taskCount < :taskCount and ip is not null and port is not null and jssj<dateadd(minute,-10,GETDATE())",
			countQuery="select count(*) from VideoInfo where zt=:zt and taskCount < :taskCount and ip is not null and port is not null and jssj<dateadd(minute,-10,GETDATE())")
	public Page<VideoInfo> getVideoInfoByZt(@Param("zt")Integer zt,@Param("taskCount")Integer taskCount,Pageable pageable); 
	
	
	
}
