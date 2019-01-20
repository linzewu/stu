package com.xs.jt.veh.dao.network;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.network.ParDataOfAnjian;

@Repository
public interface ParDataOfAnjianRepository extends JpaRepository<ParDataOfAnjian, Integer> {

	@Query(value = "from ParDataOfAnjian where jylsh=:jylsh order by id desc")
	public List<ParDataOfAnjian> findParDataOfAnjianByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "from ParDataOfAnjian where jylsh=:jylsh and sjzt=:sjzt  and tczdpd=:tczdpd")
	public List<ParDataOfAnjian> findParDataOfAnjianByJylshAndSjztAndTczdpd(@Param("jylsh") String jylsh,
			@Param("sjzt") Integer sjzt, @Param("tczdpd") Integer tczdpd);

}
