package com.xs.veh.network.driver;

import java.io.IOException;

import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.network.AbstractDeviceWeigh;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceWeigh;
import com.xs.jt.veh.network.SimpleRead.ProtocolType;
import com.xs.jt.veh.util.CharUtil;

public class DeviceWeighDriverOfJxcz extends AbstractDeviceWeigh {

	// 开始称重
	private String kscz;

	private String jscz;
	
	private boolean isChecking=false;

	public ProtocolType getProtocolType(byte[] bs) {
		return ProtocolType.DATA;
	}

	public void setData(byte[] bs, BrakRollerData brakRollerData) {

		if (brakRollerData != null && bs.length == 8&&isChecking) {
			String t1 = CharUtil.bcd2Str(new byte[] { bs[1], bs[2] });
			String t2 = CharUtil.bcd2Str(new byte[] { bs[5], bs[6] });
			brakRollerData.setZlh(Integer.parseInt(t1));
			brakRollerData.setYlh(Integer.parseInt(t2));
		}
	}

	@Override
	public BrakRollerData startCheck(VehFlow vehFlow) throws IOException, InterruptedException{

		String zs = vehFlow.getJyxm().substring(1, 2);

		String hphm = vehFlow.getHphm();

		// 开始新的一次检测
		createNew();
		
		isChecking = true;
		// 显示屏显示信息
		this.display.sendMessage(hphm, DeviceDisplay.SP);
		this.display.sendMessage(zs + "轴称重请到位", DeviceDisplay.XP);
		// 开始称重
		deviceWeigh.sendMessage(kscz);
		int i = 0;
		while (true) {

			if (this.signal.getSignal(s1)) {
				if (brakRollerData.getZlh() != null && brakRollerData.getYlh() != null) {
					this.display.sendMessage(zs + "轴称重已到位", DeviceDisplay.SP);
					this.display.sendMessage((brakRollerData.getZlh() + brakRollerData.getYlh()) + "KG",
							DeviceDisplay.XP);
				}
				i++;
			} else {
				this.display.sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
				this.display.sendMessage(zs + "轴称重请到位", DeviceDisplay.XP);
				i = 0;
			}

			if (i >= 12) {
				isChecking=false;
				break;
			}

			Thread.sleep(500);
		}
		deviceWeigh.sendHead(jscz);

		this.display.sendMessage(zs + "轴称重结束", DeviceDisplay.SP);
		this.display.sendMessage((brakRollerData.getZlh() + brakRollerData.getYlh()) + "KG", DeviceDisplay.XP);

		return brakRollerData;

	}

	private void createNew() {
		this.brakRollerData = new BrakRollerData();
	}

	@Override
	public void device2pc(byte[] endodedData) throws IOException {
		ProtocolType type = getProtocolType(endodedData);
		// 响应数据的处理方法
		if (type == ProtocolType.DATA) {
			setData(endodedData, brakRollerData);
		}
	}

	@Override
	public void init(DeviceWeigh deviceWeigh) {
		super.init(deviceWeigh);
		kscz = "FF01EE";
		jscz ="FF02EE";
	}

}
