package com.xs.veh.network.driver;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.network.AbstractDeviceBrakePad;
import com.xs.jt.veh.network.DeviceBrakePad;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.TakePicture;
import com.xs.jt.veh.util.CharUtil;


public class DeviceBrakePadOfJXPB13 extends AbstractDeviceBrakePad {

	static Log logger = LogFactory.getLog(DeviceBrakePadOfJXPB13.class);

	private String ybql = "41046259";

	private String szjcxm = "41086B";

	private String qdjc = "41046457";

	private String dqqzsj = "4104516A";

	private String dqhzsj = "41044873";

	private String dqszdsj = "41045368";

	// 前轴轮重曲线
	private String dqqzlzqx = "41044576";

	// 后轴轮重曲线
	private String dqhzlzqx = "41044675";

	// 读取前制动力曲线
	private String dqqzdlqx = "41045566";

	// 读取后制动力曲线
	private String dqhzdlqx = "41045467";

	// 仪表复位
	private String ybfw = "41045269";

	private byte[] temp;


	private String currentComm;

	private int currntIndex = 0;

	private byte[] qhzsj = new byte[44];

	private byte[] zcsj = new byte[12];

	private byte[] qxsj = new byte[1600];
	
	private List<VehFlow> vehFlows;
	


	@Override
	public List<BrakRollerData> startCheck(List<VehFlow> vehFlows) throws InterruptedException, IOException {

		if (vehFlows == null) {
			return null;
		}
		this.vehFlows=vehFlows;
		try {
			resetCheckStatus();
			sendCommom(ybfw);
			Thread.sleep(2000);
			// 仪表清0
			logger.info("仪表清0命令：" + ybql);
			sendCommom(ybql);

			deviceBrakePad.getDisplay().sendMessage(vehFlows.get(0).getHphm(), DeviceDisplay.SP);
			Thread.sleep(300);
			StringBuilder xm = new StringBuilder("30303030");

			logger.info("vehFlow size :" + vehFlows.size());

			for (VehFlow vehFlow : vehFlows) {
				if (vehFlow.getJyxm().equals("B1")) {
					xm.replace(0, 2, "31");
					qzflag = true;
				} else if (vehFlow.getJyxm().equals("B2")) {
					xm.replace(2, 4, "31");
					hzflag = true;
				} else if (vehFlow.getJyxm().equals("B0")) {
					xm.replace(4, 6, "31");
					zcflag = true;
				}
			}
			xm.insert(0, szjcxm);
			String jy = CharUtil.getCheckSum(xm.toString());
			xm.append(jy);

			logger.info("设置命令：" + xm);
			sendCommom(xm.toString());

			logger.info("启动检测：" + qdjc);
			sendCommom(qdjc);

			checkFlag = true;
			while (checkFlag) {
				Thread.sleep(300);
			}

			if (qzflag) {
				// 读取前轴数据
				logger.info("发送取前轴数据");
				sendGetDataCommom(this.dqqzsj);
				deviceBrakePad.getDisplay().sendMessage("获取前轴数据", DeviceDisplay.XP);
				
				Thread.sleep(200);
				
				logger.info("发送取前轴制动力曲线数据"); // 读取前制动力曲线
				sendGetDataCommom(this.dqqzdlqx);
				deviceBrakePad.getDisplay().sendMessage("获取前轴制动力曲线数据", DeviceDisplay.XP);
				Thread.sleep(200);

				logger.info("发送取前轴轮重曲线数据"); // 读取前轴轮重曲线
				sendGetDataCommom(this.dqqzlzqx);
				deviceBrakePad.getDisplay().sendMessage("获取前轴轮重曲线数据", DeviceDisplay.XP);
				Thread.sleep(200);

			}
			if (hzflag) {
				logger.info("发送后轴数据");
				sendGetDataCommom(this.dqhzsj);
				deviceBrakePad.getDisplay().sendMessage("获取后轴数据", DeviceDisplay.XP);
				Thread.sleep(200);
				// 读取后制动力曲线
				logger.info("发送后制动力曲线");
				sendGetDataCommom(this.dqhzdlqx);
				deviceBrakePad.getDisplay().sendMessage("获取后轴制动力曲线", DeviceDisplay.XP);
				Thread.sleep(200);
				// 读取后轴轮重曲线 
				logger.info("发送后轴轮重曲线");
				sendGetDataCommom(this.dqhzlzqx);
				deviceBrakePad.getDisplay().sendMessage("获取后轴轮重曲线", DeviceDisplay.XP);
				Thread.sleep(200);
			}
			if (zcflag) {
				// 读取驻车制动
				logger.info("读取驻车制动");
				sendGetDataCommom(this.dqszdsj);
				deviceBrakePad.getDisplay().sendMessage("获取驻车制动数据", DeviceDisplay.XP);
				Thread.sleep(200);
			}
			logger.info("brakRollerDatas: " + brakRollerDatas.size());
			Thread.sleep(200);
			return brakRollerDatas;
		} finally {
			
		}
	}

