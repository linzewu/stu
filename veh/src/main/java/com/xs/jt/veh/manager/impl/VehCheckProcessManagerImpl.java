package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.manager.IVehCheckProcessManager;

@Service("vehCheckProcessManager")
public class VehCheckProcessManagerImpl implements IVehCheckProcessManager {
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;

	@Override
	public List<VehCheckProcess> getRoadProcess(String jylsh) {
		List<VehCheckProcess> datas = vehCheckProcessRepository.getVehCheckProcesByJylshAndInJyxm(jylsh);

		return datas;
	}

	@Override
	public void updateProcess(VehCheckProcess vehCheckProcess) {
		vehCheckProcessRepository.save(vehCheckProcess);
	}

	@Override
	public VehCheckProcess getVehCheckProces(String jylsh, Integer jycs, String jyxm) {
		return vehCheckProcessRepository.getVehCheckProces(jylsh, jycs, jyxm);
	}

	@Override
	public List<VehCheckProcess> getVehCheckPrcoessByJylsh(String jylsh) {

		return vehCheckProcessRepository.getVehCheckProcesByJylshOrderByJylsh(jylsh);
	}

}
