package com.xs.jt.veh.network;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.dao.network.BrakRollerDataRepository;
import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.entity.network.OtherInfoData;
import com.xs.jt.veh.entity.network.ParDataOfAnjian;
import com.xs.jt.veh.util.CommonUtil;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;

@Service("deviceBrakePad")
@Scope("prototype")
public class DeviceBrakePad extends SimpleRead implements ICheckDevice {

//	@Resource(name = "checkDataManager")
//	private CheckDataManager checkDataManager;

	private static Log logger = LogFactory.getLog(DeviceBrakePad.class);

	private AbstractDeviceBrakePad dbp;

	private DeviceDisplay display;

	private VehCheckLogin vehCheckLogin;

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private BrakRollerDataRepository brakRollerDataRepository;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;

	public VehCheckLogin getVehCheckLogin() {
		return vehCheckLogin;
	}

	public void setVehCheckLogin(VehCheckLogin vehCheckLogin) {
		this.vehCheckLogin = vehCheckLogin;
	}

	public DeviceDisplay getDisplay() {
		return display;
	}

	public void setDisplay(DeviceDisplay display) {
		this.display = display;
	}

	public DeviceBrakePad(Device device)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchPortException,
			TooManyListenersException, PortInUseException, UnsupportedCommOperationException, IOException {
		super(device);
		init();
	}

