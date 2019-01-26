package com.xs.jt.base.module.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.base.module.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User>{
	
	@Query(value = "from User where yhm= :yhm")
	User findByYhm(@Param("yhm")String yhm);
	
	@Query(value = "from User where sfzh= :sfzh")
	User findBySfzh(@Param("sfzh")String sfzh);
	
	@Query(value = "from User  where yhm!='admin'")
	public List<User> getUsersExceptAdmin();

}
