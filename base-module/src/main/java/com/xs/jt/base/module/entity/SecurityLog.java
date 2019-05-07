package com.xs.jt.base.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 安全日志
 * @author linzewu
 *
 */
@Scope("prototype")
@Component("securityLog")
@Entity
@Table(name = "TC_SecurityLogs")
public class SecurityLog extends BaseEntity {
	
	/**
	 * 地址
	 */
	@Column(length=20)
	private String ipAddr;
	
	/**
	 * 内容
	 */
	@Column(length=2000)
	private String content;
	
	@Column(length=30)
	private String userName;
	
	@Column(length = 50)
	private String clbm;
	
	//是否标红
	@Column(length=1)
	private String signRed;

	public String getIpAddr() {
		return ipAddr;
	}

	public String getContent() {
		return content;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public String getClbm() {
		return clbm;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setClbm(String clbm) {
		this.clbm = clbm;
	}

	public String getSignRed() {
		return signRed;
	}

	public void setSignRed(String signRed) {
		this.signRed = signRed;
	}
	
	

}
