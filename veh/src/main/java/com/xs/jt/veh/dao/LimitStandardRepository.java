package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.LimitStandard;

@Repository
public interface LimitStandardRepository extends JpaRepository<LimitStandard, Integer> {

}
