package com.xs.jt.cms.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.VehicleLockRepository;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.manager.IVehicleLockManager;
@Service("vehicleLockManager")
public class VehicleLockManagerImpl implements IVehicleLockManager {
	@Autowired
	private VehicleLockRepository vehicleLockRepository;

	public boolean findMotorVehicleBusinessLockByClsbdh(String clsbdh) {
		boolean flag = false;
		List<VehicleLock> list = vehicleLockRepository.findMotorVehicleBusinessLockByClsbdh(clsbdh);
		if (list != null && list.size() > 0){
			flag = true;
		}
		return flag;
	}

}
