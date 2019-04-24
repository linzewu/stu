package com.xs.jt.srms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xs.jt.srms.entity.ArchivalWarn;

@Repository
public interface ArchivalWarnRepository extends JpaRepository<ArchivalWarn, Integer>,JpaSpecificationExecutor<ArchivalWarn>{

}
