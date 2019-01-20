package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.entity.Device;

public interface IDeviceManager {

	public Integer getMaxLine();

	public void createLinkDevice() throws SystemException;

	public List<Device> getDevicesOfType();

	public List<Device> getDevices();

	public List<Device> getDevices(Integer jcxxh);

	public List<Device> getDevicesDisplay(Integer jcxxh);

	public Device getDevice(Integer id);

	public Device saveDevice(Device device);

	public void deleteDevice(Device device);

}
