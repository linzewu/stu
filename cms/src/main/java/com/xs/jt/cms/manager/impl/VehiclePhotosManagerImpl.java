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

	public VehiclePhotos findVehiclePhotosByLsh(String lsh) {
		return vehiclePhotosRepository.findVehiclePhotosByLsh(lsh);
	}

}
