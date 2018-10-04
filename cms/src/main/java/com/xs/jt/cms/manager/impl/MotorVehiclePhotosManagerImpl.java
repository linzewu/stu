package com.xs.jt.cms.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.MotorVehiclePhotosRepository;
import com.xs.jt.cms.entity.MotorVehiclePhotos;
import com.xs.jt.cms.manager.IMotorVehiclePhotosManager;
@Service("motorVehiclePhotosManager")
public class MotorVehiclePhotosManagerImpl implements IMotorVehiclePhotosManager {
	@Autowired
	public MotorVehiclePhotosRepository motorVehiclePhotosRepository;

	public MotorVehiclePhotos save(MotorVehiclePhotos motorVehiclePhotos) {
		return motorVehiclePhotosRepository.save(motorVehiclePhotos);
	}

}
