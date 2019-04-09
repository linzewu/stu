package com.xs.jt.srms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.StoreRoom;
@Repository
public interface StoreRoomRepository extends JpaRepository<StoreRoom, Integer>,JpaSpecificationExecutor<StoreRoom>{

}
