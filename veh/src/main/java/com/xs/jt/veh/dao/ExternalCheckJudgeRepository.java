package com.xs.jt.veh.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.ExternalCheckJudge;

@Repository
public interface ExternalCheckJudgeRepository extends JpaRepository<ExternalCheckJudge, Integer> {

	@Query(value = "from ExternalCheckJudge where jylsh=:jylsh")
	public List<ExternalCheckJudge> findExternalCheckJudgeByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "delete ExternalCheckJudge where jylsh=:jylsh and jyjgbh=:jyjgbh")
	@Modifying
	public void deleteExternalCheckJudgeByJylshAndJyjgbh(@Param("jylsh") String jylsh, @Param("jyjgbh") String jyjgbh);
	
	@Query(value = "select new map(xh,rgjyxm,rgjgpd,rgjysm,rgjybz) from ExternalCheckJudge where jylsh=:jylsh")
	List<Map<String,Object>> findExternalCheckJudgeMapByJylsh(@Param("jylsh") String jylsh);
}
