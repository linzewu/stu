package com.xs.jt.srms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.entity.BaseEntity;

/**
 * 出入库登记
 * 
 * @author admin
 *
 */
@Scope("prototype")
@Component("archivalRegister")
@Entity
@Table(name = "tm_Archival_Register")
public class ArchivalRegister extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 档案室编号
	@Column
	private String archivesNo;

	// 档案架编号
	@Column
	private String rackNo;

	// 档案架内行号
	@Column
	private Integer rackRow;

	// 档案架内列号
	@Column
	private Integer rackCol;
	
	//档案序号
	@Column
	private String fileNo;

	// 号牌号码
	@Column(length = 30)
	private String hphm;

	// 号牌种类
	@Column(length = 20)
	private String hpzl;

	// 车辆识别代号
	@Column(length = 30)
	private String clsbdh;

	// 业务类型
	@Column(length = 30)
	private String ywlx;

	// 条码
	@Column
	private String barCode;

	// 状态
	@Column(length = 1)
	private String zt;

	// 原因
	@Column(length = 500)
	private String reason;

	// 经办人
	@Column(length = 30)
	private String handleUser;
	
	

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public String getRackNo() {
		return rackNo;
	}

	public void setRackNo(String rackNo) {
		this.rackNo = rackNo;
	}

	public Integer getRackRow() {
		return rackRow;
	}

	public void setRackRow(Integer rackRow) {
		this.rackRow = rackRow;
	}

	public Integer getRackCol() {
		return rackCol;
	}

	public void setRackCol(Integer rackCol) {
		this.rackCol = rackCol;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

}
