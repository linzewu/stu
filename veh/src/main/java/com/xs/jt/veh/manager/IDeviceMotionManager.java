package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.veh.entity.DeviceMotion;

public interface IDeviceMotionManager {

	public List<DeviceMotion> getMotions();

	public DeviceMotion saveDeviceMotion(DeviceMotion deviceMotion);

	public void deleteDeviceMotion(DeviceMotion deviceMotion);

}
