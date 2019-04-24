package com.xs.jt.srms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.StoreRoom;
@Repository
public interface StoreRoomRepository extends JpaRepository<StoreRoom, Integer>,JpaSpecificationExecutor<StoreRoom>{

	@Modifying
    @Query("delete from StoreRoom r where r.archivesNo = :archivesNo")
    void deleteStoreRoomByArchivesNo(@Param("archivesNo")String archivesNo);
	
	//  select rack_no,(rack_cols*rack_rows*cell_capacity) sy,(select count(file_no) from [srmsdb].[dbo].[tm_archival_case] c where c.rack_no = t.rack_no) from [srmsdb].[dbo].[tm_store_room] t
	
	@Query(value = "select new map(r.archivesNo as archivesNo,r.rackNo as rackNo,(r.rackCols*r.rackRows*r.cellCapacity) as capacity,(select count(c.fileNo) from ArchivalCase c where c.rackNo = r.rackNo and c.zt != :zt) as use )from StoreRoom r")
	public List<Map> getArchiveRackStatistics(@Param("zt")String zt);
}
