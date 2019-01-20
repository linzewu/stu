package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.VehFlow;

@Repository
public interface VehFlowRepository extends JpaRepository<VehFlow, Integer> {
	@Query(value = "from VehFlow where jylsh=:jylsh and jycs=:jycs and sx=:sx")
	public List<VehFlow> findByJylshAndJycsAndSx(@Param("jylsh") String jylsh, @Param("jycs") Integer jycs,
			@Param("sx") Integer sx);

	@Query(value = "from VehFlow where jylsh=:jylsh and jycs=:jycs and sx=1 order by sx asc")
	public VehFlow findByJylshAndJycsAndSx(@Param("jylsh") String jylsh, @Param("jycs") Integer jycs);

	@Query(value = "from VehFlow where jylsh=:jylsh and jycs=:jycs and gw=:gw order by sx asc")
	public List<VehFlow> findByJylshAndJycsAndGw(@Param("jylsh") String jylsh, @Param("jycs") Integer jycs,
			@Param("gw") Integer gw);

}
