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

import net.sf.json.JSONObject;

public class DeviceLightDriverOfMQD13A extends AbstractDeviceLight {

	static Log logger = LogFactory.getLog(DeviceLightDriverOfMQD13A.class);

	// -----------测量--------------

	// 测左灯
	private String tCzd = "07";

	// 测右灯
	private String tCyd = "41";

	// 取仪器状态
	private String tQyqzt = "43";

	// -----------取数 ------------

	// 取左辅灯数据
	private String gQzfdsj = "4A";

	// 取右辅灯数据
	private String gQyfdsj = "4B";

	// 取左主灯数据
	private String gQzzdsj = "4C";

	// 取右主灯数据
	private String gQyzdsj = "4D";

	// 取当前数据
	private String gQdqsj = "4E";

	// -------------控制------------
	// 仪器归位
	private String mYqgw = "47";

	private String mtz = "3E";

	// ----------设置 ------------
	// 设置左靠位
	private String sSzzkw = "80";

	// 设置右靠位
	private String sSzykw = "81";

	// 单测远光
	private String sDcyg = "33";
	// 单测近光
	private String sDcjg = "34";
	// 远近光均测
	private String sYjgjc = "35";
	// 先测远灯
	// private String sXcyd = "020132537107";
	// 先测近灯
	// private String sXcjd = "020132537206";
	// 四灯制
	private String sSdz = "31";
	// 二灯制
	private String sEdz = "32";
	// 需要调整远光
	private String sXytzyg = "60";
	// 不需要调整远光
	private String sBxytzyg = "61";

	// 需要调整近光
	private String sXytzjg = "62";
	// 不需要调整近光
	private String sBxytzjg = "63";

	// -----------仪器 to PC------------------------

	// 主远光测量完成
	private String rYgclwc = "4F";
	// 主远光测量完成
	private String rJgclwc = "4D";

	// 归位完成
	private String rGwwc = "40";

	// 测量出错
	private String rClcc = "13";

	private List<String> leftMessageList = new ArrayList<String>();
	private List<String> rightMessageList = new ArrayList<String>();

	private List<Byte> dataList = new ArrayList<Byte>();

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

	private String kw;

	private VehCheckLogin vehCheckLogin;

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

		if ("L".equals(kw)) {
			currentPosition = 'L';
			checking(vehCheckLogin);
			Thread.sleep(2000);
			logger.info("开始检测右灯");
			currentPosition = 'R';
			checking(vehCheckLogin);
		} else {
			currentPosition = 'R';
			checking(vehCheckLogin);
			Thread.sleep(2000);
			currentPosition = 'L';
			checking(vehCheckLogin);
		}

		isGetData = true;
		List<LightData> datas = getData(vheFlows, vehCheckLogin);
		// 仪器归位
		this.deviceLight.sendMessage(mYqgw);
		Thread.sleep(300);


