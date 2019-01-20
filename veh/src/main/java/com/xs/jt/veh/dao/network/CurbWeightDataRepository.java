package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.CurbWeightData;

@Repository
public interface CurbWeightDataRepository extends JpaRepository<CurbWeightData, Integer> {

	@Query(value = "from CurbWeightData where jylsh=:jylsh order by id desc")
	public List<CurbWeightData> getLastCurbWeightDataOfJylsh(@Param("jylsh") String jylsh);

}
