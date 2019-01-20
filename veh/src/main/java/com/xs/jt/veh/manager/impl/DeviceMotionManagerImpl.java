package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.DeviceMotionRepository;
import com.xs.jt.veh.entity.DeviceMotion;
import com.xs.jt.veh.manager.IDeviceMotionManager;

@Service("deviceMotionManager")
public class DeviceMotionManagerImpl implements IDeviceMotionManager {

	@Autowired
	private DeviceMotionRepository deviceMotionRepository;

	@Override
	public List<DeviceMotion> getMotions() {
		return deviceMotionRepository.findAll();
	}

	@Override
	public DeviceMotion saveDeviceMotion(DeviceMotion deviceMotion) {
		return deviceMotionRepository.save(deviceMotion);
	}

	@Override
	public void deleteDeviceMotion(DeviceMotion deviceMotion) {
		deviceMotionRepository.delete(deviceMotion);
	}

}
