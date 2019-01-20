package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.VehFlow;

public interface IVehFlowManager {

	public List<VehFlow> addVehFlow(VehCheckLogin vcl, List<VehCheckProcess> process, Flow flow);

	public VehFlow getNextFlow(VehFlow vehFlow);

}
