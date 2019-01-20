package com.xs.jt.veh.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.network.SuspensionDataRepository;
import com.xs.jt.veh.manager.ISuspensionDataManager;

@Service("suspensionDataManager")
public class SuspensionDataManagerImpl implements ISuspensionDataManager {
	@Autowired
	private SuspensionDataRepository suspensionDataRepository;

	@Override
	public Integer getDxjccs(String jylsh, String jyxm) {
		Integer dxcs = suspensionDataRepository.getDxjccs(jylsh, jyxm);
		if (dxcs != null) {
			return dxcs + 1;
		} else {
			return 1;
		}
	}

}
