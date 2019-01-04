package com.xs.jt.base.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("department")
@Entity
@Table(name = "TB_Department")
public class Department extends BaseEntity {
	
	@Column(length=32)
	private String sjbmdm;
	
	@Column(length=32,unique=true)
	private String bmdm;
	
	@Column
	private Integer bmjb;
	
	@Column(length=400)
	private String bmmc;
	
	@Column(length=400)
	private String bmqc;
	
	@Column(length=100)
	private String symc;
	
	@Column(length=30)
	private String fzr;
	
	@Column(length=30)
	private String lxr;
	
	@Column(length=20)
	private String lxdh;
	
	@Column(length=20)
	private String czhm;
	
	@Column(length=400)
	private String dz;
	
	@Column(length=800)
	private String bz;


	public String getSjbmdm() {
		return sjbmdm;
	}

	public void setSjbmdm(String sjbmdm) {
		this.sjbmdm = sjbmdm;
	}

	public String getBmdm() {
		return bmdm;
	}


	public String getBmmc() {
		return bmmc;
	}

	public String getBmqc() {
		return bmqc;
	}

	public String getSymc() {
		return symc;
	}

	public String getFzr() {
		return fzr;
	}

	public String getLxr() {
		return lxr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public String getCzhm() {
		return czhm;
	}

	public String getDz() {
		return dz;
	}

	public String getBz() {
		return bz;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}


	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public void setBmqc(String bmqc) {
		this.bmqc = bmqc;
	}

	public void setSymc(String symc) {
		this.symc = symc;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public void setCzhm(String czhm) {
		this.czhm = czhm;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getBmjb() {
		return bmjb;
	}

	public void setBmjb(Integer bmjb) {
		this.bmjb = bmjb;
	}
	
	

}
