package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.LightData;

@Repository
public interface LightDataRepository extends JpaRepository<LightData, Integer> {
	@Query(value = "from LightData where  jylsh=:jylsh and sjzt=:sjzt order by jycs desc")
	public List<LightData> findLightDataByJylshAndSjzt(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt);

	@Query(value = "from LightData where jylsh=:jylsh and sjzt=:sjzt  and zpd=:zpd")
	public List<LightData> findLightDataByJylshAndSjztAndZpd(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt,
			@Param("zpd") Integer zpd);

	@Query(value = "from LightData where jylsh=:jylsh and jyxm like 'H%' order by id asc")
	public List<LightData> findLightDataByJylshAndJyxm(@Param("jylsh") String jylsh);

}
