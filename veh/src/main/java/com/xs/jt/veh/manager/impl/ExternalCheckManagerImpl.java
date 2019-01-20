package com.xs.jt.veh.manager.impl;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.veh.dao.ExternalCheckRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.entity.ExternalCheck;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.IExternalCheckManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;

@Service("externalCheckManager")
public class ExternalCheckManagerImpl implements IExternalCheckManager {

	@Autowired
	private ExternalCheckRepository externalCheckRepository;
	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;
	@Autowired
	private ICheckEventManager checkEventManager;
	@Autowired
	private IVehCheckLoginManager vehCheckLoginManager;

	@Autowired
	private HttpSession session;

	private ExternalCheck setExternalCheck(VehCheckLogin vehCheckLogin, ExternalCheck externalCheck) {

		ExternalCheck ec = (ExternalCheck) this.externalCheckRepository
				.getExternalCheckByJyLsh(vehCheckLogin.getJylsh()).get(0);

		ec.setItem1(externalCheck.getItem1());
		ec.setItem2(externalCheck.getItem2());
		ec.setItem3(externalCheck.getItem3());
		ec.setItem4(externalCheck.getItem4());
		ec.setItem5(externalCheck.getItem5());
		ec.setItem6(externalCheck.getItem6());
		ec.setItem7(externalCheck.getItem7());
		ec.setItem8(externalCheck.getItem8());
		ec.setItem9(externalCheck.getItem9());
		ec.setItem10(externalCheck.getItem10());
		ec.setItem11(externalCheck.getItem11());
		ec.setItem12(externalCheck.getItem12());
		ec.setItem13(externalCheck.getItem13());
		ec.setItem14(externalCheck.getItem14());
		ec.setItem15(externalCheck.getItem15());
		ec.setItem16(externalCheck.getItem16());
		ec.setItem17(externalCheck.getItem17());
		ec.setItem18(externalCheck.getItem18());
		ec.setItem19(externalCheck.getItem19());
		ec.setItem20(externalCheck.getItem20());
		ec.setItem21(externalCheck.getItem21());
		ec.setItem22(externalCheck.getItem22());
		ec.setItem23(externalCheck.getItem23());
		ec.setItem24(externalCheck.getItem24());
		ec.setItem25(externalCheck.getItem25());
		ec.setItem26(externalCheck.getItem26());
		ec.setItem27(externalCheck.getItem27());
		ec.setItem28(externalCheck.getItem28());
		ec.setItem29(externalCheck.getItem29());
		ec.setItem30(externalCheck.getItem30());
		ec.setItem31(externalCheck.getItem31());
		ec.setItem32(externalCheck.getItem32());
		ec.setItem33(externalCheck.getItem33());
		ec.setItem34(externalCheck.getItem34());
		ec.setItem35(externalCheck.getItem35());
		ec.setItem36(externalCheck.getItem36());
		ec.setItem37(externalCheck.getItem37());
		ec.setItem38(externalCheck.getItem38());
		ec.setItem39(externalCheck.getItem39());
		ec.setItem40(externalCheck.getItem40());
		ec.setItem41(externalCheck.getItem41());
		ec.setItem80(externalCheck.getItem80());

		ec.setCwkc(externalCheck.getCwkc());
		ec.setCwkg(externalCheck.getCwkg());
		ec.setCwkk(externalCheck.getCwkk());
		ec.setZbzl(externalCheck.getZbzl());

		User user = (User) session.getAttribute("user");
		ec.setWgjcjyy(user.getYhxm());// (user.getRealName());
		ec.setWgjcjyysfzh(user.getSfzh());// (user.getIdCard());

		return ec;
	}

	private ExternalCheck setExternalCheckDC(VehCheckLogin vehCheckLogin, ExternalCheck externalCheck) {

		ExternalCheck ec = (ExternalCheck) this.externalCheckRepository
				.getExternalCheckByJyLsh(vehCheckLogin.getJylsh()).get(0);

		ec.setItem42(externalCheck.getItem42());
		ec.setItem43(externalCheck.getItem43());
		ec.setItem44(externalCheck.getItem44());
		ec.setItem45(externalCheck.getItem45());
		User user = (User) session.getAttribute("user");
		ec.setDpdtjyy(user.getYhxm());// (user.getRealName());
		ec.setDpdtjyysfzh(user.getSfzh());// (user.getIdCard());

		return ec;
	}

	private ExternalCheck setExternalCheckC1(VehCheckLogin vehCheckLogin, ExternalCheck externalCheck) {

		ExternalCheck ec = (ExternalCheck) this.externalCheckRepository
				.getExternalCheckByJyLsh(vehCheckLogin.getJylsh()).get(0);
		ec.setItem46(externalCheck.getItem46());
		ec.setItem47(externalCheck.getItem47());
		ec.setItem48(externalCheck.getItem48());
		ec.setItem49(externalCheck.getItem49());
		ec.setItem50(externalCheck.getItem50());

		User user = (User) session.getAttribute("user");
		ec.setDpjcjyy(user.getYhxm());// (user.getRealName());
		ec.setDpjyysfzh(user.getSfzh());// (user.getIdCard());
		return ec;
	}

