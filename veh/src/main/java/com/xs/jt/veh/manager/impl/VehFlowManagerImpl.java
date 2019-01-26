package com.xs.jt.veh.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.veh.common.VheFlowComparator;
import com.xs.jt.veh.dao.DeviceRepository;
import com.xs.jt.veh.dao.VehFlowRepository;
import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.manager.IVehFlowManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("vehFlowManager")
public class VehFlowManagerImpl implements IVehFlowManager {
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private VehFlowRepository vehFlowRepository;
	@Autowired
	private IBaseParamsManager baseParamsManager;

	@Override
	public List<VehFlow> addVehFlow(VehCheckLogin vcl, List<VehCheckProcess> process, Flow flow) {
		JSONArray flowJsons = JSONArray.fromObject(flow.getFlow());
		List<VehFlow> vehFlows = new ArrayList<VehFlow>();

		String allJyxm = vcl.getJyxm();

		if (vcl.getJycs() > 1) {
			allJyxm = vcl.getFjjyxm();
		}

		for (int i = 0; i < flowJsons.size(); i++) {

			JSONObject workPoint = flowJsons.getJSONObject(i);

			Integer gwid = workPoint.getInt("id");

			JSONArray sbs = workPoint.getJSONArray("sbs");

			for (int j = 0; j < sbs.size(); j++) {

				JSONObject sb = sbs.getJSONObject(j);

				Integer deviceId = sb.getInt("id");

				Optional<Device> optDev = deviceRepository.findById(deviceId);
				//

				// 如果复检，则不需要重新称重
				/*
				 * if(device.getType()== Device.CZJCSB && vcl.getJycs()==1) { continue; }
				 */

				String strJyxm = getDeivceItem(optDev, process);

				if (deviceId == -1 && allJyxm.indexOf("C1") != -1) {
					VehFlow v = new VehFlow();
					v.setGw(gwid);
					v.setHphm(vcl.getHphm());
					v.setHpzl(vcl.getHpzl());
					v.setJylsh(vcl.getJylsh());
					v.setJycs(vcl.getJycs());
					v.setJyxm("C1");
					v.setJysb(-1);
					v.setGwsx(i + 1);
					v.setSbsx(j + 1);
					v.setSbid(deviceId);
					vehFlows.add(v);
				}

				if (strJyxm != null && !strJyxm.equals("")) {
					Device device = optDev.get();
					String[] jyxmArray = strJyxm.split(",");
					for (String jyxm : jyxmArray) {
						// 如果是驻车制动 需要根据驻车轴为来生成
						if (jyxm.equals("B0")) {
							if (device.getType() == Device.ZDJCSB || device.getType() == Device.ZDPBSB) {
								String zczw = vcl.getZczw();
								for (int k = 0; k < zczw.length(); k++) {
									VehFlow v = new VehFlow();
									v.setGw(gwid);
									v.setHphm(vcl.getHphm());
									v.setHpzl(vcl.getHpzl());
									v.setJylsh(vcl.getJylsh());
									v.setJycs(vcl.getJycs());
									v.setJyxm(jyxm);
									v.setJysb(device.getId());
									v.setGwsx(i + 1);
									v.setSbsx(j + 1);
									v.setMemo(String.valueOf(zczw.charAt(k)));
									v.setSbid(deviceId);
									vehFlows.add(v);
								}
							}
						} else {
							VehFlow v = new VehFlow();
							v.setGw(gwid);
							v.setHphm(vcl.getHphm());
							v.setHpzl(vcl.getHpzl());
							v.setJylsh(vcl.getJylsh());
							v.setJycs(vcl.getJycs());
							v.setJyxm(jyxm);
							v.setJysb(device.getId());
							v.setGwsx(i + 1);
							v.setSbsx(j + 1);
							v.setSbid(deviceId);
							vehFlows.add(v);
						}
					}
				}
			}
		}

		VheFlowComparator comparator = new VheFlowComparator(flow);

		Collections.sort(vehFlows, comparator);

		int i = 1;

		for (VehFlow vehFlow : vehFlows) {
			vehFlow.setSx(i);
			vehFlowRepository.save(vehFlow);
			i++;
		}
		return vehFlows;
	}

	private String getDeivceItem(Optional<Device> optDev, List<VehCheckProcess> vehCheckProcessArray) {

		if ((!optDev.isPresent()) || vehCheckProcessArray == null) {
			return null;
		}
		Device device = optDev.get();
		BaseParams param = baseParamsManager.getBaseParamByValue("deviceType", device.getType().toString());

		String strConfig = param.getMemo();

		String[] config = strConfig.split(",");

		String jyxm = "";

		for (VehCheckProcess process : vehCheckProcessArray) {
			for (String item : config) {
				if (item.equals(process.getJyxm())) {
					jyxm += process.getJyxm() + ",";
				}
			}
		}

		if (jyxm.length() > 0) {
			jyxm = jyxm.substring(0, jyxm.length() - 1);
		}

		return jyxm;
	}

	@Override
	public VehFlow getNextFlow(VehFlow vehFlow) {
		List<VehFlow> vehFlows = vehFlowRepository.findByJylshAndJycsAndSx(vehFlow.getJylsh(), vehFlow.getJycs(),
				vehFlow.getSx() + 1);
		if (vehFlows == null || vehFlows.isEmpty()) {
			return null;
		} else {
			return vehFlows.get(0);
		}
	}

}
