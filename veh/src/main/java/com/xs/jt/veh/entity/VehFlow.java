package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("vehFlow")
@Entity
@Table(name = "TM_VehFlow")
public class VehFlow {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id")
	private Integer id;
	
	@Column(length=20)
	private String jylsh;
	
	@Column(length=20)
	private String hphm;
	
	@Column(length=10)
	private String hpzl;
	
	@Column
	private Integer jycs;
	
	@Column
	private Integer sx;
	
	@Column
	private Integer gw;
	
	@Column
	private Integer gwsx;
	
	@Column
	private Integer sbsx;
	
	@Column
	private Integer jysb;
	
	@Column
	private String jyxm;
	
	@Column(length=400)
	private String memo;
	
	@Column
	private Integer sbid;
	

	
	public Integer getSbid() {
		return sbid;
	}

	public void setSbid(Integer sbid) {
		this.sbid = sbid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getGwsx() {
		return gwsx;
	}

	public Integer getSbsx() {
		return sbsx;
	}

	public void setGwsx(Integer gwsx) {
		this.gwsx = gwsx;
	}

	public void setSbsx(Integer sbsx) {
		this.sbsx = sbsx;
	}

	public Integer getJysb() {
		return jysb;
	}

	public void setJysb(Integer jysb) {
		this.jysb = jysb;
	}

	public String getJyxm() {
		return jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public Integer getId() {
		return id;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getHphm() {
		return hphm;
	}


	public Integer getJycs() {
		return jycs;
	}

	public Integer getSx() {
		return sx;
	}

	public Integer getGw() {
		return gw;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}


	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public void setSx(Integer sx) {
		this.sx = sx;
	}

	public void setGw(Integer gw) {
		this.gw = gw;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

}
