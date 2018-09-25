package com.xs.jt.base.module.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.CoreFunction;
@Repository
public interface CoreFunctionRepository extends JpaRepository<CoreFunction,Integer>{
	
	@Modifying
    @Query("delete from CoreFunction c where c.status = :status")
    void deleteCoreFunction(@Param("status")int status);
	
	
	@Query(value = "select c from CoreFunction c where status = :status")
	List<CoreFunction> findCoreFunctionByStatus(@Param("status")int status);

}
