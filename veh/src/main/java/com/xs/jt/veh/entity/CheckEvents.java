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
 * 事件表
 */
@Scope("prototype")
@Component("events")
@Entity
@Table(name = "TM_CheckEvents")
public class CheckEvents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "jylsh",length=17)
	private String jylsh;
	
	@Column(name = "checkEvent",length=64)
	private String event;
	
	@Column(name = "jyxm",length=64)
	private String jyxm;
	
	@Column(name = "createDate")
	private Date createDate;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "message",length=4000)
	private String message;
	
	@Column(name = "zpzl",length=10)
	private String zpzl;
	
	
	@Column(name = "hphm")
	private String hphm;
	
	@Column(name = "hpzl")
	private String hpzl;
	
	@Column(name = "clsbdh")
	private String clsbdh;
	
	@Column
	private Integer jycs;
	
	
	

	
	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getZpzl() {
		return zpzl;
	}

	public void setZpzl(String zpzl) {
		this.zpzl = zpzl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	


	public String getJylsh() {
		return jylsh;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	public String getJyxm() {
		return jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
