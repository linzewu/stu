package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.InsuranceRepository;
import com.xs.jt.veh.entity.Insurance;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.IInsuranceManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;

@Service("insuranceManager")
public class InsuranceManagerImpl implements IInsuranceManager {
	@Autowired
	private InsuranceRepository insuranceRepository;
	@Autowired
	private IVehCheckLoginManager vehCheckLoginManager;
	@Autowired
	private ICheckEventManager checkEventManager;

	@Override
	public void saveInsurance(Insurance insurance) {
		this.insuranceRepository.save(insurance);

		VehCheckLogin vehCheckLogin = vehCheckLoginManager.getVehCheckLogin(insurance.getJylsh());

		checkEventManager.createEvent(insurance.getJylsh(), null, "18C61", null, insurance.getHphm(),
				insurance.getHpzl(), insurance.getClsbdh(), vehCheckLogin.getVehcsbj());
		checkEventManager.createEvent(insurance.getJylsh(), null, "18C64", null, insurance.getHphm(),
				insurance.getHpzl(), insurance.getClsbdh(), vehCheckLogin.getVehcsbj());

	}

	@Override
	public Insurance getInsurance(String jylsh) {
		List<Insurance> insurances = insuranceRepository.findInsuranceByJylsh(jylsh);
		if (insurances != null && !insurances.isEmpty()) {
			return insurances.get(0);
		}

		return null;
	}

}
