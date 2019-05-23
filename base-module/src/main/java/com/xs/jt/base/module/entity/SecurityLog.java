package com.xs.jt.base.module.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.annotation.CheckBit;


/**
 * 安全日志
 * @author linzewu
 *
 */
@Scope("prototype")
@Component("securityLog")
@Entity
@Table(name = "TC_SecurityLogs")
@CheckBit
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
	
	/**
	 * 内容
	 */
	@Column(length=2000)
	private String result;
	
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operationDateBegin;
	
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operationDateEnd;
	
	@Column(length = 120)
	private String clbmmc;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getOperationDateBegin() {
		return operationDateBegin;
	}

	public void setOperationDateBegin(Date operationDateBegin) {
		this.operationDateBegin = operationDateBegin;
	}

	public Date getOperationDateEnd() {
		return operationDateEnd;
	}

	public void setOperationDateEnd(Date operationDateEnd) {
		this.operationDateEnd = operationDateEnd;
	}

	public String getClbmmc() {
		return clbmmc;
	}

	public void setClbmmc(String clbmmc) {
		this.clbmmc = clbmmc;
	}

	@Override
	public String toString() {
		return "SecurityLog [ipAddr=" + ipAddr + ", content=" + content + ", userName=" + userName + ", clbm=" + clbm
				+ ", signRed=" + signRed + ", result=" + result +", updateTime=" +this.getUpdateTime()+ "id"+"yhm"+"sfzmhm"+"ygh"+"xm]";
	}
	
	

}
