package com.xs.veh.network.driver;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.network.AbstractDeviceBrakRoller;
import com.xs.jt.veh.network.DeviceBrakRoller;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.TakePicture;
import com.xs.jt.veh.util.CharUtil;

/**
 * 江新加载制动台
 * 
 * @author linze
 *
 */
public class DeviceBrakRollerDriverOfJXGT2CZ extends AbstractDeviceBrakRoller {

	private static Log logger = LogFactory.getLog(DeviceBrakRollerDriverOfJXGT2CZ.class);

	// 举升器上升
	private String jsqss = "41046358";

	// 举升器下降
	private String jsqxj = "41046457";

	// 仪表清零
	private String ybql = "41046259";

	// 开始检测
	private String ksjc = "41046655";

	private String kscz = "41046853 ";
	
	private String qs="41046B50";
	
	private String ttjs="4104704B";
	
	private String ttxj="4104714A";
	
	private String dwzt="4104615A";
	

	private String scMessage;


	@Override
	public BrakRollerData startCheck(VehFlow vehFlow) throws SystemException, IOException, InterruptedException {
		try {
			Integer intZw = Integer.parseInt(vehFlow.getJyxm().substring(1, 2));
			scMessage = vehFlow.getJyxm().equals("B0") ? "请拉手刹" : "请踩刹车";
			String zw = getZW(intZw);
			this.getTemp().clear();
			Thread.sleep(1000);
			// 清理数据
			deviceBrakRoller.clearDate();
			
			/*logger.info("下降：" + jsqxj);
			deviceBrakRoller.sendMessage(jsqxj);
			logger.info("下降：" + CharUtil.byte2HexOfString(getDevData(new byte[4])));*/
			
			if(vehFlow.getJyxm().indexOf("L")==0){
				dw(vehFlow, zw,1);
				logger.info("台体举升：" + ttjs);
				deviceBrakRoller.sendMessage(ttjs);
				logger.info("台体举升返回：" + CharUtil.byte2HexOfString(getDevData(new byte[4])));
				cz(1);
			}else if(vehFlow.getJyxm().indexOf("B")==0&&!vehFlow.getJyxm().equals("B0")){
				logger.info("等待称重到位");
				dw(vehFlow, zw,0);
				cz(0);
			}
			
			dw(vehFlow, zw,1);
			
			
			deviceBrakRoller.getDisplay().sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
			deviceBrakRoller.getDisplay().sendMessage("开始检测制动", DeviceDisplay.XP);
			Thread.sleep(1000);
			
			logger.info("开始检测命令：" +ksjc);
			// 开始检测
			deviceBrakRoller.sendMessage(ksjc);
			
			byte[] d1 = getDevData(new byte[4]);
			
			logger.info("开始检测命令返回：" + CharUtil.byte2HexOfString(d1));
			
			deviceBrakRoller.getDisplay().sendMessage("开始检测阻滞力", DeviceDisplay.XP);
			byte[] d2 = getDevData(new byte[5]);
			byte[] d3 = getDevData(new byte[12]);
			
			deviceBrakRoller.getDisplay().sendMessage("阻滞力检测结束", DeviceDisplay.SP);
			deviceBrakRoller.getDisplay().sendMessage(scMessage, DeviceDisplay.XP);
			
			TakePicture.createNew(this.deviceBrakRoller.getVehCheckLogin(), vehFlow.getJyxm(), 1000);
			
			while(true){
 				byte[] d4 =  getDevData(new byte[12]);
 				
 				if(d4[2]==0x45&&d4[6]==0x45&&d4[10]==0x45){
 					break;
 				}else{
 					Integer zlzd=Integer.parseInt(new String(new byte[]{d4[3],d4[4],d4[5],d4[6]}).trim());
 					Integer ylzd=Integer.parseInt(new String(new byte[]{d4[7],d4[8],d4[9],d4[10]}).trim());
 					brakRollerData.getLeftData().add(zlzd);
 					brakRollerData.getRigthData().add(ylzd);
 				}
			}
			
			deviceBrakRoller.setInfoData(brakRollerData);
			//brakRollerData.setSfjzz(BrakRollerData.SFJZZ_NO);
			logger.info("开始取数据：" + qs);
			//获取检测结果
			deviceBrakRoller.sendMessage(qs);
			byte[] d5 = getDevData(new byte[20]);
			logger.info("取数结束");
			Integer zzdl=Integer.parseInt(new String(new byte[]{d5[3],d5[4],d5[5],d5[6]}).trim());
			Integer yzdl=Integer.parseInt(new String(new byte[]{d5[7],d5[8],d5[9],d5[10]}).trim());
			
			Integer zgcc=Integer.parseInt(new String(new byte[]{d5[11],d5[12],d5[13],d5[14]}).trim());
			Integer ygcc=Integer.parseInt(new String(new byte[]{d5[15],d5[16],d5[17],d5[18]}).trim());
			
			brakRollerData.setZzdl(zzdl);
			brakRollerData.setYzdl(yzdl);
			brakRollerData.setZzdlcd(zgcc);
			brakRollerData.setYzdlcd(ygcc);

			deviceBrakRoller.getDisplay().sendMessage(zw + "制动检测完成", DeviceDisplay.SP);
			if (intZw != 0) {
				brakRollerData.setZw(intZw);
			} else {
				brakRollerData.setZw(Integer.parseInt(vehFlow.getMemo().trim()));
			}
			return brakRollerData;
		} finally {
			if (nextVehFlow == null || (!nextVehFlow.getJyxm().equals("B0"))
					|| (nextVehFlow.getJyxm().equals("B0") && vehFlow.getJyxm().equals("B0"))) {
				this.deviceBrakRoller.sendMessage(jsqss);
				Thread.sleep(500);
			}
			
			if(vehFlow.getJyxm().indexOf("L")==0){
				logger.info("台体下降：" + ttxj);
				deviceBrakRoller.sendMessage(ttxj);
				logger.info("台体下降返回：" + CharUtil.byte2HexOfString(getDevData(new byte[4])));
			}
		}
	}
	
