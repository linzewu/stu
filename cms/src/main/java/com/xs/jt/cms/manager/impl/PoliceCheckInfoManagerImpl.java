package com.xs.jt.cms.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.PoliceCheckInfoRepository;
import com.xs.jt.cms.entity.PoliceCheckInfo;
import com.xs.jt.cms.manager.IPoliceCheckInfoManager;
@Service("policeCheckInfoManager")
public class PoliceCheckInfoManagerImpl implements IPoliceCheckInfoManager {

	@Autowired
	private PoliceCheckInfoRepository policeCheckInfoRepository;
	
	public PoliceCheckInfo save(PoliceCheckInfo policeCheckInfo) {
		// TODO Auto-generated method stub
		return policeCheckInfoRepository.save(policeCheckInfo);
	}

	public PoliceCheckInfo findPoliceCheckInfoByLsh(String lsh) {
		return policeCheckInfoRepository.findPoliceCheckInfoByLsh(lsh);
	}

}
