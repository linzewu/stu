package com.xs.jt.base.module.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Scope("prototype")
@Component("user")
@Entity
@Table(name = "TB_User")
public class User extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static Integer ZT_TY=2;
	
	public final static Integer ZT_CZMM=0;
	
	public final static Integer ZT_ZC=1;
	
	public static final String SYSTEM_USER="system";
	
	@Column(length=32)
	private String bmdm;
	
	@Column(length=32)
	private String yhxm;
	
	@Column(length=32,unique=true)
	private String yhm;
	
	@Column(length=32)
	private String mm;
	
	@Column(length=32)
	private String sfzh;
	
	@Column(length=32)
	private String gh;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
	private Date mmyxq;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
	private Date zhyxq;
	
	@Column(length=32)
	private String ipqsdz;
	
	@Column(length=32)
	private String ipjsdz;
	
	@Column(length=1000)
	private String gdip;
	
	@Column
	private Integer zt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Column
	private Date zjdlsj;
	
	@Column
	private Integer qxms;
	
	@Column(length=2000)
	private String js;
	
	@Column(length=8000)
	private String qx;
	
	//允许登录时间(开始)
	@Column(length=10)
	private String permitBeginTime;
	
	//允许登录时间(截止)
	@Column(length=10)
	private String permitEndTime;
	
	@Column
	private Character isPolice;
	
	@Column
	private Integer loginFailCou;
	
	//本次登陆ip
	@Column(length=30)
	private String ip;
	
	//上次登录的时间
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastTimeLoginDate;
	
	//上次登录的IP终端
	@Column(length=30)
	private String lastTimeIP;
	
	//上次登录失败的时间
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastTimeLoginFailDate;
	
	//上次登录失败的IP终端
	@Column(length=30)
	private String lastTimeLoginFailIP;
	
	@Transient
	private String pwOverdue;
	@Transient
	private String bmmc;
	@Transient
	@JsonIgnore
	private MultipartFile qmFile;

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getJs() {
		return js;
	}

	public String getQx() {
		return qx;
	}


	public void setJs(String js) {
		this.js = js;
	}

	public void setQx(String qx) {
		this.qx = qx;
	}

	public String getBmdm() {
		return bmdm;
	}

	public String getYhxm() {
		return yhxm;
	}

	public String getYhm() {
		return yhm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public String getGh() {
		return gh;
	}

	public Date getMmyxq() {
		return mmyxq;
	}

	public Date getZhyxq() {
		return zhyxq;
	}

	public String getIpqsdz() {
		return ipqsdz;
	}

	public String getIpjsdz() {
		return ipjsdz;
	}

	public String getGdip() {
		return gdip;
	}


	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public void setMmyxq(Date mmyxq) {
		this.mmyxq = mmyxq;
	}

	public void setZhyxq(Date zhyxq) {
		this.zhyxq = zhyxq;
	}

	public void setIpqsdz(String ipqsdz) {
		this.ipqsdz = ipqsdz;
	}

	public void setIpjsdz(String ipjsdz) {
		this.ipjsdz = ipjsdz;
	}

	public void setGdip(String gdip) {
		this.gdip = gdip;
	}

	public Integer getZt() {
		return zt;
	}

	public Date getZjdlsj() {
		return zjdlsj;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public void setZjdlsj(Date zjdlsj) {
		this.zjdlsj = zjdlsj;
	}

	public Integer getQxms() {
		return qxms;
	}

	public void setQxms(Integer qxms) {
		this.qxms = qxms;
	}

	public String getPermitBeginTime() {
		return permitBeginTime;
	}

	public String getPermitEndTime() {
		return permitEndTime;
	}

	public void setPermitBeginTime(String permitBeginTime) {
		this.permitBeginTime = permitBeginTime;
	}

	public void setPermitEndTime(String permitEndTime) {
		this.permitEndTime = permitEndTime;
	}

	public Character getIsPolice() {
		return isPolice;
	}

	public Integer getLoginFailCou() {
		return loginFailCou;
	}

	public String getIp() {
		return ip;
	}

	public Date getLastTimeLoginDate() {
		return lastTimeLoginDate;
	}

	public String getLastTimeIP() {
		return lastTimeIP;
	}

	public Date getLastTimeLoginFailDate() {
		return lastTimeLoginFailDate;
	}

	public String getLastTimeLoginFailIP() {
		return lastTimeLoginFailIP;
	}

	public void setIsPolice(Character isPolice) {
		this.isPolice = isPolice;
	}

	public void setLoginFailCou(Integer loginFailCou) {
		this.loginFailCou = loginFailCou;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setLastTimeLoginDate(Date lastTimeLoginDate) {
		this.lastTimeLoginDate = lastTimeLoginDate;
	}

	public void setLastTimeIP(String lastTimeIP) {
		this.lastTimeIP = lastTimeIP;
	}

	public void setLastTimeLoginFailDate(Date lastTimeLoginFailDate) {
		this.lastTimeLoginFailDate = lastTimeLoginFailDate;
	}

	public void setLastTimeLoginFailIP(String lastTimeLoginFailIP) {
		this.lastTimeLoginFailIP = lastTimeLoginFailIP;
	}

	public String getPwOverdue() {
		return pwOverdue;
	}

	public void setPwOverdue(String pwOverdue) {
		this.pwOverdue = pwOverdue;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String encodePwd(String password) {
		if(StringUtils.isEmpty(password)) {
			return null;
		}else {
			try {
				return md5(this.getId().toString()+password+this.yhxm);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public MultipartFile getQmFile() {
		return qmFile;
	}

	public void setQmFile(MultipartFile qmFile) {
		this.qmFile = qmFile;
	}
	
}
	
	
