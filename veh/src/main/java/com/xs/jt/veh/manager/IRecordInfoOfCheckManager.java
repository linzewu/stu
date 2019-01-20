package com.xs.jt.veh.manager;

import com.xs.jt.veh.entity.RecordInfoOfCheck;

public interface IRecordInfoOfCheckManager {

	public RecordInfoOfCheck getRecordInfoOfCheckInfo();

	public void deleteRecordInfo();

	public void saveRecordInfoOfCheckInfo(RecordInfoOfCheck recordInfoOfCheck);

}
