package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("deviceCheckJudeg")
@Entity
@Table(name = "TM_DeviceCheckJudeg")
public class DeviceCheckJudeg extends BaseEntity {

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
	private String yqjyxm;

	@Column(length = 1024)
	private String yqjyjg;

	@Column(length = 1024)
	private String yqbzxz;

	@Column(length = 10)
	private String yqjgpd;

	@Column(length = 1024)
	private String yqjybz;
	
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

	public String getJyjgbh() {
		return jyjgbh;
	}

	public Integer getXh() {
		return xh;
	}

	public String getYqjyxm() {
		return yqjyxm;
	}

	public String getYqjyjg() {
		return yqjyjg;
	}

	public String getYqbzxz() {
		return yqbzxz;
	}

	public String getYqjgpd() {
		return yqjgpd;
	}

	public String getYqjybz() {
		return yqjybz;
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

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public void setYqjyxm(String yqjyxm) {
		this.yqjyxm = yqjyxm;
	}

	public void setYqjyjg(String yqjyjg) {
		this.yqjyjg = yqjyjg;
	}

	public void setYqbzxz(String yqbzxz) {
		this.yqbzxz = yqbzxz;
	}

	public void setYqjgpd(String yqjgpd) {
		this.yqjgpd = yqjgpd;
	}

	public void setYqjybz(String yqjybz) {
		this.yqjybz = yqjybz;
	}

}
