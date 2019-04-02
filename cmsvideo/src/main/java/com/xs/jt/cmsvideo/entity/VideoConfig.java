package com.xs.jt.cmsvideo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("videoConfig")
@Entity
@Table(name = "tm_Video_Config")
public class VideoConfig extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String jyjgbh;
	
	//@Column
	//private String jcxdh;
	
	@Column
	private String ip;
	
	@Column
	private String port;
	
	//后端端口
	@Column
	private String hdPort;
	
	@Column
	private String userName;
	
	@Column
	private String password;
	
	@Column
	private int channel;
	
//	@Column
//	private String jyxm;
	
	@Column
	private String deviceName;
	
	@Column(length=12)
	private String cyqxh;//	查验区序号
	
	@Column(length=15)
	private String cyqtd;//	查验区通道

	public String getJyjgbh() {
		return jyjgbh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	//public String getJcxdh() {
	//	return jcxdh;
	//}

	///public void setJcxdh(String jcxdh) {
	//	this.jcxdh = jcxdh;
	//}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCyqxh() {
		return cyqxh;
	}

	public void setCyqxh(String cyqxh) {
		this.cyqxh = cyqxh;
	}

	public String getCyqtd() {
		return cyqtd;
	}

	public void setCyqtd(String cyqtd) {
		this.cyqtd = cyqtd;
	}

	public String getHdPort() {
		return hdPort;
	}

	public void setHdPort(String hdPort) {
		this.hdPort = hdPort;
	}
	

}
