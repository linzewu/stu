package com.xs.jt.veh.manager;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.VehCheckLogin;

import net.sf.json.JSONObject;

public interface IVehCheckLoginManager {

	public void updateVehCheckLoginState(String jylsh);

	public VehCheckLogin repeatLogin(VehCheckLogin vehCheckLogin);

	public JSONObject vehLogin(VehCheckLogin vehCheckLogin);

	public VehCheckLogin getVehCheckLogin(String jylsh);

	public List<VehCheckLogin> getExternalC1(String hphm);

	public List<VehCheckLogin> getExternalDC(String hphm);

	public List<VehCheckLogin> getExternalR(String hphm);

	public List<VehCheckLogin> getVehCheckLoginOfSXZT(Integer zt);

	public List<VehCheckLogin> getExternalCheckVhe(String hphm);

	public Message upLine(Integer id);

	public List<VehCheckLogin> getVheInfoOfHphm(String hphm);

	public Map<String, Object> getVehChecking(Integer page, Integer rows, VehCheckLogin vehCheckLogin, Integer[] jczt);

	public boolean isLoged(VehCheckLogin vehCheckLogin);

	public JSONObject unLoginVeh(Integer id) throws RemoteException, UnsupportedEncodingException, DocumentException;

}
