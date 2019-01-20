package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.CheckEvents;

@Repository
public interface CheckEventRepository
		extends JpaRepository<CheckEvents, Integer>, JpaSpecificationExecutor<CheckEvents> {

	@Query(value = "from CheckEvents where jylsh=:jylsh")
	public List<CheckEvents> findCheckEventsByJylsh(@Param("jylsh") String jylsh);

	@Modifying
	@Query(value = "update CheckEvents set state=0 where jylsh=:jylsh ")
	public void updateCheckEventsByJylsh(@Param("jylsh") String jylsh);
}
