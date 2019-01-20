package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.Flow;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer> {

	@Query(value = "from Flow where jcxdh=:jcxdh and jclclx=:jclclx")
	public Flow getFlowByJcxdhAndJclclx(@Param("jcxdh") Integer jcxdh, @Param("jclclx") Integer jclclx);

	@Query(value = "from Flow where jcxdh=:jcxdh")
	public List<Flow> getFlowByJcxdh(@Param("jcxdh") Integer jcxdh);

}
