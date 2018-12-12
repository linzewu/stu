package com.xs.jt.base.module.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.SignaturePhoto;
@Repository
public interface SignaturePhotoRepository extends JpaRepository<SignaturePhoto,Integer>{
	
	@Query(value = "from SignaturePhoto where yhm= :yhm")
	SignaturePhoto findByYhm(@Param("yhm")String yhm);

}