		return datas;

	}

	private List<LightData> getData(List<VehFlow> vheFlows, VehCheckLogin vehCheckLogin)
			throws InterruptedException, IOException, SystemException {

		if (checkState != ZT_ZC) {
			return null;
		}
		List<LightData> lightDatas = new ArrayList<LightData>();
		for (VehFlow vehFlow : vheFlows) {
			dataList.clear();
			if (vehFlow.getJyxm().equals("H1")) {
				this.deviceLight.sendMessage(gQzzdsj);
				logger.info("取数："+gQzzdsj);
			} else if (vehFlow.getJyxm().equals("H2")) {
				this.deviceLight.sendMessage(gQzfdsj);
				logger.info("取数："+gQzfdsj);
			} else if (vehFlow.getJyxm().equals("H3")) {
				this.deviceLight.sendMessage(gQyfdsj);
				logger.info("取数："+gQyfdsj);
			} else if (vehFlow.getJyxm().equals("H4")) {
				this.deviceLight.sendMessage(gQyzdsj);
				logger.info("取数："+gQyzdsj);
			}
			while (dataList.size() < 31) {
				//logger.info("数据长度："+dataList.size());
				Thread.sleep(100);
			}
			logger.info("数据获取完成！");
			for (int i = 0; i < 31; i++) {
				checkData[i] = dataList.get(i);
			}
			String strData = new String(checkData);
			logger.info("返回数据："+strData);

			if (vehCheckLogin.getQzdz().equals("01") || vehCheckLogin.getQzdz().equals("02")
					|| vehCheckLogin.getQzdz().equals("03") || vehCheckLogin.getQzdz().equals("05")) {
				LightData data = new LightData();
				data.setWz((vehFlow.getJyxm().equals("H1") || vehFlow.getJyxm().equals("H2")) ? LightData.WZ_Z
						: LightData.WZ_Y);
				data.setDx((vehFlow.getJyxm().equals("H1") || vehFlow.getJyxm().equals("H4")) ? LightData.DX_ZD
						: LightData.DX_FD);
				data.setGx(LightData.GX_YGD);
				setCurrent(strData, data);
				lightDatas.add(data);
			}
			if (vehCheckLogin.getQzdz().equals("01") || vehCheckLogin.getQzdz().equals("03")
					|| vehCheckLogin.getQzdz().equals("04")) {
				LightData data = new LightData();
				data.setWz((vehFlow.getJyxm().equals("H1") || vehFlow.getJyxm().equals("H2")) ? LightData.WZ_Z
						: LightData.WZ_Y);
				data.setDx((vehFlow.getJyxm().equals("H1") || vehFlow.getJyxm().equals("H4")) ? LightData.DX_ZD
						: LightData.DX_FD);
				data.setGx(LightData.GX_JGD);
				setCurrent(strData, data);
				lightDatas.add(data);
			}

		}

		return lightDatas;

	}

	private void setCurrent(String strData, LightData lightData) throws IOException, InterruptedException, SystemException {
		
		String ygsppc=strData.substring(2,6);
		String ygczpc=strData.substring(6,10);
		String strGq=strData.substring(10,14);
		String ygdg=strData.substring(14,18);
		String jgsppc=strData.substring(18,22);
		String jgczpc=strData.substring(22,26);
		String jgdg=strData.substring(26,30);

		if ("测量出错".equals(errorMessage)) {
			lightData.setSppc("0");
		} else {
			if (lightData.getGx() == LightData.GX_YGD){
				lightData.setSppc(ygsppc);
				logger.info("sppc"+ygsppc);
			}else{
				lightData.setSppc(jgsppc);
				logger.info("sppc"+new String(jgsppc));
			}
			
			
		}

		if ("测量出错".equals(errorMessage)) {
			lightData.setCzpc("0");
		} else {
			if (lightData.getGx() == LightData.GX_YGD){
				lightData.setCzpc(ygczpc);
				logger.info("czpc:"+ygczpc);
			}else{
				lightData.setCzpc(jgczpc);
				logger.info("czpc:"+jgczpc);
			}
			
		}

		logger.info("gq"+strGq);
		if ("测量出错".equals(errorMessage)) {
			lightData.setGq(0);
		} else if (CharUtil.isNumeric(strGq)||lightData.getGx() == LightData.GX_JGD ) {
			lightData.setGq(lightData.getGx() == LightData.GX_YGD ? (Integer.parseInt(strGq) * 100) : null);
		} else {
			deviceLight.sendMessage(mtz);
			Thread.sleep(300);
			deviceLight.sendMessage(mYqgw);
			deviceLight.getDisplay().sendMessage("仪器数据异常", DeviceDisplay.SP);
			deviceLight.getDisplay().sendMessage("重检测，请等待", DeviceDisplay.XP);
			Thread.sleep(1000 * 20);
			throw new SystemException("仪器数据异常！");
		}

		if ("测量出错".equals(errorMessage)) {
			lightData.setDg(1);
		} else if (CharUtil.isNumeric(ygdg)&&lightData.getGx() == LightData.GX_YGD ) {
			lightData.setDg(Integer.parseInt(ygdg.trim()));
		}else if(CharUtil.isNumeric(jgdg)&&lightData.getGx() == LightData.GX_JGD){
			lightData.setDg(Integer.parseInt(jgdg.trim()));
		}

	}
	// 开始检测
	private void checking(VehCheckLogin vehCheckLogin) throws InterruptedException, IOException, SystemException {
		this.isChecking = true;
		this.isError = false;

		String qzdz = vehCheckLogin.getQzdz();

		String gx = "远";
		if ("04".equals(qzdz)) {
			gx = "近";
		}
		if (currentPosition == 'L') {
			deviceLight.getDisplay().sendMessage("测量左灯光", DeviceDisplay.SP);
			deviceLight.getDisplay().sendMessage("请打开" + gx + "光灯", DeviceDisplay.XP);
		} else {
			deviceLight.getDisplay().sendMessage("测量右灯光", DeviceDisplay.SP);
			deviceLight.getDisplay().sendMessage("请打开" + gx + "光灯", DeviceDisplay.XP);
		}
		deviceLight.sendMessage(currentPosition == 'L' ? tCzd : tCyd);
		TakePicture.createNew(this.deviceLight.getVehCheckLogin(), currentPosition == 'L'?"H1":"H4", 7000);
		while (true) {
			if (this.isError) {
				deviceLight.getDisplay().sendMessage(errorMessage, DeviceDisplay.XP);
				Thread.sleep(1000 * 2);
				if (!"测量出错".equals(errorMessage)) {
					// 仪器归位
					this.deviceLight.sendMessage(mYqgw);
					Thread.sleep(1000 * 20);
					throw new SystemException(errorMessage);
				}
			}
			if (!this.isChecking) {
				logger.info("检测结束，退出等待！");
				break;
			}
			Thread.sleep(200);
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

	private void reset(VehCheckLogin vehCheckLogin) {
		checkState = ZT_ZC;
		isChecking = false;
		isGetData = false;
		isIllegal = false;
		currentPosition = null;
		errorMessage = "";
		checkData = new byte[31];
		leftMessageList.clear();
		rightMessageList.clear();
		dataList.clear();
		JSONObject param = JSONObject.fromObject(this.deviceLight.getDevice().getQtxx());
		kw = (String) param.get("kw");
		this.vehCheckLogin = vehCheckLogin;
		logger.info("靠位"+kw);
	}

	// 设置检测参数
	private void setting(VehCheckLogin vehCheckLogin, List<VehFlow> vheFlows) throws IOException, InterruptedException {
		// 重置设置
		reset(vehCheckLogin);
	/*	// 设置靠位
		if ("R".equals(kw)) {
			this.deviceLight.sendMessage(sSzykw);
		} else {
			this.deviceLight.sendMessage(sSzzkw);
		}
		Thread.sleep(300);*/

		String qzdz = vehCheckLogin.getQzdz();

		// 设置远近光
		if ("01".equals(qzdz) || "03".equals(qzdz)) {
			this.deviceLight.sendMessage(sYjgjc);
		} else if ("02".equals(qzdz) || "05".equals(qzdz)) {
			this.deviceLight.sendMessage(sDcyg);
		} else if ("04".equals(qzdz)) {
			this.deviceLight.sendMessage(sDcjg);
		}
		Thread.sleep(300);

		// 设置灯型
		if ("01".equals(qzdz) || "02".equals(qzdz)) {
			this.deviceLight.sendMessage(sSdz);
		} else {
			this.deviceLight.sendMessage(sEdz);
		}
		Thread.sleep(300);

		// 不需要调整灯光
		//deviceLight.sendMessage(sBxytzyg);
		//Thread.sleep(300);
		//deviceLight.sendMessage(sBxytzjg);
		//Thread.sleep(300);
	}

	@Override
	public void device2pc(byte[] data) throws IOException, InterruptedException {
		
		if (isGetData) {
			for (byte b : data) {
				dataList.add(b);
			}
		} else if (data.length == 1) {
			String rtx = CharUtil.byte2HexOfString(data);
			logger.info("灯光仪返回："+rtx);
			if (rtx.equals(rClcc)) {
				errorMessage = "测量出错";
				this.isError = true;
				this.isChecking = false;
				return;
			}

			String qzdz = vehCheckLogin.getQzdz();

			if (rtx.equals(rYgclwc)) {
				deviceLight.getDisplay().sendMessage("远光灯测量结束", DeviceDisplay.SP);
				if (qzdz.equals("01") || qzdz.equals("03")) {
					deviceLight.getDisplay().sendMessage("切换近光灯", DeviceDisplay.XP);
				}else{
					this.isChecking = false;
				}
			}
			if (rtx.equals(rJgclwc)) {
				deviceLight.getDisplay().sendMessage("近光灯测量结束", DeviceDisplay.XP);
				this.isChecking = false;
			}
		}

	}

	@Override
	public void setDeviceLight(DeviceLight deviceLight) {
		this.deviceLight = deviceLight;
	}

}
