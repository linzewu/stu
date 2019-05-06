package com.xs.jt.cmsvideo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.annotation.CheckBit;
import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("ftpConfig")
@Entity
@Table(name = "tm_Ftp_Config")
@CheckBit
public class FtpConfig extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String jyjgbh;
	@Column(length=30)
	private String ftpHost;
	@Column(length=30)
	private String ftpUserName;
	@Column(length=30)
	private String ftpPassword;
	@Column
	private int ftpPort;
	@Column(length=12)
	private String cyqxh;//	查验区序号
	//容量 单位T
	@Column
	private Integer capacity;
	
	
	public String getJyjgbh() {
		return jyjgbh;
	}
	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getCyqxh() {
		return cyqxh;
	}
	public void setCyqxh(String cyqxh) {
		this.cyqxh = cyqxh;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return "FtpConfig [jyjgbh=" + jyjgbh + ", ftpHost=" + ftpHost + ", ftpUserName=" + ftpUserName
				+ ", ftpPassword=" + ftpPassword + ", ftpPort=" + ftpPort + ", cyqxh=" + cyqxh + ", capacity="
				+ capacity + "]";
	}
	
	
	
	

}