	public DeviceBrakePad() {

	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据
			// 制动返回数据
			byte[] readBuffer = new byte[1024 * 64];
			int length = 0;
			int lengthTemp = 0;
			try {
				while (inputStream.available() > 0) {
					lengthTemp = inputStream.read(readBuffer);
					length += lengthTemp;
					// logger.info("数据长度" + length);
					if (length >= 1024 * 64) {
						logger.debug("读入的数据超过1024 * 64");
						break;
					}
				}
				byte[] endodedData = new byte[length];
				System.arraycopy(readBuffer, 0, endodedData, 0, length);

				dbp.device2pc(endodedData);

			} catch (Exception e) {
				logger.error("制动设备获取数据异常", e);
			}
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void startCheck(VehCheckLogin vehCheckLogin, VehFlow vehFlow, Map<String, Object> otherParam) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startCheck(VehCheckLogin vehCheckLogin, List<VehFlow> vehFlows, Map<String, Object> otherParam)
			throws InterruptedException, IOException {

		this.vehCheckLogin = vehCheckLogin;

		Date startDate = new Date();
		DecimalFormat decimalFormat = new DecimalFormat(".0");
		List<BrakRollerData> datas = dbp.startCheck(vehFlows);
		// 驻车结果
		ParDataOfAnjian parDataOfAnjian = null;
		OtherInfoData otherInfoData = new OtherInfoData();
		otherInfoData.setBaseInfo(vehCheckLogin);
		Integer zclh = 0;
		Integer zdlh = 0;
		boolean sfhg = true;
		for (BrakRollerData brakRollerData : datas) {
			// 设置基础数据
			brakRollerData.setBaseDeviceData(vehCheckLogin, vehCheckLogin.getJycs(), brakRollerData.getJyxm());
			// 非驻车制动则计算检测结果
			if (!brakRollerData.getJyxm().equals("B0")) {
				// 空载行车制动率
				brakRollerData.setKzxczdl(vehCheckLogin);
				// 空载制动率限制及判定
				brakRollerData.setKzzdlxz(vehCheckLogin);
				brakRollerData.setKzzdlpd();

				// 设置空载不平衡率
				brakRollerData.setKzbphl(vehCheckLogin);
				// 设置不平衡率限值
				brakRollerData.setBphlxz(vehCheckLogin);
				// 空载不平衡率判定
				brakRollerData.setKzbphlpd();
				brakRollerData.setZpd();

				String strpd = "O";

				if (brakRollerData.getZpd() == BrakRollerData.PDJG_BHG) {
					sfhg = false;
					strpd = "X";
				}
				display.sendMessage(
						brakRollerData.getZw() + "轴：" + decimalFormat.format(brakRollerData.getKzxczdl()) + "/"
								+ decimalFormat.format(brakRollerData.getKzbphl()) + "/" + strpd,
						brakRollerData.getZw() == 1 ? DeviceDisplay.SP : DeviceDisplay.XP);
				zdlh += brakRollerData.getZzdl() + brakRollerData.getYzdl();
				zclh += brakRollerData.getZlh() + brakRollerData.getYlh();
			} else {
				parDataOfAnjian = new ParDataOfAnjian();
				parDataOfAnjian.setZczczdl(brakRollerData.getZzdl() + brakRollerData.getYzdl());
			}
		}

		Thread.sleep(2000);

		if (parDataOfAnjian != null) {
			logger.info("驻车判定！");
			Integer zczczdl = parDataOfAnjian.getZczczdl();
			zczczdl = zczczdl == null ? 0 : zczczdl;
			
			Integer oldzclh=(Integer) otherParam.get("zclh");
			zclh=(zclh==0&&oldzclh!=null)?oldzclh:zclh;
			
			logger.info("驻车轮荷："+zclh);
			logger.info("驻车制动力："+parDataOfAnjian.getZczczdl());
			if(zclh>0){
				parDataOfAnjian.setTczclh(zclh);
				Float tczdl = (float) ((parDataOfAnjian.getZczczdl() * 1.0 / (zclh * 0.98 * 1.0)) * 100);
				logger.info("驻车制动率："+CommonUtil.MathRound1(tczdl));
				parDataOfAnjian.setTczclh(zclh);
				parDataOfAnjian.setTczdl(CommonUtil.MathRound1(tczdl));
				parDataOfAnjian.setTczdxz(vehCheckLogin,false);
				parDataOfAnjian.setTczdpd();
				String strpd = "O";
				if (parDataOfAnjian.getTczdpd() == BrakRollerData.PDJG_BHG) {
					logger.info("驻车判定不合格！");
					sfhg = false;
					strpd = "X";
				}
				display.sendMessage("驻车：" + decimalFormat.format(CommonUtil.MathRound1(tczdl)) + "/" + strpd,
						DeviceDisplay.SP);
			}
			
			
		}else{
			display.sendMessage(vehCheckLogin.getHphm(),DeviceDisplay.SP);
		}

		
		if(zdlh>0){
			otherInfoData.setJczczbzl(zclh);
			otherInfoData.setZdlh(zdlh);
			if (zclh != 0) {
				Float zczdl = (float) ((zdlh * 1.0 / (zclh * 0.98 * 1.0)) * 100);
				otherInfoData.setZczdl(CommonUtil.MathRound1(zczdl));
			}
			otherInfoData.setZczdlxz();
			otherInfoData.setZczdlpd();
			
			String strpd = "O";
			if (otherInfoData.getZcpd().equals(BrakRollerData.PDJG_BHG.toString())) {
				logger.info("整车判定不合格！");
				strpd="X";
				sfhg = false;
			}
			
			display.sendMessage("整车：" + decimalFormat.format(otherInfoData.getZczdl()) + "/" + strpd,
					DeviceDisplay.XP);
			Thread.sleep(2000);
		}
		
		
		if (sfhg) {
			display.sendMessage("检判定结果：O", DeviceDisplay.XP);
		} else {
			display.sendMessage("检判定结果：X", DeviceDisplay.SP);
			display.sendMessage("是否复位，等待30S", DeviceDisplay.XP);
			Thread.sleep(30 * 1000);
		}

		Thread.sleep(2000);
		display.sendMessage("请向前行驶", DeviceDisplay.XP);

		Thread.sleep(2000);
		this.display.setDefault();

		for (BrakRollerData brakRollerData : datas) {
			this.brakRollerDataRepository.save(brakRollerData);
		}
		for (VehFlow vehFlow : vehFlows) {
			VehCheckProcess process = this.vehCheckProcessRepository.getVehCheckProces(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs(), vehFlow.getJyxm());
			process.setKssj(startDate);
			process.setJssj(new Date());
			this.vehCheckProcessRepository.save(process);
		}
	}

	@Override
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 初始化制动设备
		dbp = (AbstractDeviceBrakePad) Class.forName(this.getDevice().getDeviceDecode()).newInstance();
		String temp = (String) this.getQtxxObject().get("kzsb-xsp");
		// 加载挂载设备
		if (temp != null) {
			Integer deviceid = Integer.parseInt(temp);
			display = (DeviceDisplay) servletContext.getAttribute(deviceid + "_" + Device.KEY);
		}
		dbp.init(this);

	}

}
