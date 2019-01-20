package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.RecordInfoOfJcx;

@Repository
public interface RecordInfoOfJcxRepository extends JpaRepository<RecordInfoOfJcx, Integer> {

}
