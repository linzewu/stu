package com.xs.jt.veh.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.ExternalCheckJudgeRepository;
import com.xs.jt.veh.entity.ExternalCheckJudge;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.manager.IExternalCheckJudgeManager;

@Service("externalCheckJudgeManager")
public class ExternalCheckJudgeManagerImpl implements IExternalCheckJudgeManager {

	@Autowired
	private ExternalCheckJudgeRepository externalCheckJudgeRepository;

	@Override
	public void createExternalCheckJudge(VehCheckLogin vehCheckLogin) {
		String jylsh = vehCheckLogin.getJylsh();
		String jyjgbh = vehCheckLogin.getJyjgbh();

		externalCheckJudgeRepository.deleteExternalCheckJudgeByJylshAndJyjgbh(jylsh, jyjgbh);

		// List<ExternalCheck> externalChecks = (List<ExternalCheck>)
		// this.hibernateTemplate.find("from ExternalCheck where jylsh=? ", jylsh);

		int i = 1;
		if (vehCheckLogin.getJyxm().indexOf("F1") >= 0) {
			for (; i <= 5; i++) {
				ExternalCheckJudge ecj = new ExternalCheckJudge();
				ecj.setJylsh(vehCheckLogin.getJylsh());
				ecj.setJycs(vehCheckLogin.getJycs());
				ecj.setJyjgbh(vehCheckLogin.getJyjgbh());
				ecj.setHphm(vehCheckLogin.getHphm());
				ecj.setHpzl(vehCheckLogin.getHpzl());
				ecj.setXh(i);
				if (i == 1) {
					ecj.setRgjyxm("车辆唯一性检测");
				} else if (i == 2) {
					ecj.setRgjyxm("车辆特征参数检查");
					/*
					 * if(externalChecks!=null&&!externalChecks.isEmpty()){ ExternalCheck
					 * externalCheck =externalChecks.get(0); String item6 =
					 * externalCheck.getItem6(); if(cs.equals("J")&&sf.equals("苏")){
					 * if(item6.equals("1")||item6.equals("2")){
					 * ecj.setRgjybz(externalCheck.getCwkc()+"*"+externalCheck.getCwkk()+"*"+
					 * externalCheck.getCwkg()); } } }
					 */

				} else if (i == 3) {
					ecj.setRgjyxm("车辆外观检查");
				} else if (i == 4) {
					ecj.setRgjyxm("安检装置检查");
				} else if (i == 5) {
					ecj.setRgjyxm("联网查询");
				}
				ecj.setRgjgpd("1");
				externalCheckJudgeRepository.save(ecj);
			}
		}

		if (vehCheckLogin.getJyxm().indexOf("DC") >= 0) {
			ExternalCheckJudge ecj = new ExternalCheckJudge();
			ecj.setJylsh(vehCheckLogin.getJylsh());
			ecj.setJycs(vehCheckLogin.getJycs());
			ecj.setJyjgbh(vehCheckLogin.getJyjgbh());
			ecj.setHphm(vehCheckLogin.getHphm());
			ecj.setHpzl(vehCheckLogin.getHpzl());
			ecj.setXh(i);
			ecj.setRgjyxm("底盘动态检验");
			ecj.setRgjgpd("1");
			externalCheckJudgeRepository.save(ecj);
			i++;
		}

		if (vehCheckLogin.getJyxm().indexOf("C1") >= 0) {

			ExternalCheckJudge ecj = new ExternalCheckJudge();
			ecj.setJylsh(vehCheckLogin.getJylsh());
			ecj.setJycs(vehCheckLogin.getJycs());
			ecj.setJyjgbh(vehCheckLogin.getJyjgbh());
			ecj.setHphm(vehCheckLogin.getHphm());
			ecj.setHpzl(vehCheckLogin.getHpzl());
			ecj.setXh(i);
			ecj.setRgjyxm("车辆底盘部件检查");
			ecj.setRgjgpd("1");
			externalCheckJudgeRepository.save(ecj);
			i++;
		}

	}

	@Override
	public List<Map<String,Object>> getExternalCheckJudge(String jylsh) {
		return externalCheckJudgeRepository.findExternalCheckJudgeMapByJylsh(jylsh);
	}

}
