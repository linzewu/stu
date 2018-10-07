package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehiclePhotos;
@Repository
public interface VehiclePhotosRepository extends JpaRepository<VehiclePhotos, Integer>{
	
	@Query(value = "select top 1  t.*  from TM_VehiclePhotos t where lsh = :lsh and zpzl=:zpzl order by jccs desc",nativeQuery = true)
	public VehiclePhotos findLastPhotosByLshAndZpzl(@Param("lsh")String lsh,@Param("zpzl") String  zpzl);

}
