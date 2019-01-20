package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.VehCheckProcess;

@Repository
public interface VehCheckProcessRepository extends JpaRepository<VehCheckProcess, Integer> {

	@Query(value = "from VehCheckProcess where jylsh=:jylsh and jycs=:jycs  and jyxm=:jyxm")
	public VehCheckProcess getVehCheckProces(@Param("jylsh") String jylsh, @Param("jycs") Integer jycs,
			@Param("jyxm") String jyxm);

	@Query(value = "from VehCheckProcess where jylsh=:jylsh")
	public List<VehCheckProcess> getVehCheckProcesByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "from VehCheckProcess where jylsh=:jylsh and jyxm in('R1','R2','R3')")
	public List<VehCheckProcess> getVehCheckProcesByJylshAndInJyxm(@Param("jylsh") String jylsh);

	@Query(value = "from VehCheckProcess where jylsh=:jylsh and jycs =:jycs")
	public List<VehCheckProcess> getVehCheckProcesByJylshAndJycs(@Param("jylsh") String jylsh,
			@Param("jycs") Integer jycs);

	@Query(value = "From VehCheckProcess where jylsh =:jylsh order by jylsh desc")
	public List<VehCheckProcess> getVehCheckProcesByJylshOrderByJylsh(@Param("jylsh") String jylsh);

}
