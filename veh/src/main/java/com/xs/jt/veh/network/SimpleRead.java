package com.xs.jt.veh.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.util.CharUtil;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import net.sf.json.JSONObject;

public abstract class SimpleRead implements SerialPortEventListener, Runnable {
	public enum ProtocolType {
		/**
		 * 数据
		 */
		DATA,
		/**
		 * 通知型命令
		 */
		NOTICE,

		DATA_AND_NOTICE
	}

	static Log logger = LogFactory.getLog(SimpleRead.class);

	protected CommPortIdentifier portId; // 串口通信管理类
	protected InputStream inputStream; // 从串口来的输入流
	protected OutputStream outputStream;// 向串口输出的流
	protected SerialPort serialPort; // 串口的引用
	protected boolean isOpen = false; // 端口是否打开

	private boolean isAddListener = true;

	private Device device;

	private JSONObject qtxxObject;

	private boolean isRun = false;

	private byte[] rtxByte;

	public byte[] getRtxByte() {
		return rtxByte;
	}

	public void setRtxByte(byte[] rtxByte) {
		this.rtxByte = rtxByte;
	}

	public JSONObject getQtxxObject() {
		return qtxxObject;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.device = device;
		String qtxx = device.getQtxx();
		qtxxObject = JSONObject.fromObject(qtxx);
		init();
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public void clearDate() {
		this.rtxByte = null;
	}

	public SimpleRead() {

	}

	public SimpleRead(Device device) throws NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, IOException {
		this.device = device;
		String qtxx = device.getQtxx();
		qtxxObject = JSONObject.fromObject(qtxx);
	}

	public void setAddListener(boolean isAddListener) {
		this.isAddListener = isAddListener;
	}

	public void open() throws NoSuchPortException, PortInUseException, IOException, UnsupportedCommOperationException,
			TooManyListenersException {

		portId = CommPortIdentifier.getPortIdentifier(device.getCom());
		logger.info(device.getCom());
		serialPort = (SerialPort) portId.open("myapp", 100);// 打开串口名字为myapp,延迟为2毫秒
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
		serialPort.setSerialPortParams(device.getRate(), device.getDatabits(), device.getStopbits(),
				device.getParity());

		if (isAddListener) {
			serialPort.addEventListener(this); // 给当前串口天加一个监听器
			serialPort.notifyOnDataAvailable(true); // 当有数据时通知
			logger.info("串口监听启动");
		}
		this.isOpen = true;
	}

	public void close() throws IOException, InterruptedException {

		if (this.isRun) {
			this.setRun(false);
			Thread.sleep(300);
		}

		if (isOpen) {
			serialPort.notifyOnDataAvailable(false);
			serialPort.removeEventListener();
			inputStream.close();
			serialPort.close();
			isOpen = false;
		}
	}

	public abstract void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException;

	public void sendMessage(String message) throws IOException {
		Integer dhcf = Integer.parseInt(qtxxObject.get("dhcf").toString());
		Integer xylx = Integer.parseInt(qtxxObject.get("xylx").toString());

		if (dhcf == 0) {
			message += "\r\n";
		}
		// logger.info(message);
		if (xylx == 0) {
			outputStream.write(message.getBytes());
		} else {
			outputStream.write(CharUtil.hexStringToByte(message));
		}
	}

	public void sendMessage(byte[] message) throws IOException {
		outputStream.write(message);
	}

	public void sendHead(String head) throws IOException {

		Integer xylx = Integer.parseInt(qtxxObject.get("xylx").toString());
		if (xylx == 0) {
			outputStream.write(head.getBytes());
		} else {
			outputStream.write(CharUtil.hexStringToByte(head));
		}
	}
}