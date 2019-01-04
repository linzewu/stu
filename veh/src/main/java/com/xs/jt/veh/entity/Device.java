package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("device")
@Entity
@Table(name = "TM_Device")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler", "decode" })
public class Device extends BaseEntity {

	/**
	 * 制动设备
	 */
	public final static Integer ZDJCSB = 1;

	/**
	 * 灯光设备
	 */
	public final static Integer DGJCSB = 2;

	/**
	 * 速度设备
	 */
	public final static Integer SDJCSB = 3;

	/**
	 * 侧滑设备
	 */
	public final static Integer CHJCSB = 4;

	/**
	 * 称重设备
	 */
	public final static Integer CZJCSB = 5;
	
	/**
	 * 平板设备
	 */
	public final static Integer ZDPBSB = 6;
	
	

	
	public final static Integer GDKG = 90;

	public final static Integer XSP = 91;

	public static final String KEY = "device";

	@Column
	private Integer type;

	@Column(length = 20)
	private String com;

	@Column
	private Integer rate;

	@Column
	private Integer databits;

	@Column
	private Integer stopbits;

	@Column
	private Integer parity;

	@Column
	private Integer jcxxh;

	@Column
	private Integer gw;

	@Column
	private String sbcs;

	@Column
	private String sbxh;

	@Column(length = 8000)
	private String qtxx;
	
	@Column
	private String name;

	@Column
	private String deviceDecode;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceDecode() {
		return deviceDecode;
	}

	public void setDeviceDecode(String deviceDecode) {
		this.deviceDecode = deviceDecode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCom() {
		return com;
	}

	public Integer getRate() {
		return rate;
	}

	public Integer getJcxxh() {
		return jcxxh;
	}

	public Integer getGw() {
		return gw;
	}

	public String getSbcs() {
		return sbcs;
	}

	public String getSbxh() {
		return sbxh;
	}

	public String getQtxx() {
		return qtxx;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public void setJcxxh(Integer jcxxh) {
		this.jcxxh = jcxxh;
	}

	public void setGw(Integer gw) {
		this.gw = gw;
	}

	public void setSbcs(String sbcs) {
		this.sbcs = sbcs;
	}

	public void setSbxh(String sbxh) {
		this.sbxh = sbxh;
	}

	public void setQtxx(String qtxx) {
		this.qtxx = qtxx;
	}

	public Integer getDatabits() {
		return databits;
	}

	public Integer getStopbits() {
		return stopbits;
	}

	public Integer getParity() {
		return parity;
	}

	public void setDatabits(Integer databits) {
		this.databits = databits;
	}

	public void setStopbits(Integer stopbits) {
		this.stopbits = stopbits;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public String getThredKey() {
		return this.getId() + "_" + KEY;
	}

}
