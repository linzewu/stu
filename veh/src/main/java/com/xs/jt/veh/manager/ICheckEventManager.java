package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.veh.entity.CheckEvents;

public interface ICheckEventManager {

	public List<?> getEvents();

	public void createEvent(String jylsh, Integer jycs, String event, String jyxm, String hphm, String hpzl,
			String clsbdh, Integer csbj);

	public void createEvent(String jylsh, Integer jycs, String event, String jyxm, String hphm, String hpzl,
			String clsbdh, String zpzl, Integer csbj);

	public void resetEventState(String jylsh);
	
	public void deleteCheckEvents(CheckEvents checkEvent);

	public void saveCheckEvents(CheckEvents checkEvent);

}
