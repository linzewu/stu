package com.xs.jt.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehiclePhotos;
@Repository
public interface VehiclePhotosRepository extends JpaRepository<VehiclePhotos, Integer>{

}
