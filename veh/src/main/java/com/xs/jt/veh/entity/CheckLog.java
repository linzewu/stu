package com.xs.jt.veh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 上报日志表
 */
@Scope("prototype")
@Component("checkLog")
@Entity
@Table(name = "TC_CheckLogs")
public class CheckLog  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code",length=64)
	private String code;
	
	@Column(name = "message",length=2000)
	private String message;
	
	@Column(name = "xml",length=8000)
	private String xml;
	
	@Column(name = "bo",length=8000)
	private String bo;
	
	//接口表名称
	@Column(name = "jkbmc",length=128)
	private String jkbmc;
	
	//那一条数据
	@Column(name = "jkbid",length=128)
	private Integer jkbid;
	
	@Column(name = "jylsh",length=128)
	private String jylsh;
	
	@Column(name = "sbsj")
	private Date sbsj;
	
	@Column(name = "hmph")
	private String hmph;
	
	@Column(name = "hpzl")
	private String hpzl;
	

	public String getHmph() {
		return hmph;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHmph(String hmph) {
		this.hmph = hmph;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getBo() {
		return bo;
	}

	public void setBo(String bo) {
		this.bo = bo;
	}

	public Date getSbsj() {
		return sbsj;
	}

	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getJkbmc() {
		return jkbmc;
	}

	public void setJkbmc(String jkbmc) {
		this.jkbmc = jkbmc;
	}

	public Integer getJkbid() {
		return jkbid;
	}

	public void setJkbid(Integer jkbid) {
		this.jkbid = jkbid;
	}

	public String getJylsh() {
		return jylsh;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

}
