package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SpeedData;


public abstract class AbstractDeviceSpeed {
	
	protected DeviceSpeed deviceSpeed;

	protected DeviceDisplay display;

	protected DeviceSignal signal;
	
	protected SpeedData speedData;
	
	protected Integer s1;
	
	private List<Byte> temp = new LinkedList<Byte>();
	
	public abstract SpeedData startCheck(VehCheckLogin vehCheckLogin,VehFlow vehFlow) throws IOException, InterruptedException;

	public void device2pc(byte[] ed) throws IOException {
		for (byte b : ed) {
			temp.add(b);
		}
	}
	
	public void init(DeviceSpeed deviceSpeed) {
		this.deviceSpeed = deviceSpeed;
		display=deviceSpeed.getDisplay();
		signal=deviceSpeed.getSignal();
		s1=deviceSpeed.getS1();
	}
	
	public List<Byte> getTemp() {
		return temp;
	}
	
	public byte[] getDevData(byte[] contex) throws InterruptedException {

		for (int i = 0; i < contex.length; i++) {
			while (temp.isEmpty()) {
				Thread.sleep(50);
			}
			contex[i] = temp.remove(0);
		}

		return contex;
	}


}
