package com.xs.jt.veh.dao;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.MyTable;

@Repository
public interface MyTableRepository extends CrudRepository<MyTable, Long> {

	@Procedure(name = "getJYLSH")
	String getJylsh(@Param("Tabel_Name") String Tabel_Name, @Param("prefix") String prefix);

}
