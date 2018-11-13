package com.xs.jt.cms.zhpt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class  PDAServiceRepository {
	
	@Resource(name="oraJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	//@Query(value = "SELECT v.* from arc_sys.tb_user v where yhm=:bh", nativeQuery = true)
	//List<Object> findPcbStVehicle(@Param("bh")String bh);
	
	public List<Map<String,Object>> findPcbStVehicle(String bh){
		String sql = "SELECT v.* from TRFF_APP.PCB_FINAL_PARA v where bh=?";
		return jdbcTemplate.queryForList(sql, bh);
	}
	//trff_app.
	public  List<Map<String,Object>> findGongGaoListbyCLXH(String clxh) {
		String sql = "SELECT PSV.BH,PSV.CLXH,TO_CHAR(PSV.GGRQ,'YYYY-MM-DD') ||' '|| PSV.CLXH  as GGRQ  FROM TRFF_APP.PCB_FINAL_PARA PSV"
				+ " WHERE PSV.CLXH = ? order by PSV.GGRQ desc";
		return jdbcTemplate.queryForList(sql,clxh);
	}
	
	
	public  List<Map<String,Object>> findAllGongGaoListbyCLXH(String clxh) {
		String sql = "SELECT PSV.*,TO_CHAR(PSV.GGRQ,'YYYY-MM-DD') ||' '|| PSV.CLXH  as GGRQ  FROM TRFF_APP.PCB_FINAL_PARA PSV"
				+ " WHERE PSV.CLXH = ? order by PSV.GGRQ desc";
		return jdbcTemplate.queryForList(sql,clxh);
	}
	
	
	public  List<Map<String,Object>> getListOfDPGG(String dpid){
		String sql = "select c.* from TRFF_APP.PCB_ST_CHASSIS c where dpid=?";
		return jdbcTemplate.queryForList(sql, dpid);
	}
	
	public  List<Map<String,Object>> getDPGG(String bh){
		String sql = "select c.* from TRFF_APP.PCB_ST_CHASSIS c where bh=?";
		return jdbcTemplate.queryForList(sql, bh);
	}
	public  List<Map<String,Object>> getImplCarParam(String clxh){
		String sql = "select * from TRFF_APP.PCB_FINAL_PARA where clxh like ? and rownum <= 100";
		return jdbcTemplate.queryForList(sql, clxh + "%");
	}
	
	public Map<String,Object> getGongGaoInfo(String clxh){
		String sql = "SELECT * from TRFF_APP.PCB_FINAL_PARA PSV where PSV.CLXH = ? and rownum = 1 order by PSV.GGRQ desc";
		return jdbcTemplate.queryForMap(sql, clxh);
	}
	
	public List<String> getZPBHList(String bh){
		String sql="Select PSPS.ZPBH from TRFF_APP.PCB_ST_PHOTODES PSPS WHERE PSPS.BH=?";
		return jdbcTemplate.queryForList(sql, String.class,bh);
		
	}
	
	public String getFirstGGBH(String clxh) {

		String sql = "SELECT PSV.BH,PSV.CLXH,TO_CHAR(PSV.GGRQ,'YYYY-MM-DD') ||' '|| PSV.CLXH  as GGRQ  FROM trff_app.PCB_FINAL_PARA PSV"
				+ " WHERE PSV.CLXH = ? order by PSV.GGRQ desc";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, clxh);

		if (list == null || list.size() == 0) {
			return "";
		} else {
			Map<String,Object> map = list.get(0);
			return (String) map.get("BH");
		}

	}
	
}
