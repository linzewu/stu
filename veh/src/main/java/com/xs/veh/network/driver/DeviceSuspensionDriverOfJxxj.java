package com.xs.veh.network.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.SuspensionData;
import com.xs.jt.veh.network.AbstractDeviceSuspension;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceSuspension;
import com.xs.jt.veh.network.SimpleRead.ProtocolType;
import com.xs.jt.veh.util.CharUtil;

/**
 * 汽车悬架装置
 * 
 * @author linzewu
 *
 */
public class DeviceSuspensionDriverOfJxxj extends AbstractDeviceSuspension {
	private static Log logger = LogFactory.getLog(DeviceSuspensionDriverOfJxxj.class);

	// 准备检测
	private String zbjc;
	// 正式检测
	private String zsjc;
	// 取该次检测结果数据
	private String qjg;
	// 取消本次检测
	private String qxjc;
	// 对仪表进行清零
	private String ybql;
	// 称重检测完毕,开始启动电机 计算吸收率
	private String czwb;
	// 车辆到位
	private String cldw;
	// 车辆离开检测台
	private String lkjq;
	// 本次检测结束，上位机可以取数据了
	private String jcjs;

	public ProtocolType getProtocolType(byte[] bs) {
		return ProtocolType.DATA;
	}

	private void createNew() {
		this.suspensionData = new SuspensionData();
		this.getTemp().clear();
	}

	@Override
	public SuspensionData startCheck(VehCheckLogin vehCheckLogin, VehFlow vehFlow)
			throws IOException, InterruptedException {
		// 仪表清零
		deviceSuspension.sendMessage(ybql);
		// 开始新的一次检测
		createNew();

		label: while (true) {
			// 显示屏显示信息
			this.display.sendMessage(vehCheckLogin.getHphm(), DeviceDisplay.SP);
			this.display.sendMessage("请上悬架仪检测", DeviceDisplay.XP);
			logger.info("请上悬架仪上线检测：" + zbjc);
			// 准备检测
			deviceSuspension.sendMessage(zbjc);
			String zbInfo = CharUtil.byte2HexOfString(getDevData(new byte[4]));
			logger.info("上线检测反馈：" + zbInfo);
			if (cldw.equals(zbInfo)) {
				// 开始检测
				this.display.sendMessage("悬架仪开始检测", DeviceDisplay.XP);
				deviceSuspension.sendMessage(zsjc);
				logger.info("悬架仪正式开始检测：" + zsjc);
				while (true) {
					zbInfo = CharUtil.byte2HexOfString(getDevData(new byte[4]));
					if (jcjs.equals(zbInfo)) { // 本次检测结束，上位机可以取数据了
						break;
					} else if (zbInfo.startsWith("F1")) {
						// 实时获取左轮重

						logger.info("左轮实时数据：" + zbInfo);
					} else if (zbInfo.startsWith("F2")) {
						// 实时获取右轮重
						logger.info("右轮实时数据：" + zbInfo);
					} else if ((lkjq.equals(zbInfo))) {
						continue label;
					}
				}

				// 检测结束
				this.display.sendMessage("检测结束，获取数据中...", DeviceDisplay.XP);
				deviceSuspension.sendMessage(qjg);
				logger.info("悬架仪检测结束，获取数据中...：" + qjg);
				// 获取数据
				zbInfo = CharUtil.byte2HexOfString(getDevData(new byte[12]));
				logger.info("检测数据：" + zbInfo);

				break;
			} else if (lkjq.equals(zbInfo)) {// 车未上台或已经离开
				continue;
			}
		}

		return this.suspensionData;

	}

	@Override
	public void init(DeviceSuspension deviceSuspension) {
		super.init(deviceSuspension);
		zbjc = "FF 01 EE";
		zsjc = "FF 02 EE";
		qjg = "FF 03 EE";
		qxjc = "FF 04 EE";
		ybql = "FF 05 EE";
		czwb = "FF 00 07 EE";
		cldw = "FF 00 08 EE";
		lkjq = "FF 00 09 EE";
		jcjs = "FF 00 0A EE";
	}

}
