package com.xs.jt.veh.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.network.SideslipDataRepository;
import com.xs.jt.veh.manager.ISideslipDataManager;

@Service("sideslipDataManager")
public class SideslipDataManagerImpl implements ISideslipDataManager {

	@Autowired
	private SideslipDataRepository sideslipDataRepository;

	@Override
	public Integer getDxjccs(String jylsh, String jyxm) {
		Integer dxcs = sideslipDataRepository.getDxjccs(jylsh, jyxm);
		if (dxcs != null) {
			return dxcs + 1;
		} else {
			return 1;
		}
	}

}
