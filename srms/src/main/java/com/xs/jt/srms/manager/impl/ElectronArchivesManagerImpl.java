package com.xs.jt.srms.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.srms.manager.IElectronArchivesManager;
import com.xs.jt.srms.pt.dao.VehCarInfoRepository;
@Service
public class ElectronArchivesManagerImpl implements IElectronArchivesManager {
	
	@Autowired
	private VehCarInfoRepository vehCarInfoRepository;

	@Override
	public Map<String, Object> getVehCarInfosList(Integer page, Integer rows, Map param) {
		List<Map<String,Object>> list = this.vehCarInfoRepository.getVehCarInfosByLsh(page, rows, param);
		
		int count = this.vehCarInfoRepository.getVehCarInfoCount(param);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", count);
		return data;
	}

}
