package com.xs.jt.cmsvideo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.PreCarRegister;

@Repository
public interface PreCarRegisterRepository extends JpaRepository<PreCarRegister, Integer>,JpaSpecificationExecutor<PreCarRegister> {

	@Query(value = "from PreCarRegister where lsh = :lsh")
	PreCarRegister findPreCarRegisterByLsh(@Param("lsh")String lsh);
}
