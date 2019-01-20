package com.xs.veh.network.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.LightData;
import com.xs.jt.veh.network.AbstractDeviceLight;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceLight;
import com.xs.jt.veh.network.TakePicture;
import com.xs.jt.veh.util.CharUtil;

public class DeviceLightDriverOfMQD6A extends AbstractDeviceLight {

	static Log logger = LogFactory.getLog(DeviceLightDriverOfMQD6A.class);

	// -----------测量--------------

	// 原地测量
	private String tYdcl = "020132545126";

	// 全程测量
	private String tQccl = "020132545225";

	// 仅一灯测量
	private String tJydcl = "020132545b1C";

	// 测左灯
	private String tCzd = "020132540770";

	// 测右灯
	private String tCyd = "020132544037";

	// 退出调整
	private String tTctz = "020132545324";

	// 退出测量
	private String tTccl = "020132545423";

	// 只测右灯
	private String tZcyd = "02013254591E";

	// 只测左灯
	private String tZczd = "020132545A1D";

	// 取仪器状态
	private String tQyqzt = "020132546F08";

	// -----------取数 ------------

	// 取左辅灯数据
	private String gQzfdsj = "020132474A3A";

	// 取右辅灯数据
	private String gQyfdsj = "020132474B39";

	// 取左主灯数据
	private String gQzzdsj = "020132474C38";

	// 取右主灯数据
	private String gQyzdsj = "020132474D37";

	// 取当前数据
	private String gQdqsj = "020132474E36";

	// 取全部测量数据
	private String gQqbclsj = "020132474F35";
	// -------------控制------------
	// 仪器归位
	private String mYqgw = "0201324D4737";

	private String mtz = "0201324D3E40";

	// ----------设置 ------------
	// 设置左靠位
	private String sSzzkw = "";

	// 设置右靠位
	private String sSzykw = "";
	// 单测远光
	private String sDcyg = "020132533345";
	// 单测近光
	private String sDcjg = "020132533444";
	// 远近光均测
	private String sYjgjc = "020132533543";
	// 先测远灯
	private String sXcyd = "020132537107";
	// 先测近灯
	private String sXcjd = "020132537206";
	// 四灯制
	private String sSdz = "020132533147";
	// 二灯制
	private String sEdz = "020132533246";
	// 需要调整远光
	private String sXytzyg = "020132536018";
	// 不需要调整远光
	private String sBxytzyg = "020132536117";
	// 需要调整近光
	private String sXytzjg = "020132536216";
	// 不需要调整近光
	private String sBxytzjg = "020132536315";

	// -----------仪器 to PC------------------------

	// 主远光测量完成
	private String rZygclwc = "0232544F29";

	// 主远光测量完成
	private String rFygclwc = "0232544E2A";

	// 主远光测量完成
	private String rJgclwc = "0232544D2B";

	// 归位完成
	private String rGwwc = "0232544038";

	// 测量无灯
	private String rClwd = "0232451374";

	// 测量出错
	private String rClcc = "0232451473";

	private List<String> leftMessageList = new ArrayList<String>();
	private List<String> rightMessageList = new ArrayList<String>();

	private List<Byte> dataList = new ArrayList<Byte>();

	// 是否检测左灯光
	private boolean isCheckLeft;

	// 是否检测右灯
	private boolean isCheckRight;

	// 是已经在检测
	private boolean isChecking;

	// 是否错误
	private boolean isError = false;

	private String errorMessage;

	// 是否開始取數據
	private boolean isGetData;

	// 当前位置 L 左 R 右
	private Character currentPosition;

	// 检测返回的数据
	private byte[] checkData;

	// 检测状态
	private Integer checkState;

	// 正常
	public static Integer ZT_ZC = 0;

	// 无灯
	public static Integer ZT_WD = 1;

	// 出错
	public static Integer ZT_CC = 2;

	public boolean isIllegal = false;

	@Override
	public void sysSetting() {
	}

