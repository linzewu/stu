package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.WorkPoint;

public interface IWorkPointManager {

	public List<WorkPoint> getWorkPointsByJcxdh(Integer jcxdh);

	public List<WorkPoint> getWorkPoints();

	public WorkPoint saveWorkPoint(WorkPoint workPoint);

	public void deleteWorkPoint(WorkPoint workPoint);

	public Message startWorkpoint(Integer id);

	public Message stopWorkpoint(Integer id) throws InterruptedException;

	public Message reStartWorkpoint(Integer id) throws InterruptedException;

}
