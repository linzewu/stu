package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.SpeedData;

@Repository
public interface SpeedDataRepository extends JpaRepository<SpeedData, Integer> {
	@Query(value = "from SpeedData where   jylsh=:jylsh and sjzt=:sjzt order by jycs desc")
	public List<SpeedData> findSpeedDataByJylshAndSjzt(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt);

	@Query(value = "from SpeedData where jylsh=:jylsh and sjzt=:sjzt  and zpd=:zpd")
	public List<SpeedData> findSpeedDataByJylshAndSjztAndZpd(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt,
			@Param("zpd") Integer zpd);

	@Query(value = "select max(dxcs) from SpeedData where jylsh=:jylsh  and jyxm=:jyxm")
	public Integer getDxjccs(@Param("jylsh") String jylsh, @Param("jyxm") String jyxm);

	@Query(value = "from SpeedData where jylsh=:jylsh order by id desc")
	public List<SpeedData> findSpeedDataByJylsh(@Param("jylsh") String jylsh);
}
