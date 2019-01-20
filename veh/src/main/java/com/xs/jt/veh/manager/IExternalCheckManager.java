package com.xs.jt.veh.manager;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.ExternalCheck;
import com.xs.jt.veh.entity.VehCheckLogin;

public interface IExternalCheckManager {

	/**
	 * 保存外检测信息
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheck(ExternalCheck externalCheck) throws InterruptedException;

	/**
	 * 动态底盘
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheckDC(ExternalCheck externalCheck) throws InterruptedException;

	/**
	 * 动态底盘
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheckC1(ExternalCheck externalCheck) throws InterruptedException;

	public void createExternalCheck(VehCheckLogin vehCheckLogin);

}
