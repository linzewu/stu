package com.xs.jt.veh.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.network.SpeedDataRepository;
import com.xs.jt.veh.manager.ISpeedDataManager;

@Service("speedDataManager")
public class SpeedDataManagerImpl implements ISpeedDataManager {

	@Autowired
	private SpeedDataRepository speedDataRepository;

	@Override
	public Integer getDxjccs(String jylsh, String jyxm) {
		Integer dxcs = speedDataRepository.getDxjccs(jylsh, jyxm);
		if (dxcs != null) {
			return dxcs + 1;
		} else {
			return 1;
		}
	}

}
