package com.xs.jt.srms.dao;

import java.util.List;
import java.util.Map;

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
	public List<ArchivalCase> getArchivalCaseByClsbdh(@Param("clsbdh") String clsbdh);
	
	@Query(value = "from ArchivalCase where barCode = :barCode")
	public List<ArchivalCase> getArchivalCaseByBarCode(@Param("barCode") String barCode);
	
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
	
	@Query(value = "from ArchivalCase where archivesNo = :archivesNo and rackNo = :rackNo and rackRow = :rackRow and rackCol = :rackCol and fileNo in (:fileNo) order by fileNo asc")
	public List<ArchivalCase> findMultiNoUseArchivalCase(@Param("archivesNo") String archivesNo,
			@Param("rackNo") String rackNo, @Param("rackRow") Integer rackRow, @Param("rackCol") Integer rackCol,
			@Param("fileNo") List<String> fileNo);
	
	
	@Query(value = "select new map(archivesNo as archivesNo,rackNo as rackNo,rackRow as rackRow,rackCol as rackCol,max(fileNo) as fileNo) from ArchivalCase  where archivesNo = :archivesNo and rackNo = :rackNo group  by archivesNo,rackNo,rackRow,rackCol")
	public List<Map> getMaxFileNoByArchivesNoAndRackNo(@Param("archivesNo")String archivesNo,@Param("rackNo")String rackNo);
	
	@Query(value = "select new map(archivesNo as archivesNo,rackNo as rackNo,rackRow as rackRow,rackCol as rackCol) from ArchivalCase  where archivesNo = :archivesNo and rackNo = :rackNo and zt =0  group by archivesNo,rackNo,rackRow,rackCol order by archivesNo,rackNo,rackRow,rackCol")
	public List<Map> getNoUsedByArchivesNoAndRackNo(@Param("archivesNo")String archivesNo,@Param("rackNo")String rackNo);
	
	
	@Query(value = "select * from (select c.* from tm_archival_case c   where archives_no = :archivesNo and c.rack_no = :rackNo and rack_row = :rackRow and rack_col = :rackCol and zt = :zt  order by c.archives_no,c.rack_no,c.rack_row,c.rack_col,c.file_no )where rownum =1 ",nativeQuery=true)
	public ArchivalCase findSpecialNoUseArchivalCase(@Param("archivesNo")String archivesNo,@Param("rackNo")String rackNo,@Param("rackRow") Integer rackRow, @Param("rackCol") Integer rackCol,@Param("zt")String zt);
}