	/**
	 * 保存外检测信息
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheck(ExternalCheck externalCheck) throws InterruptedException {
		VehCheckLogin vehCheckLogin = vehCheckLoginRepository.getVehCheckLoginByJylsh(externalCheck.getJyjgbh(),
				externalCheck.getJylsh());

		ExternalCheck ec = setExternalCheck(vehCheckLogin, externalCheck);

		Message message = new Message();
		if (vehCheckLogin != null) {
			this.externalCheckRepository.save(ec);

			vehCheckLogin.setVehwjzt(VehCheckLogin.ZT_JYJS);
			vehCheckLogin.setExternalCheckDate(new Date());
			User user = (User) session.getAttribute("user");
			if (user != null) {
				vehCheckLogin.setWjy(user.getYhxm());// .getRealName());
				vehCheckLogin.setWjysfzh(user.getSfzh());// .getIdCard());
			}
			this.vehCheckLoginRepository.save(vehCheckLogin);

			VehCheckProcess vehCheckProcess = vehCheckProcessRepository.getVehCheckProces(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs(), "F1");
			vehCheckProcess.setJssj(new Date());
			this.vehCheckProcessRepository.save(vehCheckProcess);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C80", "F1",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());
			Thread.sleep(1000);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C58", "F1",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());

			// 判断项目的状态
			vehCheckLoginManager.updateVehCheckLoginState(vehCheckLogin.getJylsh());
			message.setMessage("上传成功");
			message.setState(Message.STATE_SUCCESS);
		} else {
			message.setMessage("无法找到该机动车的登陆信息");
			message.setState(Message.STATE_ERROR);
		}
		return message;
	}

	/**
	 * 动态底盘
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheckDC(ExternalCheck externalCheck) throws InterruptedException {
		VehCheckLogin vehCheckLogin = vehCheckLoginRepository.getVehCheckLoginByJylsh(

				externalCheck.getJyjgbh(), externalCheck.getJylsh());

		ExternalCheck ec = setExternalCheckDC(vehCheckLogin, externalCheck);

		Message message = new Message();
		if (vehCheckLogin != null) {
			this.externalCheckRepository.save(ec);
			vehCheckLogin.setVehdtdpzt(VehCheckLogin.ZT_JYJS);
			this.vehCheckLoginRepository.save(vehCheckLogin);
			// 判断项目的状态

			VehCheckProcess vehCheckProcess = vehCheckProcessRepository.getVehCheckProces(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs(), "DC");
			vehCheckProcess.setJssj(new Date());
			this.vehCheckProcessRepository.save(vehCheckProcess);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C80", "DC",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());
			Thread.sleep(1000);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C58", "DC",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());

			vehCheckLoginManager.updateVehCheckLoginState(vehCheckLogin.getJylsh());

			message.setMessage("上传成功");
			message.setState(Message.STATE_SUCCESS);
		} else {
			message.setMessage("无法找到该机动车的登陆信息");
			message.setState(Message.STATE_ERROR);
		}
		return message;
	}

	/**
	 * 动态底盘
	 * 
	 * @param externalCheck
	 * @return
	 * @throws InterruptedException
	 */
	public Message saveExternalCheckC1(ExternalCheck externalCheck) throws InterruptedException {
		VehCheckLogin vehCheckLogin = vehCheckLoginRepository.getVehCheckLoginByJylsh(externalCheck.getJyjgbh(),
				externalCheck.getJylsh());

		ExternalCheck ec = setExternalCheckC1(vehCheckLogin, externalCheck);

		Message message = new Message();
		if (vehCheckLogin != null) {
			this.externalCheckRepository.save(ec);
			vehCheckLogin.setVehdpzt(VehCheckLogin.ZT_JYJS);
			this.vehCheckLoginRepository.save(vehCheckLogin);

			VehCheckProcess vehCheckProcess = vehCheckProcessRepository.getVehCheckProces(vehCheckLogin.getJylsh(),
					vehCheckLogin.getJycs(), "C1");
			vehCheckProcess.setJssj(new Date());
			this.vehCheckProcessRepository.save(vehCheckProcess);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C80", "C1",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());
			Thread.sleep(1000);
			checkEventManager.createEvent(vehCheckLogin.getJylsh(), vehCheckLogin.getJycs(), "18C58", "C1",
					vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(),
					vehCheckLogin.getVehcsbj());

			// 判断项目的状态
			vehCheckLoginManager.updateVehCheckLoginState(vehCheckLogin.getJylsh());
			message.setMessage("上传成功");
			message.setState(Message.STATE_SUCCESS);
		} else {
			message.setMessage("无法找到该机动车的登陆信息");
			message.setState(Message.STATE_ERROR);
		}
		return message;
	}

	@Override
	public void createExternalCheck(VehCheckLogin vehCheckLogin) {
		ExternalCheck ec = new ExternalCheck();

		ec.setHphm(vehCheckLogin.getHphm());
		ec.setHpzl(vehCheckLogin.getHpzl());
		ec.setJylsh(vehCheckLogin.getJylsh());
		ec.setJyjgbh(vehCheckLogin.getJyjgbh());
		ec.setJycs(vehCheckLogin.getJycs());
		this.externalCheckRepository.save(ec);

	}

}
