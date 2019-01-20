package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.RecordInfoOfJcxRepository;
import com.xs.jt.veh.entity.RecordInfoOfJcx;
import com.xs.jt.veh.manager.IRecordInfoOfJcxManager;

@Service("recordInfoOfJcxManager")
public class RecordInfoOfJcxManagerImpl implements IRecordInfoOfJcxManager {

	@Autowired
	private RecordInfoOfJcxRepository recordInfoOfJcxRepository;

	@Override
	public RecordInfoOfJcx getRecordInfoOfJcx() {
		List<RecordInfoOfJcx> list = recordInfoOfJcxRepository.findAll();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return new RecordInfoOfJcx();
	}

	@Override
	public void deleteRecordInfo() {
		recordInfoOfJcxRepository.deleteAll();

	}

	@Override
	public void saveRecordInfoOfJcx(RecordInfoOfJcx recordInfoOfJcx) {
		recordInfoOfJcxRepository.save(recordInfoOfJcx);

	}

}