	public boolean getSignal(int type) throws IOException, InterruptedException{
		
			/*logger.info("开始获取光电状态："+dwzt);*/
			deviceBrakRoller.sendMessage(dwzt);
		/*	logger.info("光电类型："+type);*/
			int index=type==0?3:4;
			byte[] dwzt = this.getDevData(new byte[12]);
			/*logger.info("光电返回"+CharUtil.byte2HexOfString(dwzt));*/
			if(dwzt[index]==0x4F){
				/*logger.info("光电到位");*/
				return true;
			}else{
				return false;
			}
	}

	/**
	 * 称重
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SystemException
	 */
	private void cz(int type) throws InterruptedException, IOException, SystemException {

		// 开始称重命令
		int i = 0;
		int zlh = 0;
		int ylh = 0;
		deviceBrakRoller.getDisplay().sendMessage("开始称重", DeviceDisplay.SP);
		while (true) {
			int index=type==0?3:11;
			logger.info("轮重下标"+index);
			deviceBrakRoller.sendMessage(kscz);
			byte[] czdata = getDevData(new byte[20]);
			logger.info("称重返回：" + CharUtil.byte2HexOfString(czdata));
			zlh = Integer.parseInt(new String(new byte[] { czdata[index++], czdata[index++], czdata[index++], czdata[index++] }).trim());
			ylh = Integer.parseInt(new String(new byte[] { czdata[index++], czdata[index++], czdata[index++], czdata[index++] }).trim());
			deviceBrakRoller.getDisplay().sendMessage(zlh + ylh + "KG", DeviceDisplay.XP);
			if (i >= 20) {
				break;
			}
			i++;
			Thread.sleep(300);
		}

		if (zlh + ylh == 0) {
			throw new SystemException("称重数据异常:" + zlh + " \\ " + ylh);
		} else {
			deviceBrakRoller.getDisplay().sendMessage("结束称重", DeviceDisplay.SP);
			brakRollerData.setZlh(zlh);
			brakRollerData.setYlh(ylh);
			Thread.sleep(1000);
		}
	}

	private void dw(VehFlow vehFlow, String zw,int type) throws InterruptedException, IOException {
		// 发送到位延时时间
		Thread.sleep(200);
		String strType=type==0?"称重":"制动";

		deviceBrakRoller.getDisplay().sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
		deviceBrakRoller.getDisplay().sendMessage(zw +strType+ "请到位", DeviceDisplay.XP);
		
		logger.info(zw +strType+ "请到位");
		
		// 等待到位
		int i = 0;
		while (true) {
			if(getSignal(type)){
				deviceBrakRoller.getDisplay().sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
				deviceBrakRoller.getDisplay().sendMessage(zw + strType+"已到位", DeviceDisplay.XP);
				i++;
			}else{
				deviceBrakRoller.getDisplay().sendMessage(vehFlow.getHphm(), DeviceDisplay.SP);
				deviceBrakRoller.getDisplay().sendMessage(zw +strType+ "请到位", DeviceDisplay.XP);
				i = 0;
			}
			
			if (i >= 6) {
				break;
			}
			Thread.sleep(500);
		}
		logger.info(zw +strType+ "已到位");
		if(type==1){
			logger.info("举升器下降命令：" +jsqxj);
			deviceBrakRoller.sendMessage(jsqxj);
			logger.info("举升器下降返回：" + CharUtil.byte2HexOfString(getDevData(new byte[4])));
			Thread.sleep(12000);
		}
		
	}

	// 倒数计时线程
	public void ds(final String title, final Integer ms, final String afterTitle) {

		deviceBrakRoller.getExecutor().execute(new Runnable() {
			public void run() {
				try {
					Thread.sleep(300);
					deviceBrakRoller.getDisplay().sendMessage(title, DeviceDisplay.SP);
					for (int i = 1; i <= ms; i++) {
						if (isbs) {
							return;
						}
						deviceBrakRoller.getDisplay().sendMessage(String.valueOf(i), DeviceDisplay.XP);
						Thread.sleep(1000);
					}
					if (afterTitle != null) {
						deviceBrakRoller.getDisplay().sendMessage(afterTitle, DeviceDisplay.XP);
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/*public static void main(String[] ages) {

		String[] a=new String[]{"1","2","3"};
		int i=0;
		System.out.println(a[i++]);
		System.out.println(i);

	}*/


	@Override
	public void init(DeviceBrakRoller deviceBrakRoller) {

		this.deviceBrakRoller = deviceBrakRoller;
	}

}
