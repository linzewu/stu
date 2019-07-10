package com.xs.jt.srms.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.srms.manager.IVehFlowManager;
import com.xs.jt.srms.pt.dao.CarInfoRepository;
@Service
public class VehFlowManagerImpl implements IVehFlowManager {

	@Autowired
	private CarInfoRepository carInfoRepository;
	@Override
	public List<Map<String, Object>> getVehFlowByClsbdh(String clsbdh) {
		return carInfoRepository.getVehFlowByClsbdh(clsbdh);
	}
	@Override
	public List<Map<String, Object>> getCarInfoByLsh(String lsh) {
		
		return carInfoRepository.getCarInfoByBarcode(lsh);
	}
	
	

}
