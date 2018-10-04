package com.xs.jt.cms.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.zhpt.dao.PDAServiceRepository;
@Service("pDAServiceManager")
public class PDAServiceManagerImpl implements IPDAServiceManager {
	
	@Autowired
	private PDAServiceRepository pDAServiceRepository;

	public List<Map<String,Object>> findPcbStVehicle(String bh) {
		
		return pDAServiceRepository.findPcbStVehicle(bh);
	}

	public List<Map<String, Object>> findGongGaoListbyCLXH(String clxh) {
		return pDAServiceRepository.findGongGaoListbyCLXH(clxh);
	}

	public List<Map<String, Object>> getListOfDPGG(String dpid) {
		return pDAServiceRepository.getListOfDPGG(dpid);
	}

	public List<Map<String, Object>> getDPGG(String bh) {
		return pDAServiceRepository.getDPGG(bh);
	}

}
