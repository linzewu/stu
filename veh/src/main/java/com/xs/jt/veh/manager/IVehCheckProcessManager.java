package com.xs.jt.veh.manager;

import java.util.List;

import com.xs.jt.veh.entity.VehCheckProcess;

public interface IVehCheckProcessManager {

	public List<VehCheckProcess> getRoadProcess(String jylsh);

	public void updateProcess(VehCheckProcess vehCheckProcess);

	public VehCheckProcess getVehCheckProces(String jylsh, Integer jycs, String jyxm);

	/**
	 * 通过检验流水查询检验项目
	 * 
	 * @param jylsh
	 * @return
	 */
	public List<VehCheckProcess> getVehCheckPrcoessByJylsh(String jylsh);

}
