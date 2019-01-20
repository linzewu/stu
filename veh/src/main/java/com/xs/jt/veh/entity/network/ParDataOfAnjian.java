package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.veh.entity.VehCheckLogin;


@Scope("prototype")
@Component("parDataOfAnjian")
@Entity
@Table(name = "TM_ParDataOfAnjian")
public class ParDataOfAnjian extends BaseDeviceData {
	
	@Column
	private Integer yzzczdl;
	
	@Column
	private Integer ezzczdl;
	
	@Column
	private Integer sanzzczdl;
	
	@Column
	private Integer sizzczdl;
	
	@Column
	private Integer wzzczdl;
	
	@Column
	private Integer zczczdl;
	
	@Column
	private Integer tczzdl;
	
	@Column
	private Integer tcyzdl;
	
	@Column
	private Float tczdl;
	
	@Column
	private Integer tczdpd;
	
	@Column
	private Float tczdxz;
	
	/**
	 * 驻车轮荷
	 */
	@Column
	private Integer tczclh;
	
	
	

	public Integer getTczclh() {
		return tczclh;
	}

	public void setTczclh(Integer tczclh) {
		this.tczclh = tczclh;
	}

	public Integer getYzzczdl() {
		return yzzczdl;
	}

	public Integer getEzzczdl() {
		return ezzczdl;
	}

	public Integer getSanzzczdl() {
		return sanzzczdl;
	}

	public Integer getSizzczdl() {
		return sizzczdl;
	}

	public Integer getWzzczdl() {
		return wzzczdl;
	}

	public Integer getZczczdl() {
		return zczczdl;
	}

	public Integer getTczzdl() {
		return tczzdl;
	}

	public Integer getTcyzdl() {
		return tcyzdl;
	}


	public Integer getTczdpd() {
		return tczdpd;
	}

	public void setYzzczdl(Integer yzzczdl) {
		this.yzzczdl = yzzczdl;
	}

	public void setEzzczdl(Integer ezzczdl) {
		this.ezzczdl = ezzczdl;
	}

	public void setSanzzczdl(Integer sanzzczdl) {
		this.sanzzczdl = sanzzczdl;
	}

	public void setSizzczdl(Integer sizzczdl) {
		this.sizzczdl = sizzczdl;
	}

	public void setWzzczdl(Integer wzzczdl) {
		this.wzzczdl = wzzczdl;
	}

	public void setZczczdl(Integer zczczdl) {
		this.zczczdl = zczczdl;
	}

	public void setTczzdl(Integer tczzdl) {
		this.tczzdl = tczzdl;
	}

	public void setTcyzdl(Integer tcyzdl) {
		this.tcyzdl = tcyzdl;
	}


	public void setTczdpd(Integer tczdpd) {
		this.tczdpd = tczdpd;
	}

	public Float getTczdl() {
		return tczdl;
	}

	public void setTczdl(Float tczdl) {
		this.tczdl = tczdl;
	}
	
	public Float getTczdxz() {
		return tczdxz;
	}

	public void setTczdxz(Float tczdxz) {
		this.tczdxz = tczdxz;
	}
	
	public void setTczdxz(VehCheckLogin vehCheckLogin,boolean isRoller){
		vehCheckLogin.getZbzl();
		boolean flag = vehCheckLogin.getZzl()<vehCheckLogin.getZbzl()*1.2;
		if(isRoller&&flag){
			this.tczdxz=15f;
		}else {
			this.tczdxz=20f;
		}
		
	}
	
	public void setTczdpd(){
		if(tczdxz==null||tczdl==null){
			return;
		}
		tczdpd=tczdl>=tczdxz?PDJG_HG:PDJG_BHG;
	}

	@Override
	public void setZpd() {
		this.setZpd(tczdpd);
	}
	
	

}
