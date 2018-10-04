package com.xs.jt.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("motorVehicleBusinessLock")
@Entity
@Table(name = "TM_MOTORVEHICLEBUSINESSLOCK")
public class MotorVehicleBusinessLock extends BaseEntity{
	@Column(length=100)
	private String clsbdh;
	@Column(length=20)
	private String sdr;
	@Column
	private Date sdsj;
	@Column(length=1)
	private String sdzt;
	@Column(length=20)
	private String jsr;
	@Column
	private Date jssj;
	@Column(length=300)
	private String jsyy;
	@Column(length=300)
	private String sdyy;
	public String getClsbdh() {
		return clsbdh;
	}
	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	public String getSdr() {
		return sdr;
	}
	public void setSdr(String sdr) {
		this.sdr = sdr;
	}
	public Date getSdsj() {
		return sdsj;
	}
	public void setSdsj(Date sdsj) {
		this.sdsj = sdsj;
	}
	public String getSdzt() {
		return sdzt;
	}
	public void setSdzt(String sdzt) {
		this.sdzt = sdzt;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public Date getJssj() {
		return jssj;
	}
	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}
	public String getJsyy() {
		return jsyy;
	}
	public void setJsyy(String jsyy) {
		this.jsyy = jsyy;
	}
	public String getSdyy() {
		return sdyy;
	}
	public void setSdyy(String sdyy) {
		this.sdyy = sdyy;
	}

}
