package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("limitStandard")
@Entity
@Table(name = "TM_LimitStandard")
public class LimitStandard extends BaseEntity {
	
	/**
	 * 检验项目
	 */
	@Column(length=64)
	private String jyxm;
	
	/**
	 * 检测类型
	 */
	@Column(length=64)
	private String jykey;
	
	/**
	 * 合格值
	 */
	@Column(length=64)
	private String hgz;

	public String getJyxm() {
		return jyxm;
	}

	public String getHgz() {
		return hgz;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public String getJykey() {
		return jykey;
	}

	public void setJykey(String jykey) {
		this.jykey = jykey;
	}

	public void setHgz(String hgz) {
		this.hgz = hgz;
	}
	
	

}
