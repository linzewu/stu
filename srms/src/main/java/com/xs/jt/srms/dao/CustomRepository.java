package com.xs.jt.srms.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.xs.jt.srms.entity.ArchivalCase;

@Repository
public class CustomRepository {
	
	@Resource(name="srmsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	//@Query(value = "SELECT v.* from arc_sys.tb_user v where yhm=:bh", nativeQuery = true)
	//List<Object> findPcbStVehicle(@Param("bh")String bh);
	
//	public List<Map<String,Object>> findNoUseArchivalCase(String storageType){
//		String sql = "select * from (select t.storage_type,t.archives_no,t.rack_no,t.cell_capacity , t.rack_cols ,"
//				+"t.rack_rows,(t.cell_capacity * t.rack_cols * t.rack_rows) capacitys,(t.cell_capacity * t.rack_cols) colCapacitys,c.usedCapacity,c.file_no from "
//				+"TM_STORE_ROOM t left join (select rack_no,count(file_no) usedCapacity,max(file_no)file_no from "
//				+"TM_ARCHIVAL_CASE group by rack_no)c on t.rack_no = c.rack_no ) where (capacitys >usedCapacity or "
//				+"usedCapacity is null)  and storage_type = ? and rownum =1 order by archives_no asc,rack_no asc";
//		return jdbcTemplate.queryForList(sql, storageType);
//	}
	
	public List<Map<String,Object>> findCheckOutLong(){
		String sql = "select t.*,(sysdate - to_date(t.update_time,'yyyy-mm-dd hh24-mi-ss')) datebet from TM_ARCHIVAL_CASE t "
				+"where t.zt = '2' and t.ywlx in('B','L','M','J','A') and (sysdate - to_date(t.update_time,'yyyy-mm-dd hh24-mi-ss')) > 3";
		return jdbcTemplate.queryForList(sql);
	}
	
	
	public ArchivalCase findNoUseMultiArchivalCase(ArchivalCase archivalCase){
		ArchivalCase arCase = null;
		StringBuffer existSql = new StringBuffer("");
		StringBuffer inSql = new StringBuffer("");
		for (int i = 1; i < archivalCase.getCaseNumber(); i++) {
			existSql.append(" and exists (select 1 from tm_archival_case c1 where c1.archives_no = c.archives_no")
					.append(" and c1.rack_no = c.rack_no and c1.rack_row = c.rack_row and c1.rack_col = c.rack_col")
					.append(" and c1.file_no = replace(lpad((to_number(c.file_no)+"+i+"),3),' ','0') ) ");
			
			inSql.append(",replace(lpad((to_number(b.file_no)+"+i+"),3),' ','0')");
		}
		String sql="select * from (select c.* from tm_archival_case c " + 
				" LEFT JOIN tm_store_room r ON c.rack_no = r.rack_no where r.storage_type = '0' and zt = '0' " + 
				existSql + 
				"         order by r.seq," + 
				"                  c.archives_no," + 
				"                  c.rack_no," + 
				"                  c.rack_row," + 
				"                  c.rack_col," + 
				"                  c.file_no)" + 
				"where rownum = 1";
		List<ArchivalCase> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(ArchivalCase.class));
		if(!CollectionUtils.isEmpty(list)) {
			arCase = list.get(0);
		}
		return arCase;
	}
	
	/**
	 * 在用车指定档案格入库
	 * @param archivalCase
	 * @return
	 */
	public ArchivalCase findSpecialNoUseMultiArchivalCase(ArchivalCase archivalCase){
		ArchivalCase arCase = null;
		StringBuffer existSql = new StringBuffer("");
		StringBuffer inSql = new StringBuffer("");
		for (int i = 1; i < archivalCase.getCaseNumber(); i++) {
			existSql.append(" and exists (select 1 from tm_archival_case c1 where c1.archives_no = c.archives_no")
					.append(" and c1.rack_no = c.rack_no and c1.rack_row = c.rack_row and c1.rack_col = c.rack_col")
					.append(" and c1.file_no = replace(lpad((to_number(c.file_no)+"+i+"),3),' ','0') ) ");
			
			inSql.append(",replace(lpad((to_number(b.file_no)+"+i+"),3),' ','0')");
		}
		String sql="select * from (select c.* from tm_archival_case c LEFT JOIN tm_store_room r ON c.rack_no = r.rack_no" + 
				" where c.archives_no = '"+archivalCase.getArchivesNo()+"' and c.rack_no = '"+archivalCase.getRackNo()+
				"' and c.rack_row = '"+archivalCase.getRackRow()+"' and c.rack_col = '"+archivalCase.getRackCol()+"' and zt = '0' " + 
				existSql + 
				"         order by r.seq," + 
				"                  c.archives_no," + 
				"                  c.rack_no," + 
				"                  c.rack_row," + 
				"                  c.rack_col," + 
				"                  c.file_no)" + 
				"where rownum = 1";
		List<ArchivalCase> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(ArchivalCase.class));
		if(!CollectionUtils.isEmpty(list)) {
			arCase = list.get(0);
		}
		return arCase;
	}
	
	
	public ArchivalCase findNoUseMultiOtherArchivalCase(ArchivalCase archivalCase){
		ArchivalCase arCase = null;
		StringBuffer existSql = new StringBuffer("");
		StringBuffer inSql = new StringBuffer("");
		for (int i = 1; i < archivalCase.getCaseNumber(); i++) {
			existSql.append(" and exists (select 1 from tm_archival_case c1 where c1.archives_no = c.archives_no")
					.append(" and c1.rack_no = c.rack_no and c1.rack_row = c.rack_row and c1.rack_col = c.rack_col")
					.append(" and c1.file_no = replace(lpad((to_number(c.file_no)+"+i+"),3),' ','0') ) ");
			
			inSql.append(",replace(lpad((to_number(b.file_no)+"+i+"),3),' ','0')");
		}
		String sql="select * from (select c.* from tm_archival_case c " + 
				" LEFT JOIN tm_store_room r ON c.rack_no = r.rack_no where r.storage_type = '1' and zt = '0' " + 
				existSql + 
				"         order by r.seq," + 
				"                  c.archives_no," + 
				"                  c.rack_no," + 
				"                  c.rack_row," + 
				"                  c.rack_col," + 
				"                  c.file_no)" + 
				"where rownum = 1";
		List<ArchivalCase> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(ArchivalCase.class));
		if(!CollectionUtils.isEmpty(list)) {
			arCase = list.get(0);
		}
		return arCase;
	}
	
	

}
