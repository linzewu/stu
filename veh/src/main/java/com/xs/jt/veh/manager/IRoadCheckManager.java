package com.xs.jt.veh.manager;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.RoadCheck;

public interface IRoadCheckManager {

	public RoadCheck getRoadCheck(String jylsh);

	public Message saveRoadCheck(RoadCheck roadCheck) throws InterruptedException;

}
