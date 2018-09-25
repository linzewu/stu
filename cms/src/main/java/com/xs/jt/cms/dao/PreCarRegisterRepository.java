package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.PreCarRegister;

@Repository
public interface PreCarRegisterRepository extends JpaRepository<PreCarRegister, Integer> {

}