	@Override
	public void device2pc(byte[] data) throws IOException, InterruptedException {
//		logger.info("数据长度：" + data.length + "  currntIndex : " + currntIndex);

		if (currentComm.equals(dqqzsj) || currentComm.equals(dqhzsj)) {
			for (int i = 0; i < data.length; i++) {
				
				if(currntIndex >= qhzsj.length){
					break;
				}
				
				qhzsj[currntIndex] = data[i];
				currntIndex++;
				//logger.info("currntIndex: " + currntIndex);
				if (currntIndex == qhzsj.length) {
					setQHData(qhzsj);
				}
			}
		} else if (dqszdsj.equals(currentComm)) {
			for (int i = 0; i < data.length; i++) {
				if(currntIndex >= zcsj.length){
					break;
				}
				
				zcsj[currntIndex] = data[i];
				currntIndex++;
				logger.info("currntIndex: " + currntIndex);
				if (currntIndex == zcsj.length) {
					setZCData(zcsj);
				}
			}
		} else if (dqqzdlqx.equals(currentComm) || dqhzdlqx.equals(currentComm) || dqhzlzqx.equals(currentComm)
				|| dqqzlzqx.equals(currentComm)) {

			for (int i = 0; i < data.length; i++) {
				if(currntIndex >= qxsj.length){
					break;
				}
				qxsj[currntIndex] = data[i];
				currntIndex++;
			//	logger.info("currntIndex: " + currntIndex);
				if (currntIndex == qxsj.length) {
					processQX(qxsj, currentComm);
					break;
				}
			}

		} else {
			logger.info("返回数据：" + CharUtil.byte2HexOfString(data));
			Integer index=null;
			for(int z=0;z<data.length;z++){
				if(data[z]==0x41){
					int length = CharUtil.byteToInt(data[z+1]);
					if(length==4){
						index=z;
						break;
					}
				}
			}
			
			if(index!=null){
				temp = new byte[4];
				temp[0]=data[index];
				temp[1]=data[index+1];
				temp[2]=data[index+2];
				temp[3]=data[index+3];
				processData(temp);
			}
		
		}

	}

	private void processQX(byte[] data, String common) {
		logger.info("处理曲线数据 命令："+common);
		if (data.length == 1600) {
			StringBuilder dbdataLeft = new StringBuilder();
			StringBuilder dbdataRigth = new StringBuilder();
			for (int i = 0; i < 1600; i++) {
				char zq = (char) data[i];
				i++;
				char zb = (char) data[i];
				i++;
				char zs = (char) data[i];
				i++;
				char zg = (char) data[i];
				i++;
				char yq = (char) data[i];
				i++;
				char yb = (char) data[i];
				i++;
				char ys = (char) data[i];
				i++;
				char yg = (char) data[i];

				String strTempLeft = new String(new char[] { zq, zb, zs, zg });
				Integer intdataLeft = Integer.parseInt(strTempLeft);
				dbdataLeft.append(intdataLeft.toString());
				dbdataLeft.append(",");
				String strTempRigth = new String(new char[] { yq, yb, ys, yg });
				Integer intdataRigth = Integer.parseInt(strTempRigth);
				dbdataRigth.append(intdataRigth.toString());
				dbdataRigth.append(",");
			}

			int dbDataLeftLength = dbdataLeft.length();
			if (dbDataLeftLength > 0) {
				dbdataLeft.deleteCharAt(dbDataLeftLength - 1);
			}

			int dbDataRigthLength = dbdataRigth.length();
			if (dbDataRigthLength > 0) {
				dbdataRigth.deleteCharAt(dbDataRigthLength - 1);
			}

			String jyxm = null;
			BrakRollerData brd=null;
			if (common.equals(dqqzdlqx) || common.equals(dqqzlzqx)) {
				jyxm = "B1";
			} 
			
			if(common.equals(dqhzdlqx) || common.equals(dqhzlzqx)){
				jyxm = "B2";
			}
			
			logger.info("jyxm:"+jyxm);
			if(jyxm==null){
				return;
			}
			brd = this.getBrakRollerDataOfJyxm(jyxm);

			if (common.equals(dqqzdlqx) || common.equals(dqhzdlqx)) {
				brd.setLeftDataStr(dbdataLeft.toString());
				brd.setRigthDataStr(dbdataRigth.toString());
			}
			if (common.equals(dqhzlzqx) || common.equals(dqqzlzqx)) {
				brd.setZdtlhStr(dbdataLeft.toString());
				brd.setYdtlhStr(dbdataRigth.toString());
			}
		}

	}