	@Override
	public List<LightData> startCheck(VehCheckLogin vehCheckLogin, List<VehFlow> vheFlows)
			throws IOException, InterruptedException, SystemException {

		// 设置检测参数
		setting(vehCheckLogin, vheFlows);
		// 等待到位
		dw(vehCheckLogin);

		// 检测是否违规操作
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 保护
				int i = 1000;
				try {
					while (!isGetData && i <= 1000) {
						if (deviceSignal2.getSignal(s2)) {
							// 仪器归位
							isIllegal = true;
							break;
						}
						Thread.sleep(300);
						i++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		if (isCheckLeft) {
			currentPosition = 'L';
			checking();
			TakePicture.createNew(this.deviceLight.getVehCheckLogin(), "H1");
		}
		if (isCheckRight) {
			currentPosition = 'R';
			checking();
			TakePicture.createNew(this.deviceLight.getVehCheckLogin(), "H4");
		}
		// 仪器归位
		this.deviceLight.sendMessage(mYqgw);
		Thread.sleep(300);
		// 取数据
		this.deviceLight.sendMessage(gQqbclsj);

		isGetData = true;

		return getData(vheFlows, vehCheckLogin);

	}

	private List<LightData> getData(List<VehFlow> vheFlows, VehCheckLogin vehCheckLogin)
			throws InterruptedException, IOException, SystemException {

		if (checkState != ZT_ZC) {
			return null;
		}

		while (dataList.size() < 112) {
			Thread.sleep(200);
		}

		for (int i = 0; i < 112; i++) {
			checkData[i] = dataList.get(i);
		}

		String jyxm = "";

		for (VehFlow vehFlow : vheFlows) {
			jyxm += vehFlow.getJyxm() + ",";
		}

		List<LightData> lightDatas = new ArrayList<LightData>();

		// 获取所有检测数据
		byte[] leftMainHighBeamData = new byte[18];
		System.arraycopy(checkData, 2, leftMainHighBeamData, 0, leftMainHighBeamData.length);
		byte[] leftSideHighBeamData = new byte[18];
		System.arraycopy(checkData, 20, leftSideHighBeamData, 0, leftSideHighBeamData.length);
		byte[] leftMainLowBeamData = new byte[18];
		System.arraycopy(checkData, 38, leftMainLowBeamData, 0, leftMainLowBeamData.length);

		byte[] rightMainHighBeamData = new byte[18];
		System.arraycopy(checkData, 57, rightMainHighBeamData, 0, rightMainHighBeamData.length);
		byte[] rightSideHighBeamData = new byte[18];
		System.arraycopy(checkData, 75, rightSideHighBeamData, 0, rightSideHighBeamData.length);
		byte[] rightMainLowBeamData = new byte[18];
		System.arraycopy(checkData, 93, rightMainLowBeamData, 0, rightMainLowBeamData.length);

		if (jyxm.indexOf("H4") >= 0 && "无法找到灯光".equals(errorMessage) && (vehCheckLogin.getQzdz().equals("01")
				|| vehCheckLogin.getQzdz().equals("03") || vehCheckLogin.getQzdz().equals("04"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_JGD);
			setCurrent(rightMainLowBeamData, data);
			lightDatas.add(data);
		} else if (!isEmptyData(rightMainLowBeamData) && jyxm.indexOf("H4") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_JGD);
			setCurrent(rightMainLowBeamData, data);
			lightDatas.add(data);
		}

		if (jyxm.indexOf("H3") >= 0 && "无法找到灯光".equals(errorMessage)
				&& (vehCheckLogin.getQzdz().equals("01") || vehCheckLogin.getQzdz().equals("02"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_FD);
			data.setGx(LightData.GX_YGD);
			setCurrent(rightSideHighBeamData, data);
			lightDatas.add(data);
		} else if (!isEmptyData(rightSideHighBeamData) && jyxm.indexOf("H3") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_FD);
			data.setGx(LightData.GX_YGD);
			setCurrent(rightSideHighBeamData, data);
			lightDatas.add(data);
		}

		if (jyxm.indexOf("H4") >= 0 && "无法找到灯光".equals(errorMessage) && (vehCheckLogin.getQzdz().equals("01")
				|| vehCheckLogin.getQzdz().equals("02") || vehCheckLogin.getQzdz().equals("03"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_YGD);
			setCurrent(rightMainHighBeamData, data);
			this.lightDatas.add(data);
			lightDatas.add(data);
		} else if (!isEmptyData(rightMainHighBeamData) && jyxm.indexOf("H4") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Y);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_YGD);
			setCurrent(rightMainHighBeamData, data);
			this.lightDatas.add(data);
			lightDatas.add(data);
		}

		if (jyxm.indexOf("H1") >= 0 && "无法找到灯光".equals(errorMessage)
				&& (vehCheckLogin.getQzdz().equals("01") || vehCheckLogin.getQzdz().equals("02")
						|| vehCheckLogin.getQzdz().equals("03") || vehCheckLogin.getQzdz().equals("05"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_YGD);
			setCurrent(leftMainHighBeamData, data);
			lightDatas.add(data);
		} else if (!isEmptyData(leftMainHighBeamData) && jyxm.indexOf("H1") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_YGD);
			setCurrent(leftMainHighBeamData, data);
			lightDatas.add(data);
		}

		if (jyxm.indexOf("H2") >= 0 && "无法找到灯光".equals(errorMessage)
				&& (vehCheckLogin.getQzdz().equals("01") || vehCheckLogin.getQzdz().equals("02"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_FD);
			data.setGx(LightData.GX_YGD);
			setCurrent(leftSideHighBeamData, data);
			lightDatas.add(data);
		} else if (!isEmptyData(leftSideHighBeamData) && jyxm.indexOf("H2") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_FD);
			data.setGx(LightData.GX_YGD);
			setCurrent(leftSideHighBeamData, data);
			lightDatas.add(data);
		}

		if (jyxm.indexOf("H1") >= 0 && "无法找到灯光".equals(errorMessage) && (vehCheckLogin.getQzdz().equals("01")
				|| vehCheckLogin.getQzdz().equals("03") || vehCheckLogin.getQzdz().equals("04"))) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_JGD);
			setCurrent(leftMainLowBeamData, data);
			lightDatas.add(data);
		} else if (!isEmptyData(leftMainLowBeamData) && jyxm.indexOf("H1") >= 0) {
			LightData data = new LightData();
			data.setWz(LightData.WZ_Z);
			data.setDx(LightData.DX_ZD);
			data.setGx(LightData.GX_JGD);
			setCurrent(leftMainLowBeamData, data);
			lightDatas.add(data);
		}

