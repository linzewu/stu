package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.VehCheckLog;

@Repository
public interface VehCheckLogRepository extends JpaRepository<VehCheckLog, Integer> {

}
