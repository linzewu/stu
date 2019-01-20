package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.TooManyListenersException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.entity.Device;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;

@Service("deviceDisplay")
@Scope("prototype")
public class DeviceDisplay extends SimpleRead {

	public final static Integer SP = 0;

	public final static Integer XP = 1;

	public DeviceDisplay() {
	}

	public DeviceDisplay(Device device) throws NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, IOException {
		super(device);
		init();
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
	}

	@Override
	public void run() {
	}

	public String getRtx() {
		return null;
	}

	public void sendMessage(String message, int ph) throws IOException {

		Integer dhcf = Integer.parseInt(this.getQtxxObject().get("dhcf").toString());

		String xy = null;
		if (ph == 1) {
			xy = (String) this.getQtxxObject().get("xpxy");
		} else {
			xy = (String) this.getQtxxObject().get("spxy");
		}

		this.sendHead(xy);

		if (dhcf == 0) {
			message += "\r\n";
		}
		this.outputStream.write(message.getBytes("GBK"));
	}
	
	
	public void setDefault() throws IOException{
		String xpmr = (String) this.getQtxxObject().get("xpmr");
		String spmr = (String) this.getQtxxObject().get("spmr");
		sendMessage(xpmr,XP);
		sendMessage(spmr,SP);
	}
	

	@Override
	public void init() {
		// 没有返回值不启用监听器
		this.setAddListener(false);

	}
}
