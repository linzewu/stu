package com.xs.jt.cmsvideo.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomQueryRepository {
	@Resource(name="cmsVideoJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> findPcbStVehicle(String bh){
		String sql = "SELECT w.lsh,w.jycs,w.vid,w.remark,w.type, from tm_Video_Warn w left join tm_Video_Info v on w.vid = v.id where bh=?";
		return jdbcTemplate.queryForList(sql, bh);
	}
}
