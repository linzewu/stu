package com.xs.jt.cms.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.cms.entity.VehicleLock;

public interface IVehicleLockManager {
	
	public boolean findMotorVehicleBusinessLockByClsbdh(String clsbdh);
	
	public Map<String,Object> getVehicleLocks(Integer page, Integer rows,VehicleLock vehicleLock);
	
	public VehicleLock save(VehicleLock vehCheckInfo);
	
	public List<VehicleLock> findLockVehicle(String clsbdh);

}
