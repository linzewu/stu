package com.xs.jt.base.module.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Scope("prototype")
@Component("baseParams")
@Entity
@Table(name = "TB_BaseParams")
public class BaseParams {

	@Id
//	@GenericGenerator(name = "idGenerator", strategy = "identity")
//	@GeneratedValue(generator = "idGenerator")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TID")
	private Integer id;

	@Column(length = 64)
	private String type;
	@Column(length = 128)
	private String paramName;
	@Column(length = 128)
	private String paramValue;
	@Column(length = 8000)
	private String memo;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTime;
	
	@Column
	private Integer seq;
	
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
