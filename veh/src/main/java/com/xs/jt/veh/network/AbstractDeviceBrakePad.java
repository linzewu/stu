package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;


public abstract class AbstractDeviceBrakePad {
	
	protected DeviceBrakePad deviceBrakePad;
	
	protected List<BrakRollerData> brakRollerDatas;
	
	protected boolean checkFlag =false ;
	
	protected boolean qzflag=false;
	protected boolean hzflag=false;
	protected boolean zcflag=false;
	

	public abstract List<BrakRollerData> startCheck(List<VehFlow> vehFlows) throws InterruptedException, IOException ;

	public abstract void device2pc(byte[] data) throws IOException,InterruptedException;
	
	public abstract void init(DeviceBrakePad deviceBrakePad);
	
	
	
	public DeviceBrakePad getDeviceBrakePad() {
		return deviceBrakePad;
	}


	public void setDeviceBrakePad(DeviceBrakePad deviceBrakePad) {
		this.deviceBrakePad = deviceBrakePad;
	}
	


	public List<BrakRollerData> getBrakRollerDatas() {
		return brakRollerDatas;
	}

	public void setBrakRollerDatas(List<BrakRollerData> brakRollerDatas) {
		this.brakRollerDatas = brakRollerDatas;
	}

	public void resetCheckStatus() {
		if(brakRollerDatas==null){
			brakRollerDatas =new ArrayList<BrakRollerData>();
		}else{
			brakRollerDatas.clear();
		}
		checkFlag=false;
		qzflag=false;
		hzflag=false;
		zcflag=false;
	}
	
	

}
