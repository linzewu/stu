package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.TooManyListenersException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.entity.Device;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;
import net.sf.json.JSONObject;

/**
 * 信号设备 光电开关
 * @author linze
 *
 */

@Service("deviceSignal")
@Scope("prototype")
public class DeviceSignal extends SimpleRead {

	public AbstractDeviceSignal dsd;
	
	protected String rtx;

	static Log logger = LogFactory.getLog(DeviceSignal.class);
	
	public void setRtx(String rtx) {
		this.rtx = rtx;
	}

	public DeviceSignal() {
	}

	public DeviceSignal(Device device) throws NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		super(device);
		init();
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

			byte[] readBuffer = new byte[1024];
			int length = 0;
			int lengthTemp = 0;
			try {
				while (inputStream.available() > 0) {
					lengthTemp = inputStream.read(readBuffer);
					length += lengthTemp;
					if (length >= 1024 * 128) {
						logger.error("读入的数据超过1024");
						break;
					}
				}
				byte[] endodedData = new byte[length];
				System.arraycopy(readBuffer, 0, endodedData, 0, length);
				dsd.decode(endodedData);
				//this.setRtx();
			} catch (IOException e) {
				logger.error("光电开关读取数据流异常", e);
			}
			break;
		}
	}

	public String getRtx() {
		return rtx;
	}

	@Override
	public void run() {
		String qtxx = this.getDevice().getQtxx();
		JSONObject jo = JSONObject.fromObject(qtxx);
		Integer jtpl = Integer.parseInt(jo.get("jtpl").toString());
		String dsj = (String) jo.get("dsj");
		int i=1;
		while (this.isRun()) {
			try {
				this.sendMessage(dsj);
				/*if(i%10==0){
					this.outputStream.flush();
					i=0;
				}*/
			} catch (Exception e) {
				logger.error("光电开异常", e);
			}
			try {
				Thread.sleep(jtpl);
			} catch (InterruptedException e) {
				logger.error("光电开关线程中断异常", e);
			}
			i++;
		}
		logger.info("光电开关已退出监听");
	}

	@Override
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 初始化光电解码器
		dsd = (AbstractDeviceSignal) Class.forName(this.getDevice().getDeviceDecode()).newInstance();
		dsd.init(this);
	}
	
	/**
	 *  
	 * @param index 开完位置
	 * @return  0是true 其他false
	 */
	public boolean getSignal(Integer index){ 
		
		String temp =this.getRtx();
		
		Integer count =temp.length();
		
		if(count>0&&count>index){
			String strSignal = temp.substring(count-index-1,count-index);
			
			Integer signal = Integer.parseInt(strSignal);
			
			return signal==0?true:false;
		}else{
			return false;
		}
		
		
		
		
	}
}
