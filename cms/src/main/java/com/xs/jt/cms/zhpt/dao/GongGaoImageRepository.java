package com.xs.jt.cms.zhpt.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class GongGaoImageRepository {
	
	@Resource(name="oraImgJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	public List getGongGaoImageList(List<String> zpbh){
	 	String sql = "SELECT ZP  FROM trff_zjk.PCB_ST_PHOTO WHERE ZPBH IN (:zpbh)";
	 	
	 	Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("zpbh", zpbh);
	    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	    List imageList = namedParameterJdbcTemplate.queryForList(sql,paramMap);

	 	return imageList;
//		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
//		try{
//			SQLQuery  query = session.createSQLQuery(sql);
//			query.addScalar("ZP",BlobType.INSTANCE);
//			query.setParameterList("zpbh", zpbh);
//			return query.list();
//		}finally{
//			session.clear();
//			session.close();
//		}
		
	}

}
