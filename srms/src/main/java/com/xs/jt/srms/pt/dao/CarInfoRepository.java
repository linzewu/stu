package com.xs.jt.srms.pt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class CarInfoRepository {
	
	@Resource(name="oraJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	
	public List<Map<String,Object>> getCarInfoByBarcode(String barcode){
		String sql = "select c.* from veh_flow c where lsh=?";
		return jdbcTemplate.queryForList(sql, barcode);
		
	}
	
	public List<Map<String,Object>> getCarInfoByClsbdh(String clsbdh){
		String sql = "select c.* from veh_flow c where clsbdh=?";
		return jdbcTemplate.queryForList(sql, clsbdh);
		
	}

}
