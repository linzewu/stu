package com.xs.jt.veh.manager.impl;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.veh.dao.CheckEventRepository;
import com.xs.jt.veh.dao.CheckQueueRepository;
import com.xs.jt.veh.dao.DeviceCheckJudegRepository;
import com.xs.jt.veh.dao.ExternalCheckJudgeRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.dao.VehFlowRepository;
import com.xs.jt.veh.dao.network.BrakRollerDataRepository;
import com.xs.jt.veh.dao.network.LightDataRepository;
import com.xs.jt.veh.dao.network.OtherInfoDataRepository;
import com.xs.jt.veh.dao.network.OutlineRepository;
import com.xs.jt.veh.dao.network.ParDataOfAnjianRepository;
import com.xs.jt.veh.dao.network.SideslipDataRepository;
import com.xs.jt.veh.dao.network.SpeedDataRepository;
import com.xs.jt.veh.entity.CheckEvents;
import com.xs.jt.veh.entity.CheckQueue;
import com.xs.jt.veh.entity.DeviceCheckJudeg;
import com.xs.jt.veh.entity.ExternalCheckJudge;
import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.entity.network.BaseDeviceData;
import com.xs.jt.veh.entity.network.BrakRollerData;
import com.xs.jt.veh.entity.network.LightData;
import com.xs.jt.veh.entity.network.OtherInfoData;
import com.xs.jt.veh.entity.network.Outline;
import com.xs.jt.veh.entity.network.ParDataOfAnjian;
import com.xs.jt.veh.entity.network.SideslipData;
import com.xs.jt.veh.entity.network.SpeedData;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.ICheckQueueManager;
import com.xs.jt.veh.manager.IDeviceCheckJudegManager;
import com.xs.jt.veh.manager.IExternalCheckJudgeManager;
import com.xs.jt.veh.manager.IExternalCheckManager;
import com.xs.jt.veh.manager.IFlowManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;
import com.xs.jt.veh.manager.IVehFlowManager;

import net.sf.json.JSONObject;

@Service("vehCheckLoginManager")
public class VehCheckLoginManagerImpl implements IVehCheckLoginManager {

	private static Log logger = LogFactory.getLog(VehCheckLoginManagerImpl.class);

	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private DeviceCheckJudegRepository deviceCheckJudegRepository;
	@Autowired
	private ExternalCheckJudgeRepository externalCheckJudgeRepository;
	@Autowired
	private ICheckEventManager checkEventManager;
	@Autowired
	private IDeviceCheckJudegManager deviceCheckJudegManager;
	@Autowired
	private IExternalCheckJudgeManager externalCheckJudgeManager;
	@Autowired
	private OtherInfoDataRepository otherInfoDataRepository;
	@Autowired
	private BrakRollerDataRepository brakRollerDataRepository;
	@Autowired
	private LightDataRepository lightDataRepository;
	@Autowired
	private SpeedDataRepository speedDataRepository;
	@Autowired
	private SideslipDataRepository sideslipDataRepository;
	@Autowired
	private ParDataOfAnjianRepository parDataOfAnjianRepository;
	@Autowired
	private OutlineRepository outlineRepository;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;
	@Autowired
	private IFlowManager flowManager;
	@Autowired
	private IVehFlowManager vehFlowManager;
	@Autowired
	private IExternalCheckManager externalCheckManager;

	@Autowired
	private CheckEventRepository checkEventRepository;
	@Autowired
	private CheckQueueRepository checkQueueRepository;
	@Autowired
	private VehFlowRepository vehFlowRepository;
	@Autowired
	private HttpSession session;
	@Autowired
	private ICheckQueueManager checkQueueManager;

	@Value("${isNetwork}")
	private boolean isNetwork;

