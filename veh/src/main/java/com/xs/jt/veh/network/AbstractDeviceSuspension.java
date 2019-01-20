package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SuspensionData;


public abstract class AbstractDeviceSuspension {
	protected DeviceSuspension deviceSuspension;
	protected DeviceDisplay display;
	protected SuspensionData suspensionData;
	
	private List<Byte> temp = new LinkedList<Byte>();
	public abstract SuspensionData startCheck(VehCheckLogin vehCheckLogin,VehFlow vehFlow) throws IOException, InterruptedException;

	public List<Byte> getTemp() {
		return temp;
	}
	
	public void device2pc(byte[] ed) throws IOException {
		for (byte b : ed) {
			temp.add(b);
		}
	}
	
	public void init(DeviceSuspension deviceSuspension) {
		this.deviceSuspension = deviceSuspension;
		display=deviceSuspension.getDisplay();
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
