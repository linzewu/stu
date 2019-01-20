package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.Outline;

@Repository
public interface OutlineRepository extends JpaRepository<Outline, Integer> {

	@Query(value = "from Outline where jylsh=:jylsh order by id desc ")
	public List<Outline> findOutlineByJylsh(@Param("jylsh") String jylsh);
	
	@Query(value = "from Outline where reportFlag!=:reportFlag or reportFlag is null ")
	public List<Outline> findOutlineByReportFlag(@Param("reportFlag") Integer reportFlag);

}
