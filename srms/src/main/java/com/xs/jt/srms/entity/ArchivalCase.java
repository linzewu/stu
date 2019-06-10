package com.xs.jt.srms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

/**
 * 档案格管理
 * 
 * @author admin
 *
 */
@Scope("prototype")
@Component("archivalCase")
@Entity
@Table(name = "tm_Archival_Case")
public class ArchivalCase extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//0：未使用
	public final static String ZT_WSY = "0";
	
	//1：入库
	public final static String ZT_RK = "1";
	//2： 出库
	public final static String ZT_CK = "2";

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
	
	//条码
	@Column
	private String barCode;
	
	//状态
	@Column(length = 1)
	private String zt;	

	@Transient
	private String reason;
	@Transient
	private String remark;
	@Transient
	private Integer caseNumber;
	//是否转移到其他档案架
	@Transient
	private String zyOther;
	//在用车档案入库（档案室原先没有该档案，新分配档案格存放）
	@Transient
	private String usedCarNewCheckIn;

	public String getZyOther() {
		return zyOther;
	}

	public void setZyOther(String zyOther) {
		this.zyOther = zyOther;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(Integer caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getUsedCarNewCheckIn() {
		return usedCarNewCheckIn;
	}

	public void setUsedCarNewCheckIn(String usedCarNewCheckIn) {
		this.usedCarNewCheckIn = usedCarNewCheckIn;
	}

}
