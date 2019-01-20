package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.RecordInfoOfCheckStaff;

@Repository
public interface RecordInfoOfCheckStaffRepository
		extends JpaRepository<RecordInfoOfCheckStaff, Integer>, JpaSpecificationExecutor<RecordInfoOfCheckStaff> {

}
