package com.xs.jt.veh.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xs.jt.veh.dao.DeviceCheckJudegRepository;
import com.xs.jt.veh.dao.RoadCheckRepository;
import com.xs.jt.veh.dao.network.BrakRollerDataRepository;
import com.xs.jt.veh.dao.network.CurbWeightDataRepository;
import com.xs.jt.veh.dao.network.LightDataRepository;
import com.xs.jt.veh.dao.network.OtherInfoDataRepository;
import com.xs.jt.veh.dao.network.OutlineRepository;
import com.xs.jt.veh.dao.network.ParDataOfAnjianRepository;
import com.xs.jt.veh.dao.network.SideslipDataRepository;
import com.xs.jt.veh.dao.network.SpeedDataRepository;
import com.xs.jt.veh.entity.DeviceCheckJudeg;
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
import com.xs.jt.veh.manager.IDeviceCheckJudegManager;
import com.xs.jt.veh.util.CommonUtil;

@Service("deviceCheckJudegManager")
public class DeviceCheckJudegManagerImpl implements IDeviceCheckJudegManager {
	@Autowired
	private OtherInfoDataRepository otherInfoDataRepository;
	@Autowired
	private ParDataOfAnjianRepository parDataOfAnjianRepository;
	@Autowired
	private OutlineRepository outlineRepository;
	@Autowired
	private DeviceCheckJudegRepository deviceCheckJudegRepository;
	@Autowired
	private RoadCheckRepository roadCheckRepository;
	@Autowired
	private BrakRollerDataRepository brakRollerDataRepository;
	@Autowired
	private SpeedDataRepository speedDataRepository;
	@Autowired
	private SideslipDataRepository sideslipDataRepository;
	@Autowired
	private LightDataRepository lightDataRepository;
	@Autowired
	private CurbWeightDataRepository curbWeightDataRepository;

