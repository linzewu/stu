package com.xs.veh.network.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SpeedData;
import com.xs.jt.veh.network.AbstractDeviceSpeed;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceSpeed;
import com.xs.jt.veh.network.TakePicture;
import com.xs.jt.veh.util.CharUtil;
import com.xs.jt.veh.util.CommonUtil;

public class DeviceSpeedDriverOfTLCH10 extends AbstractDeviceSpeed {

	private Log logger = LogFactory.getLog(DeviceSpeedDriverOfTLCH10.class);
	// private String up;

	private String down;

	private String ql;

	private String qs;

	private String up;


	public void setData(byte[] bs, SpeedData speedData) {

		byte[] temp = new byte[] { bs[1], bs[2] };

		String speed = CharUtil.bcd2Str(temp);

		speedData.setSpeed(CommonUtil.MathRound(Float.parseFloat(speed) / 10f));

	}

	@Override
	public SpeedData startCheck(VehCheckLogin vehCheckLogin, VehFlow vehFlow) throws IOException, InterruptedException {

		// 开始新的一次检测
		deviceSpeed.sendMessage(ql);
		this.getDevData(new byte[4]);
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
		this.getDevData(new byte[4]);
		Thread.sleep(4000);

		this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
		this.display.sendMessage("40KM/H 申报", DeviceDisplay.XP);

		// 等待测量结束
		while (true) {
			Thread.sleep(500);
			deviceSpeed.sendMessage(qs);
			byte[] data = this.getDevData(new byte[15]);
			String sdbj = new String(new byte[] { data[13] });
			String dqsd = new String(new byte[] { data[3], data[4], data[5], data[6], data[7] });
			if (data[0] == 0x41 && data[1] == 0x0f && data[1] == 0x0f && "a".equals(sdbj)) {
				TakePicture.createNew(this.deviceSpeed.getVehCheckLogin(),"S1");
				if (Float.valueOf(dqsd) == 0) {
					String strSd = new String(new byte[] { data[8], data[9], data[10], data[11], data[12] });
					this.speedData.setSpeed(Float.valueOf(strSd));
					logger.info("速度检测结束：" + strSd);
					deviceSpeed.sendMessage(up);
					logger.info("举升器上升");
					byte[] dd = this.getDevData(new byte[4]); 
					logger.info(CharUtil.byte2HexOfString(dd));
					return speedData;
				}

			}
		}
	}

	private void createNew() {
		this.speedData = new SpeedData();
	}

	@Override
	public void init(DeviceSpeed deviceSpeed) {
		super.init(deviceSpeed);
		s1 = deviceSpeed.getQtxxObject().getInt("kzsb-xhw");
		up = "41046358";
		down = "41046457";
		ql = "41046655";
		qs = "41046853";
	}
	

}
