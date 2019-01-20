package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.OtherInfoData;

@Repository
public interface OtherInfoDataRepository extends JpaRepository<OtherInfoData, Integer> {

	@Query(value = "from OtherInfoData where jylsh=:jylsh order by id desc")
	public List<OtherInfoData> findOtherInfoDataByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "from OtherInfoData where jylsh=:jylsh and zczdpd=:zczdpd")
	public List<OtherInfoData> findOtherInfoDataByJylshAndZczdpd(@Param("jylsh") String jylsh,
			@Param("zczdpd") String zczdpd);

}