	@Override
	public void createDeviceCheckJudeg(VehCheckLogin vehCheckLogin) {

		Map<String, Object> flagMap = new HashMap<String, Object>();

		List<OtherInfoData> otherInfoDatas = otherInfoDataRepository.findOtherInfoDataByJylsh(vehCheckLogin.getJylsh());

		List<ParDataOfAnjian> parDataOfAnjians = parDataOfAnjianRepository
				.findParDataOfAnjianByJylsh(vehCheckLogin.getJylsh());

		List<Outline> outlines = outlineRepository.findOutlineByJylsh(vehCheckLogin.getJylsh());

		OtherInfoData otherInfoData = null;
		ParDataOfAnjian parDataOfAnjian = null;

		Outline outline = null;

		if (otherInfoDatas != null && !otherInfoDatas.isEmpty()) {
			otherInfoData = (OtherInfoData) otherInfoDatas.get(0);
		}

		if (parDataOfAnjians != null && !parDataOfAnjians.isEmpty()) {
			parDataOfAnjian = (ParDataOfAnjian) parDataOfAnjians.get(0);
		}

		if (outlines != null && !outlines.isEmpty()) {
			outline = (Outline) outlines.get(0);
		}

		Integer xh = 1;

		// 清空报告
		deviceCheckJudegRepository.delDeviceCheckJudegByJylshAndJyjgbh(vehCheckLogin.getJylsh(),
				vehCheckLogin.getJyjgbh());

		// 路试数据
		xh = createRoadCheckJudeg(vehCheckLogin, xh);

		// 制动数据判定
		xh = createBrakRollerDateJudeg(vehCheckLogin, flagMap, xh);

		// 驻车制动率判定
		if (parDataOfAnjian != null) {
			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setYqjyxm("驻车制动率(%)");
			dcj1.setYqjyjg(parDataOfAnjian.getTczdl() == null ? "" : parDataOfAnjian.getTczdl().toString());
			dcj1.setYqbzxz(parDataOfAnjian.getTczdxz() == null ? "" : "≥" + parDataOfAnjian.getTczdxz());
			dcj1.setYqjgpd(parDataOfAnjian.getTczdpd() == null ? "" : parDataOfAnjian.getTczdpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}
		// 整车制动率判定
		if (otherInfoData != null) {
			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setYqjyxm("整车制动率(%)");
			dcj1.setYqjyjg(otherInfoData.getZczdl() == null ? "" : otherInfoData.getZczdl().toString());
			dcj1.setYqbzxz(otherInfoData.getZczdlxz() == null ? "" : "≥" + otherInfoData.getZczdlxz());
			dcj1.setYqjgpd(otherInfoData.getZcpd() == null ? "" : otherInfoData.getZcpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}
		// 灯光数据判定
		xh = createLightDataJudeg(vehCheckLogin, flagMap, xh);

		// 侧滑报告判定
		List<SideslipData> sideslipDatas = sideslipDataRepository
				.findSideslipDataByJylshAndSjzt(vehCheckLogin.getJylsh(), SideslipData.SJZT_ZC);

		if (sideslipDatas != null && !sideslipDatas.isEmpty()) {
			SideslipData sideslipData = sideslipDatas.get(0);
			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setYqjyxm("转向轮横向侧滑值(m/km)");
			dcj1.setYqjyjg(sideslipData.getSideslip() == null ? "" : sideslipData.getSideslip().toString());
			dcj1.setYqbzxz(sideslipData.getChxz().replace(",", "~"));
			dcj1.setYqjgpd(sideslipData.getChpd() == null ? "" : sideslipData.getChpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}

		// 速度报告判定
		List<SpeedData> speedDatas = speedDataRepository.findSpeedDataByJylshAndSjzt(vehCheckLogin.getJylsh(),
				SideslipData.SJZT_ZC);

		if (speedDatas != null && !speedDatas.isEmpty()) {
			SpeedData speedData = speedDatas.get(0);
			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setYqjyxm("车速表指示误差(km/h)");
			dcj1.setYqjyjg(speedData.getSpeed() == null ? "" : speedData.getSpeed().toString());
			dcj1.setYqbzxz(speedData.getSdxz().replace(",", "~"));
			dcj1.setYqjgpd(speedData.getSdpd() == null ? "" : speedData.getSdpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}

		CurbWeightData curbWeightData = null;
		List<CurbWeightData> cwdList = this.curbWeightDataRepository
				.getLastCurbWeightDataOfJylsh(vehCheckLogin.getJylsh());
		if (!cwdList.isEmpty()) {
			curbWeightData = cwdList.get(0);
		}
		if (vehCheckLogin.getJylb().equals("00") && curbWeightData != null
				&& vehCheckLogin.getJyxm().indexOf("Z1") >= 0) {
			String cllx = vehCheckLogin.getCllx();
			int xzgj = 100;
			String temp1 = "±3%或±";
			if (cllx.indexOf("H1") == 0 || cllx.indexOf("H2") == 0 || cllx.indexOf("Z1") == 0 || cllx.indexOf("Z2") == 0
					|| cllx.indexOf("Z5") == 0 || cllx.indexOf("G") == 0 || cllx.indexOf("B") == 0) {
				xzgj = 500;
			} else if (cllx.indexOf("H3") == 0 || cllx.indexOf("H4") == 0 || cllx.indexOf("Z3") == 0
					|| cllx.indexOf("Z4") == 0) {
				xzgj = 100;
			} else if (cllx.indexOf("N") == 0) {
				xzgj = 100;
				temp1 = "±5%或±";
			} else if (cllx.indexOf("M") == 0) {
				xzgj = 10;
			}

			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setXh(xh);
			dcj1.setYqjyxm("整备质量(KG)");
			dcj1.setYqjyjg(curbWeightData.getZbzl() == null ? "" : curbWeightData.getZbzl().toString());
			dcj1.setYqbzxz(temp1 + xzgj + "KG");
			dcj1.setYqjgpd(curbWeightData.getZbzlpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}

		// 外廓尺寸测量报告
		if (outline != null) {
			DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			dcj1.setXh(xh);
			dcj1.setYqjyxm("外廓尺寸(mmxmmxmm)");
			dcj1.setYqjyjg(outline.getCwkc() + "x" + outline.getCwkk() + "x" + outline.getCwkg());

			if (vehCheckLogin.getJylb().equals("00")) {
				dcj1.setYqbzxz("±1%或50mm");
			} else {
				dcj1.setYqbzxz("±2%或100mm");
			}
			dcj1.setYqjgpd(outline.getClwkccpd().toString());
			dcj1.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(dcj1);
		}

	}

	@Override
	public Integer createRoadCheckJudeg(VehCheckLogin vehCheckLogin, Integer xh) {
		RoadCheck roadCheck = roadCheckRepository.findRoadCheckByJylsh(vehCheckLogin.getJylsh());

		if (roadCheck == null) {
			return xh;
		} else {

			String cllx = vehCheckLogin.getCllx();
			Integer zzl = vehCheckLogin.getZzl();
			String zzly = vehCheckLogin.getZzly();

			if (vehCheckLogin.getJyxm().indexOf("R1") >= 0) {
				// 路试初速度
				DeviceCheckJudeg deviceCheckJudegLscsd = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegLscsd, vehCheckLogin);
				deviceCheckJudegLscsd.setYqjyxm("制动初速度（km/h）");
				deviceCheckJudegLscsd.setYqjyjg(roadCheck.getZdcsd().toString());
				deviceCheckJudegLscsd.setYqjgpd(roadCheck.getLscsdpd().toString());
				deviceCheckJudegLscsd.setYqbzxz("≥" + roadCheck.getLscsdxz().toString());
				deviceCheckJudegLscsd.setXh(xh.intValue());
				deviceCheckJudegLscsd.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegLscsd);

				// 协调时间
				DeviceCheckJudeg deviceCheckJudegXtsj = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegXtsj, vehCheckLogin);
				deviceCheckJudegXtsj.setYqjyxm("制动协调时间(S)");
				deviceCheckJudegXtsj.setYqjyjg(roadCheck.getZdxtsj().toString());
				deviceCheckJudegXtsj.setYqjgpd(roadCheck.getLsxtsjpd().toString());
				deviceCheckJudegXtsj.setYqbzxz("≤" + roadCheck.getLsxtsjxz());
				deviceCheckJudegXtsj.setXh(xh.intValue());
				deviceCheckJudegXtsj.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegXtsj);

				// 制动稳定性
				String zdwdx = "不跑偏";
				if (roadCheck.getZdwdx().equals("1")) {
					zdwdx = "不跑偏";
				} else if (roadCheck.getZdwdx().equals("2")) {
					zdwdx = "左跑偏";
				} else if (roadCheck.getZdwdx().equals("3")) {
					zdwdx = "右跑偏";
				}
				DeviceCheckJudeg deviceCheckJudegZdwdx = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegZdwdx, vehCheckLogin);
				deviceCheckJudegZdwdx.setYqjyxm("制动稳定性");
				deviceCheckJudegZdwdx.setYqjyjg(zdwdx);
				deviceCheckJudegZdwdx.setYqjgpd(roadCheck.getZdwdx().equals("1") ? "1" : "2");
				deviceCheckJudegZdwdx.setYqbzxz("-");
				deviceCheckJudegZdwdx.setXh(xh.intValue());
				deviceCheckJudegZdwdx.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegZdwdx);

				// 行车空载制动距离
				DeviceCheckJudeg deviceCheckJudegKzzdjl = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegKzzdjl, vehCheckLogin);
				deviceCheckJudegKzzdjl.setYqjyxm("空载制动距离(m)");
				deviceCheckJudegKzzdjl.setYqjyjg(roadCheck.getXckzzdjl().toString());
				deviceCheckJudegKzzdjl.setYqjgpd(roadCheck.getLskzzdjlpd().toString());
				deviceCheckJudegKzzdjl.setYqbzxz("≤" + roadCheck.getLskzzdjlxz());
				deviceCheckJudegKzzdjl.setXh(xh.intValue());
				deviceCheckJudegKzzdjl.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegKzzdjl);

