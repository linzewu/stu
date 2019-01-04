package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("deviceMotion")
@Entity
@Table(name = "TM_DeviceMotion")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler"})
public class DeviceMotion extends BaseEntity {
	
	@Column
	private Integer type;
	
	@Column(length=100)
	private String actionName;
	
	@Column(length=100)
	private String protocol;


	public String getActionName() {
		return actionName;
	}

	public String getProtocol() {
		return protocol;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
