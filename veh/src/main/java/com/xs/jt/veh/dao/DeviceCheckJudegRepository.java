package com.xs.jt.veh.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.DeviceCheckJudeg;

@Repository
public interface DeviceCheckJudegRepository extends JpaRepository<DeviceCheckJudeg, Integer> {

	@Query(value = "from DeviceCheckJudeg where jylsh=:jylsh")
	public List<DeviceCheckJudeg> findDeviceCheckJudegByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "delete DeviceCheckJudeg where jylsh=:jylsh and jyjgbh=:jyjgbh")
	@Modifying
	public void delDeviceCheckJudegByJylshAndJyjgbh(@Param("jylsh") String jylsh, @Param("jyjgbh") String jyjgbh);

	@Query(value = "from DeviceCheckJudeg where jylsh=:jylsh and bz1='R' ")
	public List<DeviceCheckJudeg> findDeviceCheckJudegByJylshAndBz1(@Param("jylsh") String jylsh);

	@Query(value = "select count(*) from DeviceCheckJudeg where jylsh=:jylsh")
	public Integer findCountByJylsh(@Param("jylsh") String jylsh);
	
	@Query(value = "select new map(xh,yqjyxm,yqjyjg,yqbzxz,yqjgpd,yqjybz) from DeviceCheckJudeg where jylsh=:jylsh")
    List<Map<String,Object>> findDeviceCheckJudegMapByJylsh(@Param("jylsh") String jylsh);

}
