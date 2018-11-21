package com.xs.jt.cms.manager.impl;

import java.util.List;

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

	@Override
	public List<VehiclePhotos> findPhotosByLshAndJccs(String lsh, int jccs) {
		return vehiclePhotosRepository.findPhotosByLshAndJccs(lsh, jccs);
	}

	@Override
	public List<VehiclePhotos> findPhotosByLsh(String lsh) {
		return vehiclePhotosRepository.findPhotosByLsh(lsh);
	}

	@Override
	public VehiclePhotos findVehPhotoById(Integer id) {
		return this.vehiclePhotosRepository.findById(id).get();
	}

	@Override
	public VehiclePhotos findPhotosByLshAndZpzlAndJccsAndClsbdh(String lsh, String zpzl, int jccs, String clsbdh) {
		return this.vehiclePhotosRepository.findPhotosByLshAndZpzlAndJccsAndClsbdh(lsh, zpzl, jccs, clsbdh);
	}

	@Override
	public void deleteVehiclePhoto(VehiclePhotos photo) {
		vehiclePhotosRepository.delete(photo);
		
	}

}
