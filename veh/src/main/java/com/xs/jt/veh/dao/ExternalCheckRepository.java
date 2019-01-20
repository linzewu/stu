package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.ExternalCheck;

@Repository
public interface ExternalCheckRepository extends JpaRepository<ExternalCheck, Integer> {

	@Query(value = "from ExternalCheck where jylsh=:jylsh")
	public List<ExternalCheck> getExternalCheckByJyLsh(@Param("jylsh") String jylsh);

}
