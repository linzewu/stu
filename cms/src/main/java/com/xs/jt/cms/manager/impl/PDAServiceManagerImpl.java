package com.xs.jt.cms.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	public List<Map<String, Object>> getImplCarParam(String clxh) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 List<Map<String, Object>> list =  pDAServiceRepository.getImplCarParam(clxh);
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		//key 转小写
		for(Object o:list){
			Map<String,Object> m=(Map)o;
			Map<String,Object> returnMap=new HashMap<String,Object>();
			for(String key: m.keySet()){
				if("GGSXRQ".equals(key)||"TZSCRQ".equals(key)){
					Date date =  (Date)m.get(key);
					if(date!=null){
						returnMap.put(key.toLowerCase(),sdf.format((date)));
					}else{
						returnMap.put(key.toLowerCase(),null);
					}
				}else{
					returnMap.put(key.toLowerCase(),m.get(key));
				}
			}
			returnList.add(returnMap);
		}
		return returnList;
	}

}
