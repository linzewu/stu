package com.xs.jt.srms.dao;

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
}
