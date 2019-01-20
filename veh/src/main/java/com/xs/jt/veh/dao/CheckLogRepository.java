package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.CheckLog;

@Repository
public interface CheckLogRepository extends JpaRepository<CheckLog, Integer> {

	@Query(value = "from CheckLog where jylsh=:jylsh and jkbmc=:jkbmc")
	public List<CheckLog> findByJylshAndJkbmc(@Param("jylsh") String jylsh, @Param("jkbmc") String jkbmc);

	@Query(value = "from CheckLog where jylsh=:jylsh")
	public List<CheckLog> findCheckLogByJylsh(@Param("jylsh") String jylsh);

}
