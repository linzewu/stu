package com.xs.jt.hwvideo.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xs.jt.hwvideo.util.CharUtil;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import net.sf.json.JSONObject;

public class SimpleRead implements SerialPortEventListener {
	
	private WeightDecodeAbstract weightDecode;
	
	
	
	public WeightDecodeAbstract getWeightDecode() {
		return weightDecode;
	}

	public void setWeightDecode(WeightDecodeAbstract weightDecode) {
		this.weightDecode = weightDecode;
	}

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
	
	private String com;
	
	private Integer rate;
	
	private Integer databits;
	private Integer stopbits;
	
	private Integer parity;
	
	

	
	protected static Log logger = LogFactory.getLog(SimpleRead.class);

	protected CommPortIdentifier portId; // 串口通信管理类
	protected InputStream inputStream; // 从串口来的输入流
	protected OutputStream outputStream;// 向串口输出的流
	protected SerialPort serialPort; // 串口的引用
	protected boolean isOpen = false; // 端口是否打开

	private boolean isAddListener = true;


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


	public void setAddListener(boolean isAddListener) {
		this.isAddListener = isAddListener;
	}

	public void open() throws NoSuchPortException, PortInUseException, IOException, UnsupportedCommOperationException,
			TooManyListenersException {

		portId = CommPortIdentifier.getPortIdentifier(com);
		serialPort = (SerialPort) portId.open("myapp", 100);// 打开串口名字为myapp,延迟为2毫秒
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
		serialPort.setSerialPortParams(rate, databits, stopbits,parity);

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

//	public abstract void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException;

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
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据
			byte[] readBuffer = new byte[1024 * 128];
			int length = 0;
			int lengthTemp = 0;
			try {
				while (inputStream.available() > 0) {
					lengthTemp = inputStream.read(readBuffer);
					length += lengthTemp;
					//logger.info("数据长度" + length);
					if (length >= 1024 * 128) {
						logger.debug("读入的数据超过1024 * 128");
						break;
					}
				}
				byte[] endodedData = new byte[length];
				System.arraycopy(readBuffer, 0, endodedData, 0, length);
				System.out.println(new String(endodedData));
				weightDecode.decode(endodedData);
			} catch (Exception e) {
				logger.error("读取灯光仪数据流异常", e);
			}
			break;
		}
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getDatabits() {
		return databits;
	}

	public void setDatabits(Integer databits) {
		this.databits = databits;
	}

	public Integer getStopbits() {
		return stopbits;
	}

	public void setStopbits(Integer stopbits) {
		this.stopbits = stopbits;
	}

	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public boolean isAddListener() {
		return isAddListener;
	}

	public void setQtxxObject(JSONObject qtxxObject) {
		this.qtxxObject = qtxxObject;
	}
	
	

}