package com.xs.jt.cmsvideo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.VideoConfig;
@Repository
public interface VideoConfigRepository extends JpaRepository<VideoConfig, Integer>,JpaSpecificationExecutor<VideoConfig> {

	@Query(value=" from VideoConfig where jyjgbh=:jyjgbh and jcxdh=:jcxdh and jyxm like %:jyxm%")
	public List<VideoConfig> getVideoConfigByJyjgbhAndJcxdhAndJyxm(@Param("jyjgbh")String jyjgbh, @Param("jcxdh")String jcxdh, @Param("jyxm")String jyxm); 
}
