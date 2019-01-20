package com.xs.jt.veh.manager;

import com.xs.jt.veh.entity.RecordInfoOfJcx;

public interface IRecordInfoOfJcxManager {

	public RecordInfoOfJcx getRecordInfoOfJcx();

	public void deleteRecordInfo();

	public void saveRecordInfoOfJcx(RecordInfoOfJcx recordInfoOfJcx);

}
