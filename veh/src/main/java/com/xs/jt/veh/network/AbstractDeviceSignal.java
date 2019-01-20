package com.xs.jt.veh.network;

import java.util.List;

public abstract class  AbstractDeviceSignal {
	
	public abstract void decode(byte[] message);
	
	private List<Byte> signals;
	
	private DeviceSignal deviceSignal;
	
	protected int signalsSize;
	
	public abstract void init(DeviceSignal deviceSignal);

	public List<Byte> getSignals() {
		return signals;
	}

	public DeviceSignal getDeviceSignal() {
		return deviceSignal;
	}

	public void setSignals(List<Byte> signals) {
		this.signals = signals;
	}

	public void setDeviceSignal(DeviceSignal deviceSignal) {
		this.deviceSignal = deviceSignal;
	}
	
}
