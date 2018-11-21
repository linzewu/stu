package com.xs.jt.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehiclePhotos;
@Repository
public interface VehiclePhotosRepository extends JpaRepository<VehiclePhotos, Integer>{
	
	@Query(value = "select top 1  t.*  from TM_VehiclePhotos t where lsh = :lsh and zpzl=:zpzl order by jccs desc",nativeQuery = true)
	public VehiclePhotos findLastPhotosByLshAndZpzl(@Param("lsh")String lsh,@Param("zpzl") String  zpzl);
	
	@Query(value = "select top 1  t.*  from TM_VehiclePhotos t where lsh = :lsh and zpzl=:zpzl and jccs=:jccs",nativeQuery = true)
	public VehiclePhotos findPhotosByLshAndZpzlAndJccs(@Param("lsh")String lsh,@Param("zpzl") String  zpzl,@Param("jccs") int jccs);
	
	@Query(value = "select lsh,hphm,hpzl,jccs,zpzl,clsbdh  from VehiclePhotos t where lsh = :lsh and jccs=:jccs")
	public List<VehiclePhotos> findPhotosByLshAndJccs(@Param("lsh")String lsh,@Param("jccs") int jccs);
	
	@Query(value = "select id,lsh,hphm,hpzl,jccs,zpzl,clsbdh  from VehiclePhotos t where lsh = :lsh")
	public List<VehiclePhotos> findPhotosByLsh(@Param("lsh")String lsh);
	
	@Query(value = "select top 1  t.*  from TM_VehiclePhotos t where lsh = :lsh and zpzl=:zpzl and jccs=:jccs and clsbdh=:clsbdh",nativeQuery = true)
	public VehiclePhotos findPhotosByLshAndZpzlAndJccsAndClsbdh(@Param("lsh")String lsh,@Param("zpzl") String  zpzl,@Param("jccs") int jccs,@Param("clsbdh") String  clsbdh);
	
	

}
