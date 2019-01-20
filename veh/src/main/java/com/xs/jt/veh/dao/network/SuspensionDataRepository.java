package com.xs.jt.veh.dao.network;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.SuspensionData;

@Repository
public interface SuspensionDataRepository extends JpaRepository<SuspensionData, Integer> {

	@Query(value = "select max(dxcs) from SuspensionData where jylsh=:jylsh  and jyxm=:jyxm")
	public Integer getDxjccs(@Param("jylsh") String jylsh, @Param("jyxm") String jyxm);

}
