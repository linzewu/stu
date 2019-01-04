package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("insurance")
@Entity
@Table(name = "TM_Insurance")
public class Insurance  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column
	private String jylsh;
	
	@Column
	private String jyjgbh;
	
	@Column
	private String jcxdh;
	
	@Column
	private String hpzl;
	
	@Column
	private String hphm;
	
	@Column
	private String clsbdh;
	
	@Column
	private String bxpzh;
	
	@Column
	private String bxje;
	
	@Column
	private String bxgs;
	
	@Column
	private String sxrq;
	
	@Column
	private String zzrq;

	public Integer getId() {
		return id;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public String getJcxdh() {
		return jcxdh;
	}

	public String getHpzl() {
		return hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public String getBxpzh() {
		return bxpzh;
	}

	public String getBxje() {
		return bxje;
	}

	public String getBxgs() {
		return bxgs;
	}

	public String getSxrq() {
		return sxrq;
	}

	public String getZzrq() {
		return zzrq;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setJcxdh(String jcxdh) {
		this.jcxdh = jcxdh;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public void setBxpzh(String bxpzh) {
		this.bxpzh = bxpzh;
	}

	public void setBxje(String bxje) {
		this.bxje = bxje;
	}

	public void setBxgs(String bxgs) {
		this.bxgs = bxgs;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}
	
	

}
