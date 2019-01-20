package com.xs.jt.veh.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class ViewQueryRepository {
	private static Log logger = LogFactory.getLog(ViewQueryRepository.class);
	
	@Resource(name="vehJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	//@Query(value = "SELECT v.* from arc_sys.tb_user v where yhm=:bh", nativeQuery = true)
	//List<Object> findPcbStVehicle(@Param("bh")String bh);
	
	public List<Map<String,Object>> getViewData(String viewName, String jylsh,String jyxm){
		String jyxmSql="";
		Object[] args = null;
		int[] argTypes = null;
		
		boolean jyxmFlag=viewName.equals("V18C55")||viewName.equals("V18C58");
		
		if(jyxmFlag){
			jyxmSql=" and jyxm=?";
		}
		
		String sql = "select top 1 * from " + viewName + "  where jylsh=? "+jyxmSql;
		
		//sq.setString("jylsh", jylsh);
				
		if(jyxmFlag){
			args = new Object[2];
			args[0] = jylsh;
			args[1] = jyxm;
			argTypes = new int[2];
			argTypes[0] = java.sql.Types.VARCHAR;
			argTypes[1] = java.sql.Types.VARCHAR;
			//sq.setString("jyxm", jyxm);
			logger.info("sql:"+sql);
		}else {
			args = new Object[1];
			args[0] = jylsh;
			argTypes = new int[1];
			argTypes[0] = java.sql.Types.VARCHAR;
		}
		logger.info("ViewName " + viewName ); 
//		List<?> entitys =sq.setFirstResult(0).setMaxResults(1)
//				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list(); 
//		return entitys;
		return jdbcTemplate.queryForList(sql, args, argTypes);
	}

}