				// 行车空载制动距离
				DeviceCheckJudeg deviceCheckJudegKzmfdd = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegKzmfdd, vehCheckLogin);
				deviceCheckJudegKzmfdd.setYqjyxm("空载MFFDD(m)");
				deviceCheckJudegKzmfdd.setYqjyjg(roadCheck.getXckzmfdd().toString());
				deviceCheckJudegKzmfdd.setYqjgpd(roadCheck.getLskzmfddpd().toString());
				deviceCheckJudegKzmfdd.setYqbzxz("≥" + roadCheck.getLskzmfddxz());
				deviceCheckJudegKzmfdd.setXh(xh.intValue());
				deviceCheckJudegKzmfdd.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegKzmfdd);

			}

			if (vehCheckLogin.getJyxm().indexOf("R2") >= 0) {
				// 驻车制动
				DeviceCheckJudeg deviceCheckJudegZcpd = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegZcpd, vehCheckLogin);
				deviceCheckJudegZcpd.setYqjyxm("路试驻车" + (roadCheck.getZcpd() == 0 ? "20%" : "15%") + "坡道路试");
				deviceCheckJudegZcpd.setYqjyjg(roadCheck.getLszczdpd().equals("1") ? "5min未溜" : "5min内溜车");
				deviceCheckJudegZcpd.setYqjgpd(roadCheck.getLszczdpd().toString());
				deviceCheckJudegZcpd.setYqbzxz("正反5min");
				deviceCheckJudegZcpd.setXh(xh.intValue());
				deviceCheckJudegZcpd.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegZcpd);
			}

			if (vehCheckLogin.getJyxm().indexOf("R3") >= 0) {
				DeviceCheckJudeg deviceCheckJudegcsb = new DeviceCheckJudeg();
				setDeviceCheckJudeg(deviceCheckJudegcsb, vehCheckLogin);
				deviceCheckJudegcsb.setYqjyxm("路试车速表");
				deviceCheckJudegcsb.setYqjyjg(roadCheck.getCsdscz().toString());
				deviceCheckJudegcsb.setYqjgpd(roadCheck.getCsbpd().toString());
				deviceCheckJudegcsb.setYqbzxz("32.8km/h ~ 40km/h");
				deviceCheckJudegcsb.setXh(xh.intValue());
				deviceCheckJudegcsb.setBz1("R");
				xh++;
				deviceCheckJudegRepository.save(deviceCheckJudegcsb);
			}

			return xh;
		}
	}

	private void setDeviceCheckJudeg(DeviceCheckJudeg deviceCheckJudeg, VehCheckLogin vehCheckLogin) {
		deviceCheckJudeg.setJylsh(vehCheckLogin.getJylsh());
		deviceCheckJudeg.setHphm(vehCheckLogin.getHphm());
		deviceCheckJudeg.setHpzl(vehCheckLogin.getHpzl());
		deviceCheckJudeg.setJycs(vehCheckLogin.getJycs());
		deviceCheckJudeg.setJyjgbh(vehCheckLogin.getJyjgbh());
	}

	private Integer createBrakRollerDateJudeg(final VehCheckLogin vehCheckLogin, Map<String, Object> flagMap,
			Integer xh) {
		List<BrakRollerData> brds = brakRollerDataRepository.findByJylshAndNotJyxmAndSjzt(vehCheckLogin.getJylsh(),
				"B0", BrakRollerData.SJZT_ZC);

		for (BrakRollerData brd : brds) {
			if (flagMap.get(brd.getJyxm()) == null) {

				String temp = brd.getJyxm().indexOf("L") == 0 ? "加载" : "";

				DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
				dcj1.setXh(xh);
				dcj1.setYqjyxm(CommonUtil.getZWStr(brd.getZw()) + temp + "制动率(%)");
				dcj1.setYqjyjg(brd.getKzxczdl() == null ? "" : brd.getKzxczdl().toString());
				dcj1.setYqbzxz(brd.getKzzdlxz() == null ? "" : "≥" + brd.getKzzdlxz().toString());
				dcj1.setYqjgpd(brd.getKzzdlpd() == null ? "" : brd.getKzzdlpd().toString());
				dcj1.setXh(xh);
				xh++;

				deviceCheckJudegRepository.save(dcj1);

				DeviceCheckJudeg dcj2 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
				dcj2.setXh(xh);
				dcj2.setYqjyxm(CommonUtil.getZWStr(brd.getZw()) + temp + "不平衡率(%)");
				dcj2.setYqjyjg(brd.getKzbphl() == null ? "" : brd.getKzbphl().toString());
				dcj2.setYqbzxz(brd.getBphlxz() == null ? "" : "≤" + brd.getBphlxz().toString());
				dcj2.setYqjgpd(brd.getKzbphlpd() == null ? "" : brd.getKzbphlpd().toString());
				dcj2.setXh(xh);
				xh++;
				deviceCheckJudegRepository.save(dcj2);

			}
			flagMap.put(brd.getJyxm(), brd);
		}
		return xh;
	}

	private DeviceCheckJudeg createDeviceCheckJudegBaseInfo(VehCheckLogin vehCheckLogin) {
		DeviceCheckJudeg dcj = new DeviceCheckJudeg();
		dcj.setHphm(vehCheckLogin.getHphm());
		dcj.setHpzl(vehCheckLogin.getHpzl());
		dcj.setJycs(vehCheckLogin.getJycs());
		dcj.setJyjgbh(vehCheckLogin.getJyjgbh());
		dcj.setJylsh(vehCheckLogin.getJylsh());
		return dcj;
	}

	private Integer createLightDataJudeg(final VehCheckLogin vehCheckLogin, Map<String, Object> flagMap, Integer xh) {
		List<LightData> lightDatas = lightDataRepository.findLightDataByJylshAndSjzt(vehCheckLogin.getJylsh(),
				LightData.SJZT_ZC);

		String cllx = vehCheckLogin.getCllx();

		String syxz = vehCheckLogin.getSyxz();
		// 光强度总和 项目
		String zgqxm = "左右外灯远光发光强度总和(cd)";
		// 光强度总和 结果
		String zgqjg = "";
		// 光强度总和 标准限值
		String zgqxz = "430000";
		// 光强度总和 判定
		String zgqpd = "";

		for (LightData lightData : lightDatas) {
			String jyxm = lightData.getJyxm();
			if (flagMap.get(jyxm + lightData.getGx()) == null) {
				if (lightData.getGx() == LightData.GX_YGD) {
					DeviceCheckJudeg dcj1 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
					dcj1.setYqjyxm(CommonUtil.getLightGQ(jyxm) + "光强度(cd)");
					dcj1.setYqjyjg(lightData.getGq() == null ? "" : lightData.getGq().toString());
					dcj1.setYqbzxz(lightData.getGqxz() == null ? "" : "≥" + lightData.getGqxz().toString());
					dcj1.setYqjgpd(lightData.getGqpd() == null ? "" : lightData.getGqpd().toString());
					dcj1.setXh(xh);
					xh++;
					deviceCheckJudegRepository.save(dcj1);
					if (StringUtils.isEmpty(zgqjg)) {
						zgqjg = dcj1.getYqjyjg();
					} else {
						zgqjg = String.valueOf(Integer.parseInt(zgqjg) + Integer.parseInt(dcj1.getYqjyjg()));
					}
				}

				if (!((cllx.indexOf("K3") == 0 || cllx.indexOf("K4") == 0 || cllx.indexOf("N") == 0)
						&& syxz.equals("A"))) {
					// if(lightData.getGx() == LightData.GX_JGD) {
					DeviceCheckJudeg dcj2 = createDeviceCheckJudegBaseInfo(vehCheckLogin);
					dcj2.setYqjyxm(CommonUtil.getLight(jyxm) + (lightData.getGx() == LightData.GX_YGD ? "远光灯" : "近光灯")
							+ "垂直偏移(mm/10m)量");

					String czpc = lightData.getCzpc().toString().trim();
					if (CommonUtil.isInteger(czpc)) {
						Integer intczpc = Integer.parseInt(czpc);
						if (intczpc > 0) {
							czpc = "+" + String.valueOf(intczpc);
						} else {
							czpc = String.valueOf(intczpc);
						}
					}
					dcj2.setYqjyjg(lightData.getCzpc() == null ? "" : czpc);
					dcj2.setYqbzxz(lightData.getCzpyxz() == null ? "" : lightData.getCzpyxz().replace(",", "~"));
					dcj2.setYqjgpd(lightData.getCzpypd() == null ? "" : lightData.getCzpypd().toString());
					dcj2.setXh(xh);
					xh++;
					deviceCheckJudegRepository.save(dcj2);
					// }
				}

			}

			flagMap.put(jyxm + lightData.getGx(), lightData);

		}

		// 光强度总和
		if (!StringUtils.isEmpty(zgqjg)) {
			DeviceCheckJudeg zgq = createDeviceCheckJudegBaseInfo(vehCheckLogin);
			zgq.setYqjyxm(zgqxm);
			zgq.setYqjyjg(zgqjg);
			zgq.setYqbzxz("≤" + zgqxz);
			zgq.setYqjgpd(Integer.parseInt(zgqjg) > Integer.parseInt(zgqxz) ? "2" : "1");
			zgq.setXh(xh);
			xh++;
			deviceCheckJudegRepository.save(zgq);
		}

		return xh;
	}

	@Override
	public List<Map<String, Object>> getDeviceCheckJudeg(String jylsh) {
		
		return deviceCheckJudegRepository.findDeviceCheckJudegMapByJylsh(jylsh);
	}

}
