package com.xs.jt.veh.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.LightData;


/**
 * 灯光仪解码器接口
 * 
 * @author linze
 *
 */
public abstract class AbstractDeviceLight {

	public enum DX {
		/**
		 * 二灯制
		 */
		EDZ,
		/**
		 * 四等制
		 */
		SDZ
	}

	public enum GX {

		/**
		 * 单侧远光
		 */
		DCYG,

		/**
		 * 单侧近光
		 */
		DCJG,

		/**
		 * 远近光均测
		 */
		YJGJC
	}

	public enum YGTZ {
		/**
		 * 远光单独调整
		 */
		YG_YES,

		/**
		 * 远光不调整
		 */
		YG_NO

	}

	public enum JGTZ {
		/**
		 * 近光调整
		 */
		JG_YES,

		/**
		 * 近光不调整
		 */
		JG_NO
	}

	public enum LightNoticeType {
		/**
		 * 主远光灯测量结束
		 */
		highBeamOfMainEnd,
		/**
		 * 副远光灯测量结束
		 */
		highBeamOfSideEnd,

		/**
		 * 近关灯测量结束
		 */
		lowBeamOfMainEnd,
		
		/**
		 * 灯光归位
		 */
		deviceBreak,
		
		/**
		 * 灯光错误
		 */
		beamError,
		
		/**
		 * 无光
		 */
		noBeam
	}
	
	


	protected DeviceLight deviceLight;

	protected List<LightData> lightDatas;
	
	protected DeviceSignal deviceSignal1;

	protected DeviceSignal deviceSignal2;

	protected Integer s1;

	protected Integer s2;
	
	protected Integer kwfx;
	

	public abstract void sysSetting() throws IOException, InterruptedException;

	public abstract List<LightData> startCheck(VehCheckLogin vehCheckLogin, List<VehFlow> vheFlows) throws IOException, InterruptedException, SystemException;

	public abstract void device2pc(byte[] data) throws IOException, InterruptedException;

	public abstract void setDeviceLight(DeviceLight deviceLight);

	public void createNewList() {
		
		if(lightDatas!=null){
			lightDatas.clear();
		}
		
		lightDatas=new ArrayList<LightData>();
		
	}
}
