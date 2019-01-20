package com.xs.jt.veh.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.DeviceCheckJudegRepository;
import com.xs.jt.veh.dao.ExternalCheckJudgeRepository;
import com.xs.jt.veh.dao.RoadCheckRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.network.BrakRollerDataRepository;
import com.xs.jt.veh.dao.network.CurbWeightDataRepository;
import com.xs.jt.veh.dao.network.LightDataRepository;
import com.xs.jt.veh.dao.network.OutlineRepository;
import com.xs.jt.veh.dao.network.ParDataOfAnjianRepository;
import com.xs.jt.veh.dao.network.SideslipDataRepository;
import com.xs.jt.veh.dao.network.SpeedDataRepository;
import com.xs.jt.veh.entity.DeviceCheckJudeg;
import com.xs.jt.veh.entity.ExternalCheckJudge;
import com.xs.jt.veh.entity.RoadCheck;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.entity.network.CurbWeightData;
import com.xs.jt.veh.entity.network.LightData;
import com.xs.jt.veh.entity.network.OtherInfoData;
import com.xs.jt.veh.entity.network.Outline;
import com.xs.jt.veh.entity.network.ParDataOfAnjian;
import com.xs.jt.veh.entity.network.SideslipData;
import com.xs.jt.veh.entity.network.SpeedData;
import com.xs.jt.veh.manager.IReportManager;

import net.sf.json.JSONObject;

@Service("reportManager")
public class ReportManagerImpl implements IReportManager {

	@Autowired
	private LightDataRepository lightDataRepository;
	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private SpeedDataRepository speedDataRepository;
	@Autowired
	private SideslipDataRepository sideslipDataRepository;
	@Autowired
	private BrakRollerDataRepository brakRollerDataRepository;
	@Autowired
	private ParDataOfAnjianRepository parDataOfAnjianRepository;
	@Autowired
	private RoadCheckRepository roadCheckRepository;
	@Autowired
	private DeviceCheckJudegRepository deviceCheckJudegRepository;
	@Autowired
	private OutlineRepository outlineRepository;
	@Autowired
	private CurbWeightDataRepository curbWeightDataRepository;
	@Autowired
	private ExternalCheckJudgeRepository externalCheckJudgeRepository;

	@Value("${jyjgmc}")
	private String jyjgmc;

	@Override
	public Map<String, Object> getReport1(String jylsh, int jycs) {
		if (jycs == 0) {
			jycs = 1;
		}

		VehCheckLogin vehCheckLogin = null;
		List<VehCheckLogin> vclList = vehCheckLoginRepository.findByJylsh(jylsh);
		if (vclList != null && !vclList.isEmpty()) {
			vehCheckLogin = vclList.get(0);
		}

		Map<String, Object> data = new HashMap<String, Object>();

		List<LightData> lightDatas = lightDataRepository.findLightDataByJylshAndJyxm(jylsh);

		Map<String, Object> lightMapData = getLightDatasOfJycs(lightDatas, jycs);
		if (!lightMapData.isEmpty()) {
			data.putAll(lightMapData);
		}

		/*
		 * for (LightData lightData : lightDatas) { lightData.setCzpy();
		 * data.put(lightData.getJyxm() + "_" + lightData.getGx(), lightData); }
		 */
		data.put("title", jyjgmc);

		List<SpeedData> sds = speedDataRepository.findSpeedDataByJylsh(jylsh);

		if (sds != null && !sds.isEmpty()) {
			SpeedData speedData = sds.get(0);
			int speedJycs = speedData.getJycs();
			if (speedJycs > jycs) {
				speedJycs = jycs;
			}
			for (SpeedData sd : sds) {

				if (sd.getJycs() == speedJycs) {
					data.put("S1", sd);
				}
			}
		}

		List<SideslipData> sids = sideslipDataRepository.findSideslipDataByJylshAndSjztOrderById(jylsh,
				SideslipData.SJZT_ZC);
		if (sids != null && !sids.isEmpty()) {
			SideslipData sideslipData = sids.get(0);
			int sideslipJycs = sideslipData.getJycs();
			if (sideslipJycs > jycs) {
				sideslipJycs = jycs;
			}
			for (SideslipData sid : sids) {
				if (sid.getJycs() == sideslipJycs) {
					data.put("A1", sid);
				}
			}
		}

		List<BrakRollerData> brds = brakRollerDataRepository.findByJylshOrderById(jylsh);

		OtherInfoData otherData = new OtherInfoData();

		for (BrakRollerData brd : brds) {
			int zdjycs = brd.getJycs();
			if (zdjycs > jycs) {
				zdjycs = jycs;
			}

			if (!brd.getJyxm().equals("B0")) {
				String key = "ZD_" + brd.getJyxm();
				if (data.get(key) == null && zdjycs == brd.getJycs()) {
					data.put(key, brd);
					if (brd.getJyxm().contains("B")) {
						int zdlh = otherData.getZdlh() == null ? 0 : otherData.getZdlh();
						int zczbzl = otherData.getJczczbzl() == null ? 0 : otherData.getJczczbzl();

						otherData.setJczczbzl(zczbzl + brd.getZlh() + brd.getYlh());
						otherData.setZdlh(zdlh + brd.getZzdl() + brd.getYzdl());
					}

				}
			} else {
				String key = "ZD_" + brd.getJyxm() + "_" + brd.getZw();
				if (data.get(key) == null && zdjycs == brd.getJycs()) {
					data.put(key, brd);
				}
			}
		}

		if (otherData.getZdlh() != null) {
			otherData.setBaseInfo(vehCheckLogin);
			otherData.setZczdlxz();
			otherData.setZczdl();
			otherData.setZczdlpd();
			data.put("other", otherData);
		}

		List<ParDataOfAnjian> plist = parDataOfAnjianRepository.findParDataOfAnjianByJylsh(jylsh);
		if (plist != null && !plist.isEmpty()) {
			ParDataOfAnjian parDataOfAnjian = plist.get(0);
			int pjycs = parDataOfAnjian.getJycs();
			if (pjycs > jycs) {
				pjycs = jycs;
			}

			for (ParDataOfAnjian pdata : plist) {
				if (pdata.getJycs() == pjycs) {
					data.put("par", pdata);
				}
			}
		}

		List<DeviceCheckJudeg> roadChecks = deviceCheckJudegRepository.findDeviceCheckJudegByJylshAndBz1(jylsh);

		if (roadChecks != null && !roadChecks.isEmpty()) {
			data.put("roadChecks", roadChecks);
		}

		RoadCheck lsys = roadCheckRepository.findRoadCheckByJylsh(jylsh);
		if (lsys != null) {
			data.put("lsy", lsys.getLsy());
		}

		List<Outline> outlines = outlineRepository.findOutlineByJylsh(jylsh);

		if (outlines != null && !outlines.isEmpty()) {

			Outline outline = outlines.get(0);
			int ojycs = outline.getJycs();

			if (ojycs > jycs) {
				ojycs = jycs;
			}

			for (Outline odata : outlines) {
				if (odata.getJycs() == ojycs) {
					data.put("wkcc", odata);
				}
			}
		}

		List<CurbWeightData> datas = curbWeightDataRepository.getLastCurbWeightDataOfJylsh(jylsh);

		if (!datas.isEmpty()) {
			data.put("Z1", datas.get(0));
		}

		return data;
	}

