package com.xs.jt.veh.manager.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.dao.network.CurbWeightDataRepository;
import com.xs.jt.veh.dao.network.OtherInfoDataRepository;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.network.CurbWeightData;
import com.xs.jt.veh.entity.network.OtherInfoData;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.ICurbWeightDataManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;
import com.xs.jt.veh.manager.IVehCheckProcessManager;

@Service("curbWeightDataManager")
public class CurbWeightDataManagerImpl implements ICurbWeightDataManager {

	@Value("${jyjgbh}")
	private String jyjgbh;

	@Autowired
	private CurbWeightDataRepository curbWeightDataRepository;
	@Autowired
	private IVehCheckProcessManager vehCheckProcessManager;
	@Autowired
	private ICheckEventManager checkEventManager;
	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;
	@Autowired
	private OtherInfoDataRepository otherInfoDataRepository;
	@Autowired
	private IVehCheckLoginManager vehCheckLoginManager;

	@Override
	public CurbWeightData getLastCurbWeightDataOfJylsh(String jylsh) {
		List<CurbWeightData> datas = curbWeightDataRepository.getLastCurbWeightDataOfJylsh(jylsh);

		if (!datas.isEmpty()) {
			return datas.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存整备质量
	 */
	@Override
	public void saveCurbWeight(CurbWeightData curbWeight) throws InterruptedException {

		VehCheckLogin vehCheckLogin = vehCheckLoginRepository.getVehCheckLoginByJylsh(jyjgbh, curbWeight.getJylsh());
		vehCheckLogin.setVehzbzlzt(VehCheckLogin.ZT_JYJS);

		VehCheckProcess vehCheckProcess = vehCheckProcessManager.getVehCheckProces(vehCheckLogin.getJylsh(),
				vehCheckLogin.getJycs(), "Z1");

		vehCheckProcess.setJssj(new Date());

		this.vehCheckLoginRepository.save(vehCheckLogin);
		this.vehCheckProcessRepository.save(vehCheckProcess);
		this.curbWeightDataRepository.save(curbWeight);

		checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C81", "Z1",
				vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
				vehCheckLogin.getVehcsbj());
		Thread.sleep(1000);
		checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C58", "Z1",
				vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
				vehCheckLogin.getVehcsbj());

		List<OtherInfoData> otherInfoDatas = otherInfoDataRepository.findOtherInfoDataByJylsh(vehCheckLogin.getJylsh());

		if (!otherInfoDatas.isEmpty()) {
			OtherInfoData other = otherInfoDatas.get(0);
			other.setJczczbzl(curbWeight.getZbzl());
			other.setZbzlpd(curbWeight.getZbzlpd());
			other.setBzzczbzl(vehCheckLogin.getZbzl());
			DecimalFormat df = new DecimalFormat(".#");
			double bfb = (Math.abs(curbWeight.getZbzl() - other.getBzzczbzl()) / (other.getBzzczbzl() * 1.0)) * 100;
			other.setZczbzlbfb(Float.valueOf(df.format(bfb)));
			this.otherInfoDataRepository.save(other);
		}

		// 判断项目的状态
		vehCheckLoginManager.updateVehCheckLoginState(vehCheckLogin.getJylsh());

	}

}
