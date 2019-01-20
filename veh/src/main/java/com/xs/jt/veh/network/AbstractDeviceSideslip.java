package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SideslipData;


public abstract class AbstractDeviceSideslip {

	protected DeviceSideslip deviceSideslip;
	
	protected DeviceDisplay display;
	
	protected DeviceSignal signal;
	
	protected SideslipData sideslipData;
	
	private List<Byte> temp = new LinkedList<Byte>();

	public abstract SideslipData startCheck(VehFlow vehFlow) throws IOException, InterruptedException;

	public void device2pc(byte[] ed) throws IOException {
		for (byte b : ed) {
			temp.add(b);
		}
	}

	public void init(DeviceSideslip deviceSideslip) {
		this.deviceSideslip = deviceSideslip;
		display=deviceSideslip.getDisplay();
		signal=deviceSideslip.getSignal();
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
