package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.SideslipData;

@Repository
public interface SideslipDataRepository extends JpaRepository<SideslipData, Integer> {

	@Query(value = "from SideslipData where jylsh=:jylsh and sjzt=:sjzt order by jycs desc")
	public List<SideslipData> findSideslipDataByJylshAndSjzt(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt);

	@Query(value = "from SideslipData where jylsh=:jylsh and sjzt=:sjzt  and zpd=:zpd")
	public List<SideslipData> findSideslipDataByJylshAndSjztAndZpd(@Param("jylsh") String jylsh,
			@Param("sjzt") Integer sjzt, @Param("zpd") Integer zpd);

	@Query(value = "select max(dxcs) from SideslipData where jylsh=:jylsh  and jyxm=:jyxm")
	public Integer getDxjccs(@Param("jylsh") String jylsh, @Param("jyxm") String jyxm);

	@Query(value = "from SideslipData where jylsh=:jylsh and sjzt=:sjzt order by id desc")
	public List<SideslipData> findSideslipDataByJylshAndSjztOrderById(@Param("jylsh") String jylsh,
			@Param("sjzt") Integer sjzt);

	@Query(value = "from SideslipData  where jylsh=:jylsh order by id desc")
	public List<SideslipData> findSideslipDataByJylshOrderById(@Param("jylsh") String jylsh);

}
