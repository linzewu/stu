package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehiclePhotos;
@Repository
public interface VehiclePhotosRepository extends JpaRepository<VehiclePhotos, Integer>{
	
	@Query(value = "from VehiclePhotos where lsh = :lsh")
	public VehiclePhotos findVehiclePhotosByLsh(@Param("lsh")String lsh);

}