	@Override
	public void updateVehCheckLoginState(String jylsh) {
		List<VehCheckLogin> array = this.vehCheckLoginRepository.findByJylsh(jylsh);

		if (array != null && !array.isEmpty()) {
			VehCheckLogin vehCheckLogin = array.get(0);
			// 仪器报告单
			if ((vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_JYJS
					|| vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehwkzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehwkzt() == VehCheckLogin.ZT_BJC)) {
				deviceCheckJudegManager.createDeviceCheckJudeg(vehCheckLogin);
			}

			// 外检报告
			if ((vehCheckLogin.getVehdpzt() == VehCheckLogin.ZT_BJC
					|| vehCheckLogin.getVehdpzt() == VehCheckLogin.ZT_JYJS)
					&& (vehCheckLogin.getVehdtdpzt() == VehCheckLogin.ZT_BJC
							|| vehCheckLogin.getVehdtdpzt() == VehCheckLogin.ZT_JYJS)
					&& (vehCheckLogin.getVehwjzt() == VehCheckLogin.ZT_BJC
							|| vehCheckLogin.getVehwjzt() == VehCheckLogin.ZT_JYJS)) {
				externalCheckJudgeManager.createExternalCheckJudge(vehCheckLogin);
			}

			if ((vehCheckLogin.getVehwjzt() == VehCheckLogin.ZT_JYJS
					|| vehCheckLogin.getVehwjzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehdpzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehdpzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehdtdpzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehdtdpzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehlszt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehlszt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehzbzlzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehzbzlzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehwkzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehwkzt() == VehCheckLogin.ZT_BJC)
					&& (vehCheckLogin.getVehzbzlzt() == VehCheckLogin.ZT_JYJS
							|| vehCheckLogin.getVehzbzlzt() == VehCheckLogin.ZT_BJC)) {

				List<DeviceCheckJudeg> deviceCheckJudegs = deviceCheckJudegRepository
						.findDeviceCheckJudegByJylsh(vehCheckLogin.getJylsh());

				List<ExternalCheckJudge> externalCheckJudges = externalCheckJudgeRepository
						.findExternalCheckJudgeByJylsh(vehCheckLogin.getJylsh());

				String jyjl = "合格";
				for (DeviceCheckJudeg deviceCheckJudeg : deviceCheckJudegs) {
					if (deviceCheckJudeg.getYqjgpd().equals("2")) {
						jyjl = "不合格";
					}
				}

				for (ExternalCheckJudge externalCheckJudge : externalCheckJudges) {
					if (externalCheckJudge.getRgjgpd().equals("2")) {
						jyjl = "不合格";
					}
				}

				vehCheckLogin.setJyjl(jyjl);
				checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C82", null,
						vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
						vehCheckLogin.getVehcsbj());
				checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C59", null,
						vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
						vehCheckLogin.getVehcsbj());

				// 复检
				if (vehCheckLogin.getVehcsbj() == 0) {
					repeatLogin(vehCheckLogin);
				} else {
					vehCheckLogin.setFjjyxm("");
					vehCheckLogin.setVehjczt(VehCheckLogin.JCZT_JYJS);
					vehCheckLogin.setVehsxzt(VehCheckLogin.ZT_JYJS);
				}

				this.vehCheckLoginRepository.save(vehCheckLogin);

				if (vehCheckLogin.getFjjyxm() == null || vehCheckLogin.getFjjyxm().equals("")) {
					checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C62", null,
							vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
							vehCheckLogin.getVehcsbj());
				} else {
					checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C65", null,
							vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
							vehCheckLogin.getVehcsbj());
					checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C52", null,
							vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
							vehCheckLogin.getVehcsbj());
				}

			}
		}

	}

	// 复检项目
	@Override
	public VehCheckLogin repeatLogin(VehCheckLogin vehCheckLogin) {

		String jylsh = vehCheckLogin.getJylsh();

		String fjjyxm = "";
		List<OtherInfoData> otherInfoData = otherInfoDataRepository.findOtherInfoDataByJylshAndZczdpd(jylsh,
				BaseDeviceData.PDJG_BHG.toString());

		// 整车不合格则全部复检
		if (otherInfoData != null && !otherInfoData.isEmpty()) {
			logger.info("整车不合格");
			if (vehCheckLogin.getJyxm().indexOf("B1") >= 0) {
				fjjyxm += "B1,";
			}
			if (vehCheckLogin.getJyxm().indexOf("B2") >= 0) {
				fjjyxm += "B2,";
			}
			if (vehCheckLogin.getJyxm().indexOf("B3") >= 0) {
				fjjyxm += "B3,";
			}
			if (vehCheckLogin.getJyxm().indexOf("B4") >= 0) {
				fjjyxm += "B4,";
			}
			if (vehCheckLogin.getJyxm().indexOf("B5") >= 0) {
				fjjyxm += "B5,";
			}
			List<BrakRollerData> brakRollerDatas = brakRollerDataRepository.findByJylshAndInJyxmAndSjzt(jylsh,
					BaseDeviceData.SJZT_ZC);
			for (BrakRollerData brakRollerData : brakRollerDatas) {
				brakRollerData.setSjzt(BaseDeviceData.SJZT_FJ);
				brakRollerDataRepository.save(brakRollerData);
			}

		} else {
			// 制动复检
			List<BrakRollerData> brakRollerDatas = brakRollerDataRepository.findByJylshAndNotJyxmAndSjztAndZpd(jylsh,
					"B0", BaseDeviceData.SJZT_ZC, BaseDeviceData.PDJG_BHG);
			for (BrakRollerData brakRollerData : brakRollerDatas) {
				logger.info(brakRollerData.getJyxm() + "不合格");
				fjjyxm += brakRollerData.getJyxm() + ",";
				brakRollerData.setSjzt(BaseDeviceData.SJZT_FJ);
				brakRollerDataRepository.save(brakRollerData);
			}
		}

		// 灯光复检
		List<LightData> lightDatas = lightDataRepository.findLightDataByJylshAndSjztAndZpd(jylsh,
				BaseDeviceData.SJZT_ZC, BaseDeviceData.PDJG_BHG);
		if (lightDatas != null && !lightDatas.isEmpty()) {
			if (vehCheckLogin.getJyxm().indexOf("H1") >= 0) {
				fjjyxm += "H1,";
			}
			if (vehCheckLogin.getJyxm().indexOf("H2") >= 0) {
				fjjyxm += "H2,";
			}
			if (vehCheckLogin.getJyxm().indexOf("H3") >= 0) {
				fjjyxm += "H3,";
			}
			if (vehCheckLogin.getJyxm().indexOf("H4") >= 0) {
				fjjyxm += "H4,";
			}
		}
		for (LightData lightData : lightDatas) {
			lightData.setSjzt(BaseDeviceData.SJZT_FJ);
			lightDataRepository.save(lightData);
		}

		// 速度复检
		List<SpeedData> speedDatas = speedDataRepository.findSpeedDataByJylshAndSjztAndZpd(jylsh,
				BaseDeviceData.SJZT_ZC, BaseDeviceData.PDJG_BHG);
		if (speedDatas != null && !speedDatas.isEmpty()) {
			fjjyxm += "S1,";
		}

		for (SpeedData speedData : speedDatas) {
			speedData.setSjzt(SpeedData.SJZT_FJ);
			speedDataRepository.save(speedData);
		}

		// 侧滑复检
		List<SideslipData> sideslipDatas = sideslipDataRepository.findSideslipDataByJylshAndSjztAndZpd(jylsh,
				BaseDeviceData.SJZT_ZC, BaseDeviceData.PDJG_BHG);
		if (sideslipDatas != null && !sideslipDatas.isEmpty()) {
			fjjyxm += "A1,";
			sideslipDatas.get(0).setSjzt(SpeedData.SJZT_FJ);
			sideslipDataRepository.save(sideslipDatas.get(0));
		}

		for (SideslipData sideslipDat : sideslipDatas) {
			sideslipDat.setSjzt(SpeedData.SJZT_FJ);
			sideslipDataRepository.save(sideslipDat);
		}

		// 驻车复检
		List<ParDataOfAnjian> parDataOfAnjian = parDataOfAnjianRepository
				.findParDataOfAnjianByJylshAndSjztAndTczdpd(jylsh, BaseDeviceData.SJZT_ZC, BaseDeviceData.PDJG_BHG);
		if (parDataOfAnjian != null && !parDataOfAnjian.isEmpty()) {
			fjjyxm += "B0,";
			parDataOfAnjian.get(0).setSjzt(ParDataOfAnjian.SJZT_FJ);
			parDataOfAnjianRepository.save(parDataOfAnjian.get(0));
		}

		List<Outline> outlines = outlineRepository.findOutlineByJylsh(jylsh);

		if (outlines != null && !outlines.isEmpty()) {
			Outline outline = outlines.get(0);
			if (outline.getClwkccpd() == BaseDeviceData.PDJG_BHG) {
				fjjyxm += "M1,";
				outline.setSjzt(ParDataOfAnjian.SJZT_FJ);
				outlineRepository.save(outline);
				vehCheckLogin.setVehwkzt(VehCheckLogin.ZT_JCZ);
			}
		}

		if (!fjjyxm.trim().equals("")) {
			fjjyxm = fjjyxm.substring(0, fjjyxm.length() - 1);
			vehCheckLogin.setVehjczt(VehCheckLogin.JCZT_JYZ);
			vehCheckLogin.setVehsxzt(VehCheckLogin.ZT_WKS);

			vehCheckLogin.setJycs(vehCheckLogin.getJycs() + 1);
			vehCheckLogin.setFjjyxm(fjjyxm);

			Flow flow = flowManager.getFlow(Integer.parseInt(vehCheckLogin.getJcxdh()), vehCheckLogin.getCheckType());

			String[] jyxmArray = fjjyxm.split(",");
			List<VehCheckProcess> processArray = new ArrayList<VehCheckProcess>();
			for (String jyxmItem : jyxmArray) {
				VehCheckProcess vcp = new VehCheckProcess();
				vcp.setClsbdh(vehCheckLogin.getClsbdh());
				vcp.setHphm(vehCheckLogin.getHphm());
				vcp.setHpzl(vehCheckLogin.getHpzl());
				vcp.setJylsh(vehCheckLogin.getJylsh());
				vcp.setJyxm(jyxmItem);
				vcp.setJycs(vehCheckLogin.getJycs());
				vehCheckProcessRepository.save(vcp);
				processArray.add(vcp);
			}
			vehFlowManager.addVehFlow(vehCheckLogin, processArray, flow);

		} else {
			vehCheckLogin.setFjjyxm("");
			vehCheckLogin.setVehjczt(VehCheckLogin.JCZT_JYJS);
			vehCheckLogin.setVehsxzt(VehCheckLogin.ZT_JYJS);
		}
		logger.info("复检项目：" + fjjyxm);
		return vehCheckLogin;

	}

	@Override
	public JSONObject vehLogin(VehCheckLogin vehCheckLogin) {
		Flow flow = flowManager.getFlow(Integer.parseInt(vehCheckLogin.getJcxdh()), vehCheckLogin.getCheckType());
		JSONObject head = new JSONObject();
		JSONObject messager = null;

		if (flow == null) {
			head.put("code", "-1");
			head.put("message", "无法获取检测流程");
			messager = new JSONObject();
			messager.put("head", head);
			return messager;
		}

		// 默认情况下为成功
		head.put("code", "1");
		vehCheckLogin.setVehcsbj(0);
		vehCheckLoginRepository.save(vehCheckLogin);
		String jyxm = vehCheckLogin.getJyxm();
		String[] jyxmArray = jyxm.split(",");
		List<VehCheckProcess> processArray = new ArrayList<VehCheckProcess>();
		for (String jyxmItem : jyxmArray) {
			VehCheckProcess vcp = new VehCheckProcess();
			vcp.setClsbdh(vehCheckLogin.getClsbdh());
			vcp.setHphm(vehCheckLogin.getHphm());
			vcp.setHpzl(vehCheckLogin.getHpzl());
			vcp.setJylsh(vehCheckLogin.getJylsh());
			vcp.setJyxm(jyxmItem);
			vcp.setJycs(vehCheckLogin.getJycs());
			this.vehCheckProcessRepository.save(vcp);
			processArray.add(vcp);
		}
		vehFlowManager.addVehFlow(vehCheckLogin, processArray, flow);
		externalCheckManager.createExternalCheck(vehCheckLogin);

		CheckEvents ce = new CheckEvents();
		ce.setClsbdh(vehCheckLogin.getClsbdh());
		ce.setEvent("18C51");
		ce.setHpzl(vehCheckLogin.getHpzl());
		ce.setJylsh(vehCheckLogin.getJylsh());
		ce.setHphm(vehCheckLogin.getHphm());
		ce.setCreateDate(new Date());
		ce.setState(vehCheckLogin.getVehcsbj());
		ce.setJycs(vehCheckLogin.getJycs());
		this.checkEventRepository.save(ce);

		CheckEvents ce2 = new CheckEvents();
		ce2.setClsbdh(vehCheckLogin.getClsbdh());
		ce2.setEvent("18C52");
		ce2.setHpzl(vehCheckLogin.getHpzl());
		ce2.setJylsh(vehCheckLogin.getJylsh());
		ce2.setHphm(vehCheckLogin.getHphm());
		ce2.setCreateDate(new Date());
		ce2.setState(vehCheckLogin.getVehcsbj());
		ce2.setJycs(vehCheckLogin.getJycs());
		this.checkEventRepository.save(ce2);

		head = new JSONObject();
		head.put("code", 1);
		head.put("isNetwork", false);
		messager = new JSONObject();
		messager.put("head", head);
		return messager;
	}

	@Override
	public VehCheckLogin getVehCheckLogin(String jylsh) {
		List<VehCheckLogin> list = vehCheckLoginRepository.findByJylsh(jylsh);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<VehCheckLogin> getExternalC1(String hphm) {
		List<VehCheckLogin> vehList = vehCheckLoginRepository.findAll(new Specification<VehCheckLogin>() {

			@Override
			public Predicate toPredicate(Root<VehCheckLogin> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.notEqual(root.get("vehjczt").as(Integer.class), VehCheckLogin.JCZT_TB));
				list.add(criteriaBuilder.equal(root.get("vehdpzt").as(Integer.class), VehCheckLogin.ZT_WKS));
				if (Common.isNotEmpty(hphm)) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), hphm));
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dlsj").as(Date.class), c.getTime()));
				}
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}

		});
		return vehList;
	}

	@Override
	public List<VehCheckLogin> getExternalDC(String hphm) {
		List<VehCheckLogin> vehList = vehCheckLoginRepository.findAll(new Specification<VehCheckLogin>() {

			@Override
			public Predicate toPredicate(Root<VehCheckLogin> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.notEqual(root.get("vehjczt").as(Integer.class), VehCheckLogin.JCZT_TB));
				list.add(criteriaBuilder.equal(root.get("vehdtdpzt").as(Integer.class), VehCheckLogin.ZT_WKS));
				if (Common.isNotEmpty(hphm)) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), hphm));
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dlsj").as(Date.class), c.getTime()));
				}
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}

		});
		return vehList;
	}

	@Override
	public List<VehCheckLogin> getExternalR(String hphm) {

		List<VehCheckLogin> vehList = vehCheckLoginRepository.findAll(new Specification<VehCheckLogin>() {

			@Override
			public Predicate toPredicate(Root<VehCheckLogin> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				Predicate pre1 = criteriaBuilder.equal(root.get("vehlszt").as(Integer.class), VehCheckLogin.ZT_JCZ);
				Predicate pre2 = criteriaBuilder.equal(root.get("vehlszt").as(Integer.class), VehCheckLogin.ZT_WKS);
				Predicate orPre = criteriaBuilder.or(pre1, pre2);

				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.notEqual(root.get("vehjczt").as(Integer.class), VehCheckLogin.JCZT_TB));
				if (Common.isNotEmpty(hphm)) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), hphm));
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dlsj").as(Date.class), c.getTime()));
				}
				list.add(orPre);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}

		});