	private void processData(byte[] data) throws IOException, InterruptedException  {
		int length = data.length;
		if (length == 4) {
			logger.info("返回数据：" + CharUtil.byte2HexOfString(data));
			if (data[0] == 0x41 && data[1] == 0x04) {
				switch (data[2]) {
				case 0x31:
					this.getDeviceBrakePad().getDisplay().sendMessage("前进！检测制动", DeviceDisplay.XP);
					break;
				case 0x32:
					this.getDeviceBrakePad().getDisplay().sendMessage("请踩刹车", DeviceDisplay.XP);
					TakePicture.createNew(this.deviceBrakePad.getVehCheckLogin(), "B1");
					Thread.sleep(200);
					TakePicture.createNew(this.deviceBrakePad.getVehCheckLogin(), "B2");
					break;
				case 0x35:
					this.getDeviceBrakePad().getDisplay().sendMessage("前进！检测手制动", DeviceDisplay.XP);
					break;
				case 0x36:
					this.getDeviceBrakePad().getDisplay().sendMessage("请拉手刹", DeviceDisplay.XP);
					TakePicture.createNew(this.deviceBrakePad.getVehCheckLogin(), "B0");
					break;
				case 0x37:
					this.getDeviceBrakePad().getDisplay().sendMessage("检测结束", DeviceDisplay.XP);
					this.checkFlag = false;
					break;
				}
			}
		}
	}

	private void setZCData(byte[] data) {
		logger.info("设置驻车数据：");
		BrakRollerData brData = getBrakRollerDataOfJyxm("B0");
		Integer zzdl = Integer.parseInt(new String(new byte[] { data[3], data[4], data[5], data[6] }));
		Integer yzdl = Integer.parseInt(new String(new byte[] { data[7], data[8], data[9], data[10] }));
		brData.setZzdl(zzdl);
		brData.setYzdl(yzdl);
		brData.setZw(2);
		brakRollerDatas.add(brData);
	}

	private void setzdlqx(String jyxm, byte[] data) {
		BrakRollerData brData = getBrakRollerDataOfJyxm("B0");

	}

	private void setQHData(byte[] data) {
		logger.info("设置前后轴数据:" + CharUtil.byte2HexOfString(new byte[] { (data[2]) }));
		String jyxm = null;
		Integer zw = null;
		if (data[2] == 0x48) {
			jyxm = "B2";
			zw = 2;
		} else if (data[2] == 0x51) {
			jyxm = "B1";
			zw = 1;
		}
		if (jyxm != null) {
			BrakRollerData brData = getBrakRollerDataOfJyxm(jyxm);
			Integer zzdl = Integer.parseInt(new String(new byte[] { data[3], data[4], data[5], data[6] }));
			Integer yzdl = Integer.parseInt(new String(new byte[] { data[7], data[8], data[9], data[10] }));
			Integer zgcc = Integer.parseInt(new String(new byte[] { data[11], data[12], data[13], data[14] }));
			Integer ygcc = Integer.parseInt(new String(new byte[] { data[15], data[16], data[17], data[18] }));
			Integer zzzl = Integer.parseInt(new String(new byte[] { data[19], data[20], data[21], data[22] }));
			Integer yzzl = Integer.parseInt(new String(new byte[] { data[23], data[24], data[25], data[26] }));
			Integer zlh = Integer.parseInt(new String(new byte[] { data[27], data[28], data[29], data[30] }));
			Integer ylh = Integer.parseInt(new String(new byte[] { data[31], data[32], data[33], data[34] }));
			Integer zdtlh = Integer.parseInt(new String(new byte[] { data[35], data[36], data[37], data[38] }));
			Integer ydtlh = Integer.parseInt(new String(new byte[] { data[39], data[40], data[41], data[42] }));
			brData.setZzdl(zzdl);
			brData.setYzdl(yzdl);
			brData.setZzdlcd(zgcc);
			brData.setYzdlcd(ygcc);
			brData.setZlh(zlh);
			brData.setYlh(ylh);
			brData.setZdtlh(zdtlh);
			brData.setYdtlh(ydtlh);
			brData.setZw(zw);
			brakRollerDatas.add(brData);
		}
	}

	private BrakRollerData getBrakRollerDataOfJyxm(String jyxm) {
		BrakRollerData currentData = null;
		for (BrakRollerData data : brakRollerDatas) {
			if (data.getJyxm().equals(jyxm)) {
				return data;
			}
		}
		currentData = new BrakRollerData();
		currentData.setJyxm(jyxm);
		return currentData;
	}

	@Override
	public void init(DeviceBrakePad deviceBrakePad) {
		this.deviceBrakePad = deviceBrakePad;
	}

	private void sendCommom(String common) throws IOException, InterruptedException  {
		deviceBrakePad.sendMessage(common);
		currentComm = common;
		logger.info(common);
		Thread.sleep(300);
	}

	private void sendGetDataCommom(String common) throws InterruptedException, IOException {

		Thread.sleep(100);
		deviceBrakePad.sendMessage(common);
		currentComm = common;
		currntIndex = 0;
		logger.info(common);
		int times = 5;
		if (common.equals(dqhzsj) || common.equals(dqqzsj)) {
			while (currntIndex < qhzsj.length) {
				Thread.sleep(times);
			}
		}

		if (common.equals(dqszdsj)) {
			while (currntIndex < zcsj.length) {
				Thread.sleep(times);
			}
		}

		if (common.equals(dqqzdlqx) || common.equals(dqhzdlqx) || common.equals(dqqzlzqx) || common.equals(dqhzlzqx)) {
			
			deviceBrakePad.getDisplay().sendMessage("获取数据请等待！", DeviceDisplay.XP);
			
			while (currntIndex < qxsj.length) {
				Thread.sleep(times);
			}
		}
	}
}
