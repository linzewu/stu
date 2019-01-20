package com.xs.jt.veh.manager;

import com.xs.jt.veh.entity.network.CurbWeightData;

public interface ICurbWeightDataManager {

	public CurbWeightData getLastCurbWeightDataOfJylsh(String jylsh);

	public void saveCurbWeight(CurbWeightData curbWeight) throws InterruptedException;

}
