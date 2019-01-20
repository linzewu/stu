package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.util.CharUtil;


public abstract class AbstractDeviceBrakRoller {

	protected DeviceBrakRoller deviceBrakRoller;

	protected BrakRollerData brakRollerData;

	protected VehFlow nextVehFlow;

	protected boolean isError = false;

	protected boolean isbs = false;

	protected boolean checkingFlage;

	protected boolean isPlusLoad = false;

	public abstract BrakRollerData startCheck(VehFlow vehFlow)
			throws SystemException, IOException, InterruptedException;

	public void device2pc(byte[] ed) throws IOException {
		for (byte b : ed) {
			temp.add(b);
		}
	}

	public abstract void init(DeviceBrakRoller deviceBrakRoller);

	public DeviceBrakRoller getDeviceBrakRoller() {
		return deviceBrakRoller;
	}

	public void setDeviceBrakRoller(DeviceBrakRoller deviceBrakRoller) {
		this.deviceBrakRoller = deviceBrakRoller;
	}

	public BrakRollerData getBrakRollerData() {
		return brakRollerData;
	}

	public void setBrakRollerData(BrakRollerData brakRollerData) {
		this.brakRollerData = brakRollerData;
	}

	public VehFlow getNextVehFlow() {
		return nextVehFlow;
	}

	public void setNextVehFlow(VehFlow nextVehFlow) {
		this.nextVehFlow = nextVehFlow;
	}

	public String getZW(Integer zw) {

		String str = "";

		switch (zw) {
		case 1:
			str = "一轴";
			break;
		case 2:
			str = "二轴";
			break;
		case 3:
			str = "三轴";
			break;
		case 4:
			str = "四轴";
			break;
		case 5:
			str = "五轴";
			break;
		case 6:
			str = "六轴";
			break;
		case 0:
			str = "驻车";
			break;
		default:
			str = zw.toString();
			break;
		}

		return str;
	}

	public void resetCheckStatus() {
		isPlusLoad = false;
		checkingFlage = true;
		isError = false;
		isbs = false;
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

	public byte[] getDevData(byte[] contex, byte beginByte) throws InterruptedException {
		while (temp.isEmpty()) {
			Thread.sleep(50);
		}
		while (temp.remove(0)!=beginByte) {
			
		}
		
		contex[0]=beginByte;
		for (int i = 1; i < contex.length; i++) {
			while (temp.isEmpty()) {
				Thread.sleep(50);
			}
			contex[i] = temp.remove(0);
		}

		return contex;
	}
	
	/*public static void main(String[] age) {
		
		List<Byte> temp =new LinkedList<Byte>();
		temp.add((byte) 0x41);
		temp.add((byte) 0x45);
		temp.add((byte) 0x46);
		temp.add((byte) 0x47);
		
		System.out.println(CharUtil.hexStringToByte("41"));
		 
		byte b ;
		while ((b=temp.remove(0))!=0x47) {
			System.out.println(CharUtil.byte2HexOfString(CharUtil.hexStringToByte("41")));
		}
		
		
	}*/
	

	private List<Byte> temp = new LinkedList<Byte>();

	public List<Byte> getTemp() {
		return temp;
	}

}
