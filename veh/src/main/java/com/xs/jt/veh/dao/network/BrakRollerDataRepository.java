package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.BrakRollerData;

@Repository
public interface BrakRollerDataRepository extends JpaRepository<BrakRollerData, Integer> {

	@Query(value = "from BrakRollerData where jylsh=:jylsh and jyxm !=:jyxm and sjzt=:sjzt order by jycs desc")
	public List<BrakRollerData> findByJylshAndNotJyxmAndSjzt(@Param("jylsh") String jylsh, @Param("jyxm") String jyxm,
			@Param("sjzt") Integer sjzt);

	@Query(value = "from BrakRollerData where jylsh=:jylsh and sjzt=:sjzt and jyxm in('B1','B2','B3','B4','B5')")
	public List<BrakRollerData> findByJylshAndInJyxmAndSjzt(@Param("jylsh") String jylsh, @Param("sjzt") Integer sjzt);

	@Query(value = "from BrakRollerData where jylsh=:jylsh and sjzt=:sjzt and jyxm<>:jyxm and zpd=:zpd")
	public List<BrakRollerData> findByJylshAndNotJyxmAndSjztAndZpd(@Param("jylsh") String jylsh,
			@Param("jyxm") String jyxm, @Param("sjzt") Integer sjzt, @Param("zpd") Integer zpd);

	@Query(value = "from BrakRollerData where jylsh=:jylsh  order by id desc ")
	public List<BrakRollerData> findByJylshOrderById(@Param("jylsh") String jylsh);

	@Query(value = "from BrakRollerData where jylsh=:jylsh  and jyxm!=:jyxm order by id desc")
	public List<BrakRollerData> findByJylshAndNotJyxmOrderById(@Param("jylsh") String jylsh,
			@Param("jyxm") String jyxm);
}
