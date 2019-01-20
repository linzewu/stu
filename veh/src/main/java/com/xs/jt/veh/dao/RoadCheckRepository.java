package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.RoadCheck;

@Repository
public interface RoadCheckRepository extends JpaRepository<RoadCheck, Integer> {

	@Query(value = "from RoadCheck where jylsh=:jylsh")
	public RoadCheck findRoadCheckByJylsh(@Param("jylsh") String jylsh);

}
