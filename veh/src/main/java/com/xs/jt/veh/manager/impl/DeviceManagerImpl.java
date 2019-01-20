package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.dao.DeviceRepository;
import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.manager.IDeviceManager;

@Service("deviceManager")
public class DeviceManagerImpl implements IDeviceManager {

	@Value("${defaultDevice}")
	private String defaultDevice;

	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public Integer getMaxLine() {
		Integer maxLine = deviceRepository.getMaxLine();

		if (maxLine == null) {
			maxLine = 0;
		}

		return maxLine;
	}

	@Override
	public void createLinkDevice() throws SystemException {
		Integer jcxxh = this.getMaxLine();
		if (jcxxh == null) {
			throw new SystemException(ResultHandler.toMyJSON(500, "获取检测线最大序号错误").toString());
		}
		jcxxh = jcxxh + 1;
		if (defaultDevice == null || defaultDevice.trim().equals("")) {
			throw new SystemException(ResultHandler.toMyJSON(500, "检测线默认设备模板未定义！").toString());
		}
		String[] devices = defaultDevice.split(",");
		for (String type : devices) {
			Device device = new Device();
			device.setType(Integer.parseInt(type));

			device.setJcxxh(jcxxh);
			this.deviceRepository.save(device);
		}

	}

	@Override
	public List<Device> getDevicesOfType() {

		return deviceRepository.getDevicesOrderByType();
	}

	@Override
	public List<Device> getDevices() {

		return deviceRepository.getDevicesOrderByJcxxh();
	}

	@Override
	public List<Device> getDevices(Integer jcxxh) {

		return deviceRepository.getDevicesByJcxxh(jcxxh);
	}

	@Override
	public List<Device> getDevicesDisplay(Integer jcxxh) {
		// TODO Auto-generated method stub
		return deviceRepository.getDevicesDisplay(jcxxh);
	}

	@Override
	public Device getDevice(Integer id) {
		// TODO Auto-generated method stub
		return deviceRepository.findById(id).get();
	}

	@Override
	public Device saveDevice(Device device) {
		// TODO Auto-generated method stub
		return deviceRepository.save(device);
	}

	@Override
	public void deleteDevice(Device device) {
		deviceRepository.delete(device);

	}

}
