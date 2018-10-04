package com.xs.jt.cms.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.MotorVehicleBusinessLockRepository;
import com.xs.jt.cms.entity.MotorVehicleBusinessLock;
import com.xs.jt.cms.manager.IMotorVehicleBusinessLockManager;
@Service("motorVehicleBusinessLockManager")
public class MotorVehicleBusinessLockManagerImpl implements IMotorVehicleBusinessLockManager {
	@Autowired
	private MotorVehicleBusinessLockRepository motorVehicleBusinessLockRepository;

	public boolean findMotorVehicleBusinessLockByClsbdh(String clsbdh) {
		boolean flag = false;
		List<MotorVehicleBusinessLock> list = motorVehicleBusinessLockRepository.findMotorVehicleBusinessLockByClsbdh(clsbdh);
		if (list != null && list.size() > 0){
			flag = true;
		}
		return flag;
	}

}
