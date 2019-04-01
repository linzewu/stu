package com.xs.jt.cmsvideo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.VideoWarn;
@Repository
public interface VideoWarnRepository extends JpaRepository<VideoWarn, Integer>,JpaSpecificationExecutor<VideoWarn>{

}
