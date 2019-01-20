package com.xs.jt.veh.manager;

import com.xs.jt.veh.entity.Insurance;

public interface IInsuranceManager {

	public void saveInsurance(Insurance insurance);

	public Insurance getInsurance(String jylsh);

}
