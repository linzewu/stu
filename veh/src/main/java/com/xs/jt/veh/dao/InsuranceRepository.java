package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.Insurance;
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
	@Query(value = "from Insurance where jylsh=:jylsh")
	public List<Insurance> findInsuranceByJylsh(@Param("jylsh") String jylsh);

}