	/**
	 * 根据建议次数获取灯光数据
	 * 
	 * @param lightDatas
	 * @param jycs
	 * @return
	 */
	private Map<String, Object> getLightDatasOfJycs(List<LightData> lightDatas, int jycs) {
		Map<String, Object> datas = new HashMap<String, Object>();
		if (lightDatas.isEmpty()) {
			return datas;
		}

		LightData lightData = lightDatas.get(lightDatas.size() - 1);

		if (lightData.getJycs() <= jycs) {
			jycs = lightData.getJycs();
		}

		for (LightData data : lightDatas) {

			if (jycs == data.getJycs()) {
				String key = data.getJyxm() + "_" + data.getGx();
				datas.put(key, data);
			}
		}

		return datas;
	}

	@Override
	public String getReport4(String jylsh) {
		List<BrakRollerData> report4 = brakRollerDataRepository.findByJylshAndNotJyxmOrderById(jylsh, "B0");

		if (report4 != null && !report4.isEmpty()) {
			// Integer jycs = report4.get(0).getJycs();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (BrakRollerData brakRollerData : report4) {
				Integer jycs = brakRollerData.getJycs();
				String jyxm = brakRollerData.getJyxm();
				String key = jycs + "_" + jyxm;

				if (tempMap.get(key) == null) {
					tempMap.put(key, brakRollerData);
				}
			}
		}

		List<SideslipData> sides = sideslipDataRepository.findSideslipDataByJylshOrderById(jylsh);

		if (sides != null && !sides.isEmpty()) {
			Integer jycs = sides.get(0).getJycs();
			for (SideslipData side : sides) {
				if (side.getJycs() != jycs) {
					sides.remove(side);
				}
			}
		}

		JSONObject jo = new JSONObject();

		jo.put("zd", report4);

		jo.put("ch", sides);

		return jo.toString();
	}

	@Override
	public Map<String, List> getReport2(String jylsh) {
		List<DeviceCheckJudeg> deviceCheckJudegs = deviceCheckJudegRepository.findDeviceCheckJudegByJylsh(jylsh);

		List<ExternalCheckJudge> externalCheckJudges = externalCheckJudgeRepository
				.findExternalCheckJudgeByJylsh(jylsh);

		Map<String, List> data = new HashMap<String, List>();

		data.put("yqsbjyjg", deviceCheckJudegs);
		data.put("rgjyjg", externalCheckJudges);

		return data;
	}

}