//		Specification s1 = new Specification() {
//		    @Override
//		    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//		    	Predicate pre1 = criteriaBuilder.equal(root.get("vehlszt").as(Integer.class), VehCheckLogin.ZT_JCZ);
//				Predicate pre2 = criteriaBuilder.equal(root.get("vehlszt").as(Integer.class), VehCheckLogin.ZT_WKS);
//		        return criteriaBuilder.or(pre1,pre2);
//		    }
//		};
//		
//		Specification s2 = new Specification() {
//		    @Override
//		    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//		    	List<Predicate> list = new ArrayList<Predicate>();
//				list.add(criteriaBuilder.notEqual(root.get("vehjczt").as(Integer.class), VehCheckLogin.JCZT_TB));
//				if(Common.isNotEmpty(hphm)) {
//					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), hphm));
//				}else {
//					Calendar c = Calendar.getInstance();
//					c.add(Calendar.MONTH, -1);
//					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dlsj").as(Date.class), c.getTime()));
//				}				
//				
//				Predicate[] p = new Predicate[list.size()];
//				return criteriaBuilder.and(list.toArray(p));
//		    }
//		};
//		
//		List<VehCheckLogin> vehList = vehCheckLoginRepository.findAll(Specifications.where(s1).and(s2));
		return vehList;
	}

	@Override
	public List<VehCheckLogin> getVehCheckLoginOfSXZT(Integer zt) {

		return vehCheckLoginRepository.findByVehsxztAndVehjczt(zt, VehCheckLogin.JCZT_TB);
	}

	@Override
	public List<VehCheckLogin> getExternalCheckVhe(String hphm) {
		List<VehCheckLogin> vehList = vehCheckLoginRepository.findAll(new Specification<VehCheckLogin>() {

			@Override
			public Predicate toPredicate(Root<VehCheckLogin> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.notEqual(root.get("vehjczt").as(Integer.class), VehCheckLogin.JCZT_TB));
				list.add(criteriaBuilder.equal(root.get("vehwjzt").as(Integer.class), VehCheckLogin.ZT_WKS));
				if (Common.isNotEmpty(hphm)) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), hphm));
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dlsj").as(Date.class), c.getTime()));
				}
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}

		});
		return vehList;
	}

	@Override
	public Message upLine(Integer id) {
		VehCheckLogin vehCheckLogin = this.vehCheckLoginRepository.findById(id).get();
		User user = (User) session.getAttribute("user");

		Message message = new Message();

		if (vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_JYJS || vehCheckLogin.getVehsxzt() == VehCheckLogin.ZT_JCZ) {
			message.setState(Message.STATE_ERROR);
			message.setMessage("引车上线失败，该流水已上线！");
			return message;

		} else {
			// 获取第一顺序流程
			VehFlow firstVehFlow = vehFlowRepository.findByJylshAndJycsAndSx(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs());

			int gwxs = firstVehFlow.getGwsx();

			List<CheckQueue> checkQueues = checkQueueRepository.getCheckQueueByGwsxAndJcxdh(gwxs,
					Integer.parseInt(vehCheckLogin.getJcxdh()));

			if (checkQueues != null && !checkQueues.isEmpty()) {
				message.setState(Message.STATE_ERROR);
				message.setMessage("线上有车，请稍等！");
				return message;
			}

			// 获取同一工位的流程
			List<VehFlow> vehFlows = vehFlowRepository.findByJylshAndJycsAndGw(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs(), firstVehFlow.getGw());

			for (VehFlow vehFlow : vehFlows) {
				CheckQueue queue = new CheckQueue();
				queue.setGwsx(vehFlow.getGwsx());
				queue.setHphm(vehFlow.getHphm());
				queue.setHpzl(vehFlow.getHpzl());
				queue.setJcxdh(Integer.parseInt(vehCheckLogin.getJcxdh()));
				queue.setJycs(vehFlow.getJycs());
				queue.setJylsh(vehFlow.getJylsh());
				queue.setPdxh(checkQueueManager.getPdxh(vehCheckLogin.getJcxdh(), vehFlow.getGwsx()));
				queue.setLcsx(vehFlow.getSx());
				this.checkQueueRepository.save(queue);
			}

			vehCheckLogin.setVehsxzt(VehCheckLogin.ZT_JCZ);
			vehCheckLogin.setUpLineDate(new Date());

			if (user != null) {
				vehCheckLogin.setYcy(user.getYhxm());// .getRealName());
				vehCheckLogin.setYcysfzh(user.getSfzh());// .getIdCard());
			}

			this.vehCheckLoginRepository.save(vehCheckLogin);

			message.setState(Message.STATE_SUCCESS);
			message.setMessage("上线成功！");

			return message;

		}
	}

	@Override
	public List<VehCheckLogin> getVheInfoOfHphm(String hphm) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		return vehCheckLoginRepository.findByHphmAndDlsj(hphm, c.getTime());
	}

	@Override
	public Map<String, Object> getVehChecking(Integer page, Integer rows, VehCheckLogin vehCheckLogin, Integer[] jczt) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VehCheckLogin> bookPage = vehCheckLoginRepository.findAll(new Specification<VehCheckLogin>() {
			public Predicate toPredicate(Root<VehCheckLogin> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(vehCheckLogin.getHphm())) {
					list.add(criteriaBuilder.like(root.get("hphm").as(String.class),
							"%" + vehCheckLogin.getHphm() + "%"));
				}
				if (StringUtils.isNotEmpty(vehCheckLogin.getClxh())) {
					list.add(criteriaBuilder.like(root.get("clxh").as(String.class),
							"%" + vehCheckLogin.getClxh() + "%"));
				}
				if (StringUtils.isNotEmpty(vehCheckLogin.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class), vehCheckLogin.getHpzl()));
				}
				if (jczt != null) {
					Path<Object> path = root.get("vehjczt");
					CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
					for (Integer zt : jczt) {
						in.value(zt);
					}
					list.add(in);
				}

				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("jylsh")));
				query.orderBy(orders);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());
		return data;
	}

	@Override
	public boolean isLoged(VehCheckLogin vehCheckLogin) {
		String hphm = vehCheckLogin.getHphm();
		String hpzl = vehCheckLogin.getHpzl();

		List data = vehCheckLoginRepository.findByHphmAndHpzlAndVehjczt(hphm, hpzl, VehCheckLogin.JCZT_DL,
				VehCheckLogin.JCZT_JYZ);

		if (data != null && !data.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 退办逻辑
	 */
	@Override
	public JSONObject unLoginVeh(Integer id) throws RemoteException, UnsupportedEncodingException, DocumentException {
		VehCheckLogin vheLogininfo = this.vehCheckLoginRepository.findById(id).get();
		final String jylsh = vheLogininfo.getJylsh();
		JSONObject jo = new JSONObject();
		JSONObject jsonHead = new JSONObject();
		jsonHead.put("code", "1");
		jsonHead.put("isNetwork", isNetwork);
		jo.put("head", jsonHead);

		vheLogininfo.setVehjczt(VehCheckLogin.JCZT_TB);
		this.vehCheckLoginRepository.save(vheLogininfo);
		// 同时修改 上线表 队列表 状态 为退办

		checkQueueRepository.deleteCheckQueueByJylsh(jylsh);

		return jo;
	}

}
