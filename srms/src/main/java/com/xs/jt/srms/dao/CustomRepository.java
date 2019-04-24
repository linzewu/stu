package com.xs.jt.srms.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.ArchivalRegister;

@Repository
public class CustomRepository {
	
	@Resource(name="srmsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	//@Query(value = "SELECT v.* from arc_sys.tb_user v where yhm=:bh", nativeQuery = true)
	//List<Object> findPcbStVehicle(@Param("bh")String bh);
	
	public List<Map<String,Object>> findNoUseArchivalCase(String storageType){
		String sql = "select * from (select t.storage_type,t.archives_no,t.rack_no,t.cell_capacity , t.rack_cols ,"
				+"t.rack_rows,(t.cell_capacity * t.rack_cols * t.rack_rows) capacitys,(t.cell_capacity * t.rack_cols) colCapacitys,c.usedCapacity,c.file_no from "
				+"TM_STORE_ROOM t left join (select rack_no,count(file_no) usedCapacity,max(file_no)file_no from "
				+"TM_ARCHIVAL_CASE group by rack_no)c on t.rack_no = c.rack_no ) where (capacitys >usedCapacity or "
				+"usedCapacity is null)  and storage_type = ? and rownum =1 order by archives_no asc,rack_no asc";
		return jdbcTemplate.queryForList(sql, storageType);
	}
	
	public List<Map<String,Object>> findCheckOutLong(){
		String sql = "select t.*,(sysdate - to_date(t.update_time,'yyyy-mm-dd hh24-mi-ss')) datebet from TM_ARCHIVAL_CASE t "
				+"where t.zt = '2' and t.ywlx in('B','L','M','J','A') and (sysdate - to_date(t.update_time,'yyyy-mm-dd hh24-mi-ss')) > 3";
		return jdbcTemplate.queryForList(sql);
	}
	
	

}
