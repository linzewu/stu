package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("workPoint")
@Entity
@Table(name = "TM_workPoint")
public class WorkPoint extends BaseEntity {
	
	public final static Integer ISUSE_YES=1;
	
	public final static Integer ISUSE_NO=0;
	
	public final static Integer GWZT_TY=0;
	
	public final static Integer GWZT_QY=1;
	
	
	public final static String THREAD_PREFIX="GWXC";

	@Column
	private Integer isUse;
	
	@Column
	private Integer gwzt;
	
	@Column
	private String name;

	@Column
	private Integer sort;
	
	@Column
	private String jylsh;
	
	@Column
	private Integer jycs;

	@Column
	private String hphm;
	
	@Column
	private String hpzl;
	
	@Column
	private Integer jcxdh;
	
	
	public String getThreadKey(){
		return THREAD_PREFIX+"_"+this.getId();
	}
	
	public Integer getGwzt() {
		return gwzt;
	}

	public void setGwzt(Integer gwzt) {
		this.gwzt = gwzt;
	}


	

	public Integer getJcxdh() {
		return jcxdh;
	}

	public void setJcxdh(Integer jcxdh) {
		this.jcxdh = jcxdh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}


	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}



	public String getJylsh() {
		return jylsh;
	}


	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}
	
	public String getWorkThreadKey(){
		return "WorkPoint_"+this.getId();
	}
	

}
