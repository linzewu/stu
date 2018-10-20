package com.xs.jt.cms.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.VehiclePhotosRepository;
import com.xs.jt.cms.entity.VehiclePhotos;
import com.xs.jt.cms.manager.IVehiclePhotosManager;
@Service("vehiclePhotosManager")
public class VehiclePhotosManagerImpl implements IVehiclePhotosManager {
	@Autowired
	public VehiclePhotosRepository vehiclePhotosRepository;

	public VehiclePhotos save(VehiclePhotos motorVehiclePhotos) {
		return vehiclePhotosRepository.save(motorVehiclePhotos);
	}
	
	public VehiclePhotos findLast45degPhotosByLsh(String lsh) {
		VehiclePhotos photos = vehiclePhotosRepository.findLastPhotosByLshAndZpzl(lsh,"11");
		
		return photos;
	}

	public VehiclePhotos findPhotosByLshAndZpzlAndJccs(String lsh, String zpzl, int jccs) {
		VehiclePhotos photos = vehiclePhotosRepository.findPhotosByLshAndZpzlAndJccs(lsh, zpzl, jccs);
		return photos;
	}

}
