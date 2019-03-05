package com.xs.jt.base.module.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.BaseParams;
@Repository
public interface BaseParamsRepository extends JpaRepository<BaseParams,Integer>,JpaSpecificationExecutor<BaseParams>{
	
	@Query(value = "from BaseParams where type = :type")
	List<BaseParams> findBaseParamsByType(@Param("type")String type);
}
