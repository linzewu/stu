package com.xs.jt.srms.vehimg.dao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.vehimg.entity.VehImg;

@Repository
public interface VehImgRepository extends JpaRepository<VehImg, Integer>{
	
	@Query(value = "from VehImg where lsh = :lsh")
	public List<VehImg> getVehImgByLsh(@Param("lsh")String lsh);
	
	@Query(value = "from VehImg where lsh=:lsh and zplx=:zplx")
	public List<VehImg> getVehImgByLshAndZplx(@Param("lsh")String lsh,@Param("zplx")String zplx);
	
	@Query(value = "select new map(max(hphm) as hphm ,max(hpzl) as hpzl,clsbdh,max(ywlx) as ywlx,lsh,to_char(min(pssj),'yyyy-MM-dd') as pssj)  from VehImg where clsbdh=:clsbdh group by clsbdh,lsh")
	public List<Map<String,Object>> getYwListByClsbdh(@Param("clsbdh")String clsbdh);
	
	@Query(value = "from VehImg where lsh=:lsh and zpzt='0'")
	public List<VehImg> getVehImgByLshOfzc(@Param("lsh")String lsh);
	
	@Query(value = "select new map(id as id,hphm as hphm,hpzl as hpzl,clsbdh as clsbdh,ywlx as ywlx,lsh as lsh)  from VehImg where lsh = :lsh")
	public List<Map<String,Object>> getVehImgMapByLsh(@Param("lsh")String lsh);
	
	@Transactional
	@Modifying
    @Query("delete from VehImg v where v.lsh=:lsh")
	public void deleteVehImgByLsh(@Param("lsh")String lsh);
	
	
//	@Resource(name="imgOraJdbcTemplate")
//    private JdbcTemplate jdbcTemplate;
//	
//	public List<Map<String,Object>> getVehImgByLsh(String lsh){
//		String sql = "select c.* from TM_VEH_IMG c where lsh=?";
//		return jdbcTemplate.queryForList(sql, lsh);		
//	}

}
