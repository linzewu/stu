package com.xs.jt.base.module.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.BlackList;
@Repository
public interface BlackListRepository extends JpaRepository<BlackList,String>,JpaSpecificationExecutor<BlackList>{
	
	@Query(value = "from BlackList  where ip = :ip")
	BlackList findBlackListByIp(@Param("ip")String ip);
	
	@Query(value = "from BlackList where enableFlag = :enableFlag")
	List<BlackList> findBlackListByEnableFlag(@Param("enableFlag")String enableFlag);

}
