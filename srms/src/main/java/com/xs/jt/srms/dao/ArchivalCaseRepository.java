package com.xs.jt.srms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.ArchivalCase;
@Repository
public interface ArchivalCaseRepository extends JpaRepository<ArchivalCase, Integer>,JpaSpecificationExecutor<ArchivalCase>{

	@Query(value = "from ArchivalCase where clsbdh = :clsbdh")
	public ArchivalCase getArchivalCaseByClsbdh(@Param("clsbdh") String clsbdh);
	@Query(value = "select * from (select c.* from tm_archival_case c LEFT JOIN tm_store_room r ON  c.rack_no = r.rack_no where r.storage_type = :storageType and zt = :zt  order by r.seq,c.archives_no,c.rack_no,c.rack_row,c.rack_col,c.file_no )where rownum =1 ",nativeQuery=true)
	public ArchivalCase findNoUseArchivalCase(@Param("storageType")String storageType,@Param("zt")String zt);
	
	/**
	 * 根据档案架删除档案格
	 * @param rackNo
	 */
	@Modifying
    @Query("delete from ArchivalCase r where r.rackNo = :rackNo")
    void deleteArchivalCaseByRackNo(@Param("rackNo")String rackNo);
	
	/**
	 * 根据档案室删除档案格
	 * @param rackNo
	 */
	@Modifying
    @Query("delete from ArchivalCase r where r.archivesNo = :archivesNo")
    void deleteArchivalCaseByArchivesNo(@Param("archivesNo")String archivesNo);
	
	
	@Query(value = "from ArchivalCase where zt != :zt and rackNo = :rackNo")
	public List<ArchivalCase> findUseArchivalCase(@Param("zt")String zt,@Param("rackNo")String rackNo);
}