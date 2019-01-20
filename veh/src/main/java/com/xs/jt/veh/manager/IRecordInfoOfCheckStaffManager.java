package com.xs.jt.veh.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.veh.entity.RecordInfoOfCheckStaff;

public interface IRecordInfoOfCheckStaffManager {

	public Map<String, Object> getRecordInfoOfCheckStaff(Integer page, Integer rows,
			final RecordInfoOfCheckStaff recordInfoOfCheckStaff);

	public void deleteRecordInfo();

	public void saveRecordInfoOfCheckStaff(List<RecordInfoOfCheckStaff> list);

}
