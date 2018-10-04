package com.xs.jt.cms.zhpt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class  PDAServiceRepository {
	
	@Resource
    private JdbcTemplate jdbcTemplate;

	//@Query(value = "SELECT v.* from arc_sys.tb_user v where yhm=:bh", nativeQuery = true)
	//List<Object> findPcbStVehicle(@Param("bh")String bh);
	
	public List<Map<String,Object>> findPcbStVehicle(String bh){
		String sql = "SELECT v.* from PCB_ST_VEHICLE v where bh=?";
		return jdbcTemplate.queryForList(sql, bh);
	}
	
	public  List<Map<String,Object>> findGongGaoListbyCLXH(String clxh) {
		String sql = "SELECT PSV.BH,PSV.CLXH,TO_CHAR(PSV.GGRQ,'YYYY-MM-DD') ||' '|| PSV.CLXH  as GGRQ  FROM trff_app.PCB_ST_VEHICLE PSV"
				+ " WHERE PSV.CLXH LIKE ? order by PSV.GGRQ desc";
		return jdbcTemplate.queryForList(sql,clxh+"%");
	}
	
	public  List<Map<String,Object>> getListOfDPGG(String dpid){
		String sql = "select c.* from trff_app.PCB_ST_CHASSIS c where dpid=?";
		return jdbcTemplate.queryForList(sql, dpid);
	}
	
	public  List<Map<String,Object>> getDPGG(String bh){
		String sql = "select c.* from trff_app.PCB_ST_CHASSIS c where bh=?";
		return jdbcTemplate.queryForList(sql, bh);
	}
}
