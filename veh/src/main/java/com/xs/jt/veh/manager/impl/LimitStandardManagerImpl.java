package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.LimitStandardRepository;
import com.xs.jt.veh.entity.LimitStandard;
import com.xs.jt.veh.manager.ILimitStandardManager;

@Service("limitStandardManager")
public class LimitStandardManagerImpl implements ILimitStandardManager {

	@Autowired
	private LimitStandardRepository limitStandardRepository;

	@Override
	public List<LimitStandard> getAllLimitStandard() {

		return limitStandardRepository.findAll();
	}

}
