package com.xs.jt.veh.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("checkPhoto")
@Entity
@Table(name = "TM_CheckPhoto")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler","zp"})
public class CheckPhoto extends BaseEntity {
	
	
	@Column(length = 20)
	@NotNull
	private String jyjgbh;
	
	@Column(length = 2)
	@NotNull
	private String jcxdh;
	
	@Column(length = 25)
	@NotNull
	private String jylsh;

	@Column(length = 20)
	@NotNull
	private String hphm;

	@Column(length = 10)
	@NotNull
	private String hpzl;
	
	@Column(length = 30)
	@NotNull
	private String clsbdh;

	@Column
	@NotNull
	private Integer jycs;

	@Lob   
    @Basic(fetch=FetchType.LAZY)   
    @Column(name="zp", nullable=true)  
	private byte[] zp;
	
	@Column
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date pssj;
	
	@Column(length = 20)
	private String jyxm;
	
	@Column(length = 20)
	@NotNull
	private String zpzl;

	public String getJyjgbh() {
		return jyjgbh;
	}

	public String getJcxdh() {
		return jcxdh;
	}

	public String getJylsh() {
		return jylsh;
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

	public Integer getJycs() {
		return jycs;
	}


	public Date getPssj() {
		return pssj;
	}

	public String getJyxm() {
		return jyxm;
	}

	public String getZpzl() {
		return zpzl;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setJcxdh(String jcxdh) {
		this.jcxdh = jcxdh;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
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

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}


	public void setPssj(Date pssj) {
		this.pssj = pssj;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public void setZpzl(String zpzl) {
		this.zpzl = zpzl;
	}

	public byte[] getZp() {
		return zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}

	public CheckPhoto(Integer id,String jyjgbh, String jcxdh, String jylsh, String hphm, String hpzl, String clsbdh, Integer jycs,
			Date pssj, String jyxm, String zpzl) {
		super();
		this.jyjgbh = jyjgbh;
		this.jcxdh = jcxdh;
		this.jylsh = jylsh;
		this.hphm = hphm;
		this.hpzl = hpzl;
		this.clsbdh = clsbdh;
		this.jycs = jycs;
		this.pssj = pssj;
		this.jyxm = jyxm;
		this.zpzl = zpzl;
		this.setId(id);
	}
	

	public CheckPhoto(){
		
	}

}
