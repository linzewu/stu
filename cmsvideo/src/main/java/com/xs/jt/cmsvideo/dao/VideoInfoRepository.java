package com.xs.jt.cmsvideo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.VideoInfo;
@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, Integer>, JpaSpecificationExecutor<VideoInfo> {

	@Query(value=" from VideoInfo where zt=:zt and (taskCount < :taskCount or taskCount is null)")
	public List<VideoInfo> getVideoInfoByZt(@Param("zt")Integer zt,@Param("taskCount")Integer taskCount); 
	
	@Query(value=" from VideoInfo where lsh=:lsh and jycs=:jycs")
	public List<VideoInfo> getVideoInfoByLshAndJycs(@Param("lsh")String lsh, @Param("jycs")Integer jycs);
	
	@Query(value=" from VideoInfo where zt=:zt and videoEnd is not null and (taskCount < :taskCount or taskCount is null)")
	public List<VideoInfo> getVideoInfosNoDownLoad(@Param("zt")Integer zt,@Param("taskCount")Integer taskCount);
	
}
