package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.CheckPhoto;

@Repository
public interface CheckPhotoRepository extends JpaRepository<CheckPhoto, Integer> {

	@Query(value = "select new CheckPhoto(id,jyjgbh,jcxdh,jylsh,hphm,hpzl,clsbdh, jycs,pssj,jyxm,zpzl) from CheckPhoto where jylsh=:jylsh")
	public List<CheckPhoto> findCheckPhotoByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "delete CheckPhoto where jylsh=:jylsh and zpzl=:zpzl")
	@Modifying
	public void deleteCheckPhotoByJylshAndZpzl(@Param("jylsh") String jylsh, @Param("zpzl") String zpzl);

	@Query(value = "from CheckPhoto where jylsh=:jylsh and zpzl=:zpzl and jycs=:jycs")
	public List<CheckPhoto> findCheckPhotoByJylshAndZpzlAndJycs(@Param("jylsh") String jylsh,
			@Param("zpzl") String zpzl, @Param("jycs") Integer jycs);

}
