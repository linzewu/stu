package com.xs.jt.srms.pt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class VehCarInfoRepository {
	
	@Resource(name="oraJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	
	public List<Map<String,Object>> getVehCarInfosByLsh(Integer page, Integer rows,Map param){
		String sql = "select c.* from VEHICLE c where 1=1 ";
		
		//jdbcTemplate.queryforli
		List params = new ArrayList();
		if(param.get("hphm") != null && (!"".equals(param.get("hphm")))) {
			sql = sql + " and hphm=?";
			params.add(param.get("hphm").toString());
		}
		if(param.get("hpzl")!= null && (!"".equals(param.get("hpzl")))) {
			sql = sql + " and hpzl=?";
			params.add(param.get("hpzl").toString());
		}
		if(param.get("clsbdh")!= null && (!"".equals(param.get("clsbdh")))) {
			sql = sql + " and clsbdh=?";
			params.add(param.get("clsbdh").toString());
		}
		if(param.get("sfzmhm")!= null && (!"".equals(param.get("sfzmhm")))) {
			sql = sql + " and sfzmhm=?";
			params.add(param.get("sfzmhm").toString());
		}
		if(param.get("xm")!= null && (!"".equals(param.get("xm")))) {
			sql = sql + " and syr=?";
			params.add(param.get("xm").toString());
		}
		String sqlStr = "SELECT * FROM(SELECT A.*, ROWNUM RN  FROM ("+sql+") A WHERE ROWNUM <= "+(page*rows)+") where RN >="+(((page-1)*rows)+1);
		
		System.out.println(sqlStr);
		return jdbcTemplate.queryForList(sqlStr, params.toArray());
		
	}
	
	public int getVehCarInfoCount(Map param) {
		String sql = "select count(*) cou from VEHICLE c where 1=1";
		List params = new ArrayList();
		if(param.get("hphm")!= null && (!"".equals(param.get("hphm")))) {
			sql = sql + " and hphm=?";
			params.add(param.get("hphm").toString());
		}
		if(param.get("hpzl")!= null && (!"".equals(param.get("hpzl")))) {
			sql = sql + " and hpzl=?";
			params.add(param.get("hpzl").toString());
		}
		if(param.get("clsbdh")!= null && (!"".equals(param.get("clsbdh")))) {
			sql = sql + " and clsbdh=?";
			params.add(param.get("clsbdh").toString());
		}
		if(param.get("sfzmhm")!= null && (!"".equals(param.get("sfzmhm")))) {
			sql = sql + " and sfzmhm=?";
			params.add(param.get("sfzmhm").toString());
		}
		if(param.get("xm")!= null && (!"".equals(param.get("xm")))) {
			sql = sql + " and syr=?";
			params.add(param.get("xm").toString());
		}
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, params.toArray());
		int count = 0;
		if(!CollectionUtils.isEmpty(list)) {
			count = Integer.parseInt(list.get(0).get("cou").toString());
		}
		return count;
	}

}
