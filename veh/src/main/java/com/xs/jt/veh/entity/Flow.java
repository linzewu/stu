package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("flow")
@Entity
@Table(name = "TM_Flow")
public class Flow extends BaseEntity {
	
	public static final Integer JZZDT_YES=1;
	
	public static final Integer JZZDT_NO=0;

	/**
	 * 安检
	 */
	public final static Integer JCLX_AJ = 0;

	/**
	 * 综检
	 */
	public final static Integer JCLX_ZJ = 1;

	/**
	 * 环检
	 */
	public final static Integer JCLX_HJ = 2;
	
	/**
	 * 称重制动交叉 是
	 */
	public final static Integer CZZDJC_YES=1;
	
	/**
	 *  称重制动交叉 否
	 */
	public final static Integer CZZDJC_NO=0;

	/**
	 * 检测线代号
	 */
	@Column
	private Integer jcxdh;
	
	/**
	 * 检测 流程类型
	 */
	@Column
	private Integer jclclx;

	/**
	 * 检测流程名称
	 */
	@Column
	private String jclcmc;
	
	/**
	 * 称重制动交叉检测
	 */
	@Column
	private Integer czzdjc;
	
	/**
	 * 显示屏
	 */
	@Column
	private Integer displayId;
	
	
	
	/**
	 * 是否有 加载制动台
	 */
	@Column
	private Integer jzzdt;
	
	
	
	@Column(length=4000)
	private String flow;
	
	




	public Integer getJzzdt() {
		return jzzdt;
	}


	public void setJzzdt(Integer jzzdt) {
		this.jzzdt = jzzdt;
	}


	public String getFlow() {
		return flow;
	}


	public void setFlow(String flow) {
		this.flow = flow;
	}

	public Integer getCzzdjc() {
		return czzdjc;
	}




	public void setCzzdjc(Integer czzdjc) {
		this.czzdjc = czzdjc;
	}




	public Integer getDisplayId() {
		return displayId;
	}




	public void setDisplayId(Integer displayId) {
		this.displayId = displayId;
	}


	public Integer getJcxdh() {
		return jcxdh;
	}


	public String getJclcmc() {
		return jclcmc;
	}


	public void setJcxdh(Integer jcxdh) {
		this.jcxdh = jcxdh;
	}


	public void setJclcmc(String jclcmc) {
		this.jclcmc = jclcmc;
	}


	public Integer getJclclx() {
		return jclclx;
	}


	public void setJclclx(Integer jclclx) {
		this.jclclx = jclclx;
	}
 
	
}
