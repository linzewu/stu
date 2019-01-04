package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("externalCheckJudge")
@Entity
@Table(name = "TM_ExternalCheckJudge")
public class ExternalCheckJudge extends BaseEntity {
	
	@Column(length = 25)
	private String jylsh;

	@Column(length = 20)
	private String hphm;

	@Column(length = 10)
	private String hpzl;

	@Column
	private Integer jycs;
	
	@Column
	private String jyjgbh;

	@Column
	private Integer xh;
	
	@Column
	private String rgjyxm;
	
	@Column
	private String rgjgpd;
	
	@Column
	private String rgjysm;
	
	@Column
	private String rgjybz;
	
	@Column(length=400)
	private String bz1;
	
	

	public String getBz1() {
		return bz1;
	}

	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public Integer getJycs() {
		return jycs;
	}


	public Integer getXh() {
		return xh;
	}

	public String getRgjyxm() {
		return rgjyxm;
	}

	public String getRgjgpd() {
		return rgjgpd;
	}

	public String getRgjysm() {
		return rgjysm;
	}

	public String getRgjybz() {
		return rgjybz;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}


	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public void setRgjyxm(String rgjyxm) {
		this.rgjyxm = rgjyxm;
	}

	public void setRgjgpd(String rgjgpd) {
		this.rgjgpd = rgjgpd;
	}

	public void setRgjysm(String rgjysm) {
		this.rgjysm = rgjysm;
	}

	public void setRgjybz(String rgjybz) {
		this.rgjybz = rgjybz;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

}
