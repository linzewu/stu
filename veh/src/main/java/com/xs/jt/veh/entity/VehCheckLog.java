package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("vehCheckLog")
@Entity
@Table(name = "TM_VehCheckLog")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler"})
public class VehCheckLog extends BaseEntity {
	
	@Column(length=4000)
	private String bo;
	
	@Column(length=4000)
	private String fhxml;
	
	@Column(length=30)
	private String hphm;
	
	@Column(length=10)
	private String hpzl;
	
	@Column(length=30)
	private String jylsh;
	
	@Column(length=30)
	private String clsbdh;
	
	@Column(length=10)
	private String code;
	
	@Column(length=4000)
	private String messager;
	
	

	public String getMessager() {
		return messager;
	}

	public void setMessager(String messager) {
		this.messager = messager;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBo() {
		return bo;
	}

	public String getFhxml() {
		return fhxml;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setBo(String bo) {
		this.bo = bo;
	}

	public void setFhxml(String fhxml) {
		this.fhxml = fhxml;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	
}
