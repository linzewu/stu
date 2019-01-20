package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.veh.entity.network.Outline;

public interface IOutlineManager {
	
	public List<Outline> getOutlineOfReportFlag();
	
	public void updateOutline(Outline outline);

}
