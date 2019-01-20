package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.RecordInfoOfCheckRepository;
import com.xs.jt.veh.entity.RecordInfoOfCheck;
import com.xs.jt.veh.manager.IRecordInfoOfCheckManager;

@Service("recordInfoOfCheckManager")
public class RecordInfoOfCheckManagerImpl implements IRecordInfoOfCheckManager {
	@Autowired
	private RecordInfoOfCheckRepository recordInfoOfCheckRepository;

	@Override
	public RecordInfoOfCheck getRecordInfoOfCheckInfo() {
		List<RecordInfoOfCheck> list = recordInfoOfCheckRepository.findAll();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return new RecordInfoOfCheck();
	}

	@Override
	public void deleteRecordInfo() {
		recordInfoOfCheckRepository.deleteAll();
	}

	@Override
	public void saveRecordInfoOfCheckInfo(RecordInfoOfCheck recordInfoOfCheck) {
		recordInfoOfCheckRepository.save(recordInfoOfCheck);
	}

}