		return lightDatas;

	}

	private void setCurrent(byte[] bs, LightData lightData) throws IOException, InterruptedException, SystemException {
		byte[] data1 = new byte[5];
		System.arraycopy(bs, 0, data1, 0, data1.length);

		if ("无法找到灯光".equals(errorMessage)) {
			lightData.setSppc("0");
		} else {
			lightData.setSppc(new String(data1));
		}

		byte[] data2 = new byte[5];
		System.arraycopy(bs, 5, data2, 0, data2.length);
		if ("无法找到灯光".equals(errorMessage)) {
			lightData.setCzpc("0");
		} else {
			lightData.setCzpc(new String(data2));
		}

		byte[] data3 = new byte[4];
		System.arraycopy(bs, 10, data3, 0, data3.length);
		String gq = new String(data3);

		if ("无法找到灯光".equals(errorMessage)) {
			lightData.setGq(0);
		} else if (CharUtil.isNumeric(gq)) {
			lightData.setGq(Integer.parseInt(gq) * 100);
		} else {
			deviceLight.sendMessage(mtz);
			Thread.sleep(300);
			deviceLight.sendMessage(mYqgw);
			deviceLight.getDisplay().sendMessage("仪器数据异常", DeviceDisplay.SP);
			deviceLight.getDisplay().sendMessage("重检测，请等待", DeviceDisplay.XP);
			Thread.sleep(1000 * 20);
			throw new SystemException("仪器数据异常！");
		}

		byte[] data4 = new byte[4];
		System.arraycopy(bs, 14, data4, 0, data4.length);
		String dg = new String(data4);
		if ("无法找到灯光".equals(errorMessage)) {
			lightData.setDg(1);
		} else if (CharUtil.isNumeric(dg)) {
			lightData.setDg(Integer.parseInt(dg.trim()));
		}

	}

	private boolean isEmptyData(byte[] highData) {

		if (new String(highData).equals("000000000000000000")) {
			return true;
		}
		return false;
	}

	// 开始检测
	private void checking() throws InterruptedException, IOException, SystemException {
		this.isChecking = true;
		this.isError = false;

		// 按检测顺序 获取提示消息
		String[] messageArray = null;
		if (currentPosition == 'L') {
			messageArray = leftMessageList.remove(0).split(",");
		} else {
			messageArray = rightMessageList.remove(0).split(",");
		}

		deviceLight.getDisplay().sendMessage(messageArray[0], DeviceDisplay.SP);
		deviceLight.getDisplay().sendMessage(messageArray[1], DeviceDisplay.XP);

		deviceLight.sendMessage(currentPosition == 'L' ? tCzd : tCyd);
		while (true) {

			if (this.isError) {
				deviceLight.getDisplay().sendMessage(errorMessage, DeviceDisplay.XP);
				Thread.sleep(1000 * 2);
				if (!"无法找到灯光".equals(errorMessage)) {
					// 仪器归位
					this.deviceLight.sendMessage(mYqgw);
					Thread.sleep(1000 * 20);
					throw new SystemException(errorMessage);
				}
			}

			if (!this.isChecking) {
				break;
			}
			// 违规操作
			if (isIllegal) {
				deviceLight.sendMessage(mtz);
				Thread.sleep(300);
				deviceLight.sendMessage(mYqgw);
				deviceLight.getDisplay().sendMessage("违规操作", DeviceDisplay.SP);
				deviceLight.getDisplay().sendMessage("停止检测", DeviceDisplay.XP);
				Thread.sleep(1000 * 20);
				throw new SystemException("违规操作！");
			}
			Thread.sleep(100);
		}
	}

	private void dw(VehCheckLogin vehCheckLogin) throws IOException, InterruptedException {
		String hphm = vehCheckLogin.getHphm();
		int i = 0;
		this.deviceLight.getDisplay().sendMessage(hphm, DeviceDisplay.SP);
		this.deviceLight.getDisplay().sendMessage("请至停止线", DeviceDisplay.XP);
		while (true) {
			if (deviceSignal1.getSignal(s1) && !deviceSignal2.getSignal(s2)) {
				i++;
				if (i == 1) {
					this.deviceLight.getDisplay().sendMessage(hphm, DeviceDisplay.SP);
					this.deviceLight.getDisplay().sendMessage("停止", DeviceDisplay.XP);
				}
			}

			if (!deviceSignal1.getSignal(s1)) {
				this.deviceLight.getDisplay().sendMessage(hphm, DeviceDisplay.SP);
				this.deviceLight.getDisplay().sendMessage("请至停止线", DeviceDisplay.XP);
				i = 0;
			} else if (deviceSignal2.getSignal(s2)) {
				this.deviceLight.getDisplay().sendMessage(hphm, DeviceDisplay.SP);
				this.deviceLight.getDisplay().sendMessage("退后", DeviceDisplay.XP);
				i = 0;
			}
			if (i >= 6) {
				break;
			}
			Thread.sleep(500);
		}
	}

	private void reset() {

		checkState = ZT_ZC;
		isChecking = false;
		isCheckLeft = false;
		isCheckRight = false;
		isGetData = false;
		isIllegal = false;
		currentPosition = null;
		errorMessage = "";
		checkData = new byte[112];
		leftMessageList.clear();
		rightMessageList.clear();
		dataList.clear();
	}

	// 设置检测参数
	private void setting(VehCheckLogin vehCheckLogin, List<VehFlow> vheFlows) throws IOException, InterruptedException {

		// 重置设置
		reset();

		List<String> settingList = new ArrayList<String>();

		List<VehFlow> zdList = new ArrayList<VehFlow>();
		List<VehFlow> ydList = new ArrayList<VehFlow>();

		for (VehFlow vehFlow : vheFlows) {
			if (vehFlow.getJyxm().equals("H1") || vehFlow.getJyxm().equals("H2")) {
				zdList.add(vehFlow);
				isCheckLeft = true;
			}
			if (vehFlow.getJyxm().equals("H3") || vehFlow.getJyxm().equals("H4")) {
				ydList.add(vehFlow);
				isCheckRight = true;
			}
		}

		String qzdz = vehCheckLogin.getQzdz();
		if (qzdz.equals("01")) {
			// 四灯 远近光
			settingList.add(sSdz);
			settingList.add(sYjgjc);
			/*
			 * deviceLight.sendMessage(sSdz); deviceLight.sendMessage(sYjgjc);
			 */
			if (zdList.size() == 2) {
				leftMessageList.add("测量左主远光灯,请开启主远光灯");
				leftMessageList.add("测量左副远光灯,请开启副远光灯");
				leftMessageList.add("测量左近光灯,请开启近光灯");
			}
			if (ydList.size() == 2) {
				rightMessageList.add("测量右主远光灯,请开启主远光灯");
				rightMessageList.add("测量右副远光灯,请开启副远光灯");
				rightMessageList.add("测量右近光灯,请开启近光灯");
			}

		} else if (qzdz.equals("02")) {
			// 四灯远光
			/*
			 * deviceLight.sendMessage(sSdz); deviceLight.sendMessage(sDcyg);
			 */

			settingList.add(sSdz);
			settingList.add(sDcyg);

			if (zdList.size() == 2) {
				leftMessageList.add("测量左主远光灯,请开启主远光灯");
				leftMessageList.add("测量左副远光灯,请开启副远光灯");
			}
			if (ydList.size() == 2) {
				rightMessageList.add("测量右主远光灯,请开启主远光灯");
				rightMessageList.add("测量右副远光灯,请开启副远光灯");
			}

		} else if (qzdz.equals("03")) {
			// 二灯远近光
			/*
			 * deviceLight.sendMessage(sEdz); deviceLight.sendMessage(sEdz);
			 */

			settingList.add(sEdz);
			settingList.add(sYjgjc);

			logger.info(sYjgjc);

			if (zdList.size() == 1) {
				leftMessageList.add("测量左远光灯,请开主远光灯");
				leftMessageList.add("测量左近光灯,请开启近光灯");
			}

			if (ydList.size() == 1) {
				rightMessageList.add("测量右远光灯,请开启远光灯");
				rightMessageList.add("测量右近光灯,请开启近光灯");
			}

		} else if (qzdz.equals("04")) {
			// 二灯近光
			/*
			 * deviceLight.sendMessage(sEdz); deviceLight.sendMessage(sDcjg);
			 */

			settingList.add(sEdz);
			settingList.add(sDcjg);

			if (zdList.size() == 1) {
				leftMessageList.add("测量左近光灯,请开启近光灯");
			}
			if (ydList.size() == 1) {
				rightMessageList.add("测量右近光灯,请开启近光灯");
			}
		}

		for (String str : settingList) {
			deviceLight.sendMessage(str);
			Thread.sleep(300);
		}

		// 不需要调整灯光
		deviceLight.sendMessage(sBxytzjg);
		Thread.sleep(300);
		deviceLight.sendMessage(sBxytzyg);
		Thread.sleep(300);

		if (this.deviceLight.getKw().equals("L")) {
			deviceLight.sendMessage(sXcjd);
		} else {
			deviceLight.sendMessage(sXcyd);
		}

	}

	@Override
	public void device2pc(byte[] data) throws IOException, InterruptedException {
		if (data.length == 5) {
			String rtx = CharUtil.byte2HexOfString(data);

			if (rtx.equals(rClwd)) {
				errorMessage = "无法找到灯光";
				this.isError = true;
				this.isChecking = false;
				return;
			}

			if (rtx.equals(rClcc)) {
				errorMessage = "测量出错";
				this.isError = true;
				this.isChecking = false;
				return;
			}

			if (rZygclwc.equals(rtx) || rFygclwc.equals(rtx) || rJgclwc.equals(rtx)) {
				String[] messageArry = null;
				if (currentPosition == 'L') {
					if (leftMessageList.size() > 0) {
						messageArry = leftMessageList.remove(0).split(",");
					} else {
						// 检测结束
						isChecking = false;
						return;
					}

				} else {
					if (rightMessageList.size() > 0) {
						messageArry = rightMessageList.remove(0).split(",");
					} else {
						// 检测结束
						isChecking = false;
						return;
					}

				}
				deviceLight.getDisplay().sendMessage(messageArry[0], DeviceDisplay.SP);
				deviceLight.getDisplay().sendMessage(messageArry[1], DeviceDisplay.XP);
			}
		} else if (isGetData) {
			for (byte b : data) {
				dataList.add(b);
			}
			if (dataList.size() == 112) {
				isGetData = false;
			}
		}

	}

	@Override
	public void setDeviceLight(DeviceLight deviceLight) {
		this.deviceLight = deviceLight;
	}

}
