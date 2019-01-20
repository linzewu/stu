package com.xs.veh.network.driver;

import java.io.IOException;

import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SpeedData;
import com.xs.jt.veh.network.AbstractDeviceSpeed;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceSpeed;
import com.xs.jt.veh.network.SimpleRead.ProtocolType;
import com.xs.jt.veh.network.TakePicture;
import com.xs.jt.veh.util.CharUtil;
import com.xs.jt.veh.util.CommonUtil;

public class DeviceSpeedDriverOfJxsd extends AbstractDeviceSpeed {

	// private String up;

	private String down;

	private String end;

	private String qs;
	
	private boolean checkingFlag=false;

	public ProtocolType getProtocolType(byte[] bs) {
		
		int end=CharUtil.byteToInt(bs[bs.length-1]);
		int end3=CharUtil.byteToInt(bs[bs.length-3]);
		
		if (bs.length == 3 && CharUtil.byteToInt(bs[0]) == 0xFF) {
			return ProtocolType.NOTICE;
		}else if(bs.length>3&&end==0xEE&&end3==0xFF){
			return ProtocolType.NOTICE;
		}else if(bs.length == 6&& CharUtil.byteToInt(bs[0]) == 0xFF){
			return ProtocolType.DATA_AND_NOTICE;
		}else {
			return ProtocolType.DATA;
		}
	}

	public void setData(byte[] bs, SpeedData speedData) {

		byte[] temp = new byte[] { bs[1], bs[2] };

		String speed = CharUtil.bcd2Str(temp);

		speedData.setSpeed(CommonUtil.MathRound(Float.parseFloat(speed)/10f));

	}

	@Override
	public SpeedData startCheck(VehCheckLogin vehCheckLogin, VehFlow vehFlow) throws IOException, InterruptedException {
		// 开始新的一次检测
		createNew();
		// 显示屏显示信息
		this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
		this.display.sendMessage("速度上线检测", DeviceDisplay.XP);
		int i = 0;
		while (true) {
			if (this.signal.getSignal(s1)) {
				this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
				this.display.sendMessage("速度到位", DeviceDisplay.XP);
				i++;
			} else {
				this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
				this.display.sendMessage("速度上线检测", DeviceDisplay.XP);
				i = 0;
			}
			if (i >= 6) {
				break;
			}
			Thread.sleep(500);
		}

		// 速度台下降
		deviceSpeed.sendMessage(down);

		Thread.sleep(4000);

		this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
		this.display.sendMessage("40KM/H 申报", DeviceDisplay.XP);

		// 等待测量结束
		while (this.speedData.getSpeed() == null) {
			Thread.sleep(500);
		}
		return speedData;
		

	}

	private void createNew() {
		this.speedData = new SpeedData();
		checkingFlag=false;
	}

	@Override
	public void device2pc(byte[] endodedData) throws IOException {
		
		
		System.out.println("数据：" + CharUtil.byte2HexOfString(endodedData));
		ProtocolType type = getProtocolType(endodedData);
		
		if(type == ProtocolType.DATA_AND_NOTICE){
			
			byte[] temp1=new byte[3];
			System.arraycopy(endodedData,0, temp1, 0, temp1.length);
			String ml = CharUtil.byte2HexOfString(endodedData);
			if (ml.equals(end)) {
				deviceSpeed.sendMessage(qs);
			}
			if(checkingFlag){
				byte[] temp2=new byte[3];
				System.arraycopy(endodedData,3, temp2, 0, temp2.length);
				setData(temp2, speedData);
				checkingFlag=false;
			}
		}
		
		// 响应数据的处理方法
		if (type == ProtocolType.DATA &&checkingFlag) {
			setData(endodedData, speedData);
			checkingFlag=false;
			TakePicture.createNew(this.deviceSpeed.getVehCheckLogin(),"S1",1000);
		}
		// 响应通知的方法
		if (type == ProtocolType.NOTICE) {
			
			String ml = null;;
			
			int last=CharUtil.byteToInt(endodedData[endodedData.length-1]);
			int last3=CharUtil.byteToInt(endodedData[endodedData.length-3]);
			
			if(endodedData.length > 3 &&last==0xEE&&last3==0xFF){
				byte[] temp=new byte[3];
				System.arraycopy(endodedData,3, temp, 0, temp.length);
				ml = CharUtil.byte2HexOfString(temp);
			}else{
				ml = CharUtil.byte2HexOfString(endodedData);
			}
			
			System.out.println("命令：" + ml);
			if (ml.equals(end)) {
				System.out.println("发送取速命令");
				deviceSpeed.sendMessage(qs);
				checkingFlag=true;
			}
		}
	}

	@Override
	public void init(DeviceSpeed deviceSpeed) {
		super.init(deviceSpeed);
		s1 = deviceSpeed.getQtxxObject().getInt("kzsb-xhw");
		// up = "D";
		down = "44";
		end = "FF00EE";
		qs = "AA";

	}

}
