package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.dao.DeviceCheckJudegRepository;
import com.xs.jt.veh.dao.RoadCheckRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.entity.DeviceCheckJudeg;
import com.xs.jt.veh.entity.RoadCheck;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.IDeviceCheckJudegManager;
import com.xs.jt.veh.manager.IRoadCheckManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;

@Service("roadCheckManager")
public class RoadCheckManagerImpl implements IRoadCheckManager {

	private Log logger = LogFactory.getLog(RoadCheckManagerImpl.class);

	@Autowired
	private RoadCheckRepository roadCheckRepository;
	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private DeviceCheckJudegRepository deviceCheckJudegRepository;
	@Autowired
	private IDeviceCheckJudegManager deviceCheckJudegManager;
	@Autowired
	private ICheckEventManager checkEventManager;
	@Autowired
	private IVehCheckLoginManager vehCheckLoginManager;

	@Override
	public RoadCheck getRoadCheck(String jylsh) {
		// TODO Auto-generated method stub
		return roadCheckRepository.findRoadCheckByJylsh(jylsh);
	}

	@Override
	public Message saveRoadCheck(RoadCheck roadCheck) throws InterruptedException {
		String jylsh = roadCheck.getJylsh();
		Message message = new Message();
		List<VehCheckLogin> datas = vehCheckLoginRepository.findByJylsh(jylsh);
		if (datas == null || datas.isEmpty()) {
			message.setState("2");
			message.setMessage("路试车辆不存在！");
			return message;
		}

		boolean createJudegFlag = false;

		if (roadCheck.getId() == null) {
			createJudegFlag = true;
		}

		Long xh = deviceCheckJudegRepository.findCountByJylsh(roadCheck.getJylsh()).longValue();
		xh++;
		VehCheckLogin vehCheckLogin = datas.get(0);
		String cllx = vehCheckLogin.getCllx();
		Integer zzl = vehCheckLogin.getZzl();
		String zzly = vehCheckLogin.getZzly();

		// 路试初速度
		roadCheck.setLscsdxz(cllx, zzl);
		roadCheck.setLscsdpd();
		// 协调时间
		roadCheck.setLsxtsjxz(zzly);
		;
		roadCheck.setLsxtsjpd();

		// 行车空载制动距离
		roadCheck.setLskzzdjlxz(cllx, zzl);
		roadCheck.setLskzzdjlpd();

		// 行车空载制动距离
		roadCheck.setLskzmfddxz(cllx, zzl);
		roadCheck.setLskzmfddpd();

		roadCheck.setCsbpd();
		// 驻车制动
		DeviceCheckJudeg deviceCheckJudegZcpd = new DeviceCheckJudeg();
		setDeviceCheckJudeg(deviceCheckJudegZcpd, vehCheckLogin);

		// 行车路试制动判定
		if (roadCheck.getLscsdpd() == new Integer(2) || roadCheck.getLskzzdjlpd() == new Integer(2)
				|| roadCheck.getLskzmfddpd() == new Integer(2)) {
			roadCheck.setLszdpd(2);
		} else {
			roadCheck.setLszdpd(1);
		}

		logger.info("路试结论：" + (roadCheck.getLszdpd() == new Integer(2)));
		logger.info("路试结论：" + (roadCheck.getLszczdpd() == new Integer(2)));
		logger.info("路试结论：" + ((roadCheck.getCsbpd() != null && roadCheck.getCsbpd() == new Integer(2))));

		if (roadCheck.getLszdpd() == new Integer(2) || roadCheck.getLszczdpd() == new Integer(2)
				|| (roadCheck.getCsbpd() != null && roadCheck.getCsbpd() == new Integer(2))) {
			roadCheck.setLsjg(2);
		} else {
			roadCheck.setLsjg(1);
			logger.info("设置路试结论：" + roadCheck.getLsjg());
		}
		this.roadCheckRepository.save(roadCheck);
		vehCheckLogin.setVehlszt(VehCheckLogin.ZT_JYJS);
		if (createJudegFlag) {
			deviceCheckJudegManager.createRoadCheckJudeg(vehCheckLogin, xh.intValue());
		}

		String jyxm = "";

		if (vehCheckLogin.getJyxm().indexOf("R1") >= 0) {
			jyxm += ",R1";
		}

		if (vehCheckLogin.getJyxm().indexOf("R2") >= 0) {
			jyxm += ",R2";
		}

		if (vehCheckLogin.getJyxm().indexOf("R3") >= 0) {
			jyxm += ",R3";
		}

		jyxm = jyxm.substring(1, jyxm.length());
		checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C55_R", "R", vehCheckLogin.getHphm(),
				vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());

		checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C54", jyxm, vehCheckLogin.getHphm(),
				vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());

		if (vehCheckLogin.getJyxm().indexOf("R1") >= 0) {
			checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C58", "R1", vehCheckLogin.getHphm(),
					vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());
		}

		if (vehCheckLogin.getJyxm().indexOf("R2") >= 0) {
			checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C58", "R2", vehCheckLogin.getHphm(),
					vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());
		}

		if (vehCheckLogin.getJyxm().indexOf("R3") >= 0) {
			checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C58", "R3", vehCheckLogin.getHphm(),
					vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());
		}

		checkEventManager.createEvent(jylsh, vehCheckLogin.getJycs(), "18C58_R", "R", vehCheckLogin.getHphm(),
				vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());

		vehCheckLoginManager.updateVehCheckLoginState(jylsh);
		message.setState(Message.STATE_SUCCESS);
		message.setMessage("保存成功！");
		return message;
	}

	private void setDeviceCheckJudeg(DeviceCheckJudeg deviceCheckJudeg, VehCheckLogin vehCheckLogin) {
		deviceCheckJudeg.setJylsh(vehCheckLogin.getJylsh());
		deviceCheckJudeg.setHphm(vehCheckLogin.getHphm());
		deviceCheckJudeg.setHpzl(vehCheckLogin.getHpzl());
		deviceCheckJudeg.setJycs(vehCheckLogin.getJycs());
		deviceCheckJudeg.setJyjgbh(vehCheckLogin.getJyjgbh());
	}

}
