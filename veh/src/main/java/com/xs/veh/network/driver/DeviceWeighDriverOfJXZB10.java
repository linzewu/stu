package com.xs.veh.network.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.network.AbstractDeviceWeigh;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceWeigh;
import com.xs.jt.veh.network.SimpleRead.ProtocolType;
import com.xs.jt.veh.util.CharUtil;

public class DeviceWeighDriverOfJXZB10 extends AbstractDeviceWeigh {
	
	static Log logger = LogFactory.getLog(DeviceWeighDriverOfJXZB10.class);

	// 开始称重
	private String dqsj="41046853";

	private String ql="41046259";
	
	private String sdcz="41046754";
	

	public ProtocolType getProtocolType(byte[] bs) {
		return ProtocolType.DATA;
	}
	@Override
	public BrakRollerData startCheck(VehFlow vehFlow) throws IOException, InterruptedException{

		
//		deviceWeigh.sendMessage(ql);
//		logger.info("清零返回："+CharUtil.byte2HexOfString(this.getDevData(new byte[4])));
		
		String zs = vehFlow.getJyxm().substring(1, 2);

		String hphm = vehFlow.getHphm();

		// 开始新的一次检测
		createNew();
		
		// 显示屏显示信息
		this.display.sendMessage(hphm, DeviceDisplay.SP);
		this.display.sendMessage(zs + "轴称重请到位", DeviceDisplay.XP);
		int i = 0;
		while (true) {
			if (this.signal.getSignal(s1)) {
				deviceWeigh.sendMessage(dqsj);
				byte[] data = this.getDevData(new byte[19]);
				logger.info("称重开始："+CharUtil.byte2HexOfString(data));
				
				Integer zlh=Integer.parseInt(new String(new byte[]{data[3],data[4],data[5],data[6],data[7]}));
				Integer ylh=Integer.parseInt(new String(new byte[]{data[8],data[9],data[10],data[11],data[12]}));
				
				this.display.sendMessage(zs + "轴称重已到位", DeviceDisplay.SP);
				this.display.sendMessage((zlh+ylh) + "KG",
						DeviceDisplay.XP);
				i++;
			} else {
				this.display.sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
				this.display.sendMessage(zs + "轴称重请到位", DeviceDisplay.XP);
				i = 0;
			}

			if (i >= 12) {
				break;
			}

			Thread.sleep(500);
		}
		/*deviceWeigh.sendMessage(sdcz);
		logger.info("称重结果锁定："+CharUtil.byte2HexOfString(this.getDevData(new byte[4])));*/
		
		deviceWeigh.sendMessage(dqsj);
		byte[] data = this.getDevData(new byte[19]);
		Integer zlh=Integer.parseInt(new String(new byte[]{data[3],data[4],data[5],data[6],data[7]}));
		Integer ylh=Integer.parseInt(new String(new byte[]{data[8],data[9],data[10],data[11],data[12]}));
		brakRollerData.setZlh(zlh);
		brakRollerData.setYlh(ylh);

		this.display.sendMessage(zs + "轴称重结束", DeviceDisplay.SP);
		this.display.sendMessage((brakRollerData.getZlh() + brakRollerData.getYlh()) + "KG", DeviceDisplay.XP);

		return brakRollerData;

	}

	private void createNew() {
		this.brakRollerData = new BrakRollerData();
	}

	
	@Override
	public void init(DeviceWeigh deviceWeigh) {
		super.init(deviceWeigh);
	}

}
