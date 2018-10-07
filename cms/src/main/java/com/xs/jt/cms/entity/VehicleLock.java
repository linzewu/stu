package com.xs.jt.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("vehicleLock")
@Entity
@Table(name = "TM_VEHICLELOCK")
public class VehicleLock extends BaseEntity{
	
	public static String SDFS_AUTO = "auto";
	public static String SDFS_BYHAND = "byhand";
	@Column(length=100)
	private String clsbdh;
	@Column(length=20)
	private String sdr;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Column
	private Date sdsj;
	@Column(length=1)
	private String sdzt;
	@Column(length=20)
	private String jsr;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Column
	private Date jssj;
	@Column(length=300)
	private String jsyy;
	@Column(length=300)
	private String sdyy;
	@Column(length=20)
	private String sdfs;
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
	public String getSdfs() {
		return sdfs;
	}
	public void setSdfs(String sdfs) {
		this.sdfs = sdfs;
	}

}
