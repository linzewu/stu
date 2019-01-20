package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.CheckQueue;

@Repository
public interface CheckQueueRepository extends JpaRepository<CheckQueue, Integer> {

	@Query(value = "select max(pdxh) from CheckQueue where jcxdh=:jcxdh and gwsx=:gwsx")
	public Integer getPdxh(@Param("jcxdh") Integer jcxdh, @Param("gwsx") Integer gwsx);

	@Query(value = "from CheckQueue where gwsx<:gwsx and jcxdh=:jcxdh")
	public List<CheckQueue> getCheckQueueByGwsxAndJcxdh(@Param("gwsx") Integer gwsx, @Param("jcxdh") Integer jcxdh);

	@Query(value = "delete CheckQueue where jylsh=:jylsh")
	@Modifying
	public void deleteCheckQueueByJylsh(@Param("jylsh") String jylsh);
}
