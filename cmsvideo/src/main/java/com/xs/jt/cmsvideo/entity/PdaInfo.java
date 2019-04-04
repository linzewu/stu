package com.xs.jt.cmsvideo.entity;

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
@Component("pdaInfo")
@Entity
@Table(name = "tm_Pda_Info")
public class PdaInfo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//未激活
	public final static Integer ZT_WJH = 0;
	
	//激活
	public final static Integer ZT_JH = 1;
	
	//到期
	public final static Integer ZT_DQ = 2;

	//使用部门 
	@Column
	private String useDept;
	
	//pda品牌
	@Column
	private String brand;
	
	//使用人
	@Column
	private String useBy;
	
	//身份证
	@Column(length=32)
	private String idCard;
	
	//串码（唯一）
	@Column
	private String serialCode;
	
	//状态（激活，未激活，到期）
	@Column(length=1)
	private String zt;
	
	//到期时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
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
