package com.xs.jt.hwvideo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("vehInfo")
@Entity
@Table(name = "tm_veh_Info")
public class VehInfo extends BaseEntity {
	
	public static Integer STATUS_JC=1;
	
	public static Integer STATUS_CS=2;
	
	private static final long serialVersionUID = 1L;
	@Column
	private String hphm;
	
	@Column
	private Integer status;
	
	@Column(length=4000)
	private String jsonData;
	
	@Column
	private Date jckssj;
	
	@Column
	private Date jcjssj;
	
	@Column
	private Date cckssj;
	
	@Column
	private Date ccjssj;
	
	@Column(length=128)
	private String inRecordId;
	
	@Column(length=128)
	private String outRecordId;
	
	@Column
	private Float inWeight;
	
	@Column
	private Float outWeight;
	
	@Column
	private Date inWeightDate;
	
	@Column
	private Date outWeightDate;
	
	@Column
	private String billNo;
	
	@Column
	private Integer inVideoStatus;
	
	@Column
	private Integer outVideoStatus;
	
	

	public Integer getInVideoStatus() {
		return inVideoStatus;
	}

	public void setInVideoStatus(Integer inVideoStatus) {
		this.inVideoStatus = inVideoStatus;
	}

	public Integer getOutVideoStatus() {
		return outVideoStatus;
	}

	public void setOutVideoStatus(Integer outVideoStatus) {
		this.outVideoStatus = outVideoStatus;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getInWeightDate() {
		return inWeightDate;
	}

	public void setInWeightDate(Date inWeightDate) {
		this.inWeightDate = inWeightDate;
	}

	public Date getOutWeightDate() {
		return outWeightDate;
	}

	public void setOutWeightDate(Date outWeightDate) {
		this.outWeightDate = outWeightDate;
	}

	public Float getInWeight() {
		return inWeight;
	}

	public void setInWeight(Float inWeight) {
		this.inWeight = inWeight;
	}

	public Float getOutWeight() {
		return outWeight;
	}

	public void setOutWeight(Float outWeight) {
		this.outWeight = outWeight;
	}

	public String getInRecordId() {
		return inRecordId;
	}

	public void setInRecordId(String inRecordId) {
		this.inRecordId = inRecordId;
	}

	public String getOutRecordId() {
		return outRecordId;
	}

	public void setOutRecordId(String outRecordId) {
		this.outRecordId = outRecordId;
	}

	public Date getJckssj() {
		return jckssj;
	}

	public void setJckssj(Date jckssj) {
		this.jckssj = jckssj;
	}

	public Date getJcjssj() {
		return jcjssj;
	}

	public void setJcjssj(Date jcjssj) {
		this.jcjssj = jcjssj;
	}

	public Date getCckssj() {
		return cckssj;
	}

	public void setCckssj(Date cckssj) {
		this.cckssj = cckssj;
	}

	public Date getCcjssj() {
		return ccjssj;
	}

	public void setCcjssj(Date ccjssj) {
		this.ccjssj = ccjssj;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	
	
	

}
