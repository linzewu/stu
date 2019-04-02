package com.xs.jt.cmsvideo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("pdaInfo")
@Entity
@Table(name = "tm_Pda_Info")
public class PdaInfo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//使用部门 
	private String useDept;
	
	//pda品牌
	private String brand;
	
	//使用人
	private String useBy;
	
	//身份证
	private String idCard;
	
	//串码（唯一）
	private String serialCode;
	
	//状态（激活，未激活，到期）
	private String zt;
	
	//到期时间
	private Date dueTime;

	public String getUseDept() {
		return useDept;
	}

	public void setUseDept(String useDept) {
		this.useDept = useDept;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUseBy() {
		return useBy;
	}

	public void setUseBy(String useBy) {
		this.useBy = useBy;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

}
