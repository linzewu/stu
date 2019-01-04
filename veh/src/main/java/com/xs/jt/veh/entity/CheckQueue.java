package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("checkQueue")
@Entity
@Table(name = "TM_CheckQueue")
public class CheckQueue extends BaseEntity {
	
	/**
	 * 检测线代号
	 */
	@Column
	private Integer jcxdh;
	
	/**
	 * 工位顺序
	 */
	@Column
	private Integer gwsx;
	
	/**
	 * 排队序号
	 */
	@Column
	private Integer pdxh;
	
	@Column
	private String jylsh;
	
	@Column
	private Integer jycs;
	
	@Column
	private String hphm;
	
	@Column
	private String hpzl;
	
	@Column
	private Integer lcsx;
	
	@Column(length=20)
	private String jyxm;
	
	
	
	
	public String getJyxm() {
		return jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public Integer getLcsx() {
		return lcsx;
	}

	public void setLcsx(Integer lcsx) {
		this.lcsx = lcsx;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public Integer getJcxdh() {
		return jcxdh;
	}

	public Integer getGwsx() {
		return gwsx;
	}

	public Integer getPdxh() {
		return pdxh;
	}

	public String getJylsh() {
		return jylsh;
	}


	public String getHphm() {
		return hphm;
	}

	public void setJcxdh(Integer jcxdh) {
		this.jcxdh = jcxdh;
	}

	public void setGwsx(Integer gwsx) {
		this.gwsx = gwsx;
	}

	public void setPdxh(Integer pdxh) {
		this.pdxh = pdxh;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}
	
	
	
}
