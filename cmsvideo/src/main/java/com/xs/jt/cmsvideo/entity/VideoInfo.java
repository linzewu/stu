package com.xs.jt.cmsvideo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.annotation.CheckBit;
import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("videoInfo")
@Entity
@Table(name = "tm_Video_Info")
@CheckBit
public class VideoInfo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//0：为下载
	public final static Integer ZT_WXZ=0;
	//下载中
	public final static Integer ZT_XZZ=1;
	//已下载
	public final static Integer ZT_YXZ=2;
	//转码上传中
	public final static Integer ZT_ZMSCZ=3;
	//结束
	public final static Integer ZT_JS=4;
	//下载失败
	public final static Integer ZT_XZSB=5;
	//转码上传失败
	public final static Integer ZT_ZMSCSB=6;
	
	@Column
	private String jyjgbh;
	@Column(length=20)
	private String hphm;
	@Column(length=20)
	private String hpzl;
	@Column(length=100)
	private String clsbdh;
	@Column(length = 128)
	private String lsh;
	@Column
	private Integer jycs;
//	@Column(length=30)
//	private String jyxm;
	//状态（0：为下载；1：下载中；2：已下载；3：转码上传中；4：结束；5：下载失败；6：转码上传失败）
	@Column
	private Integer zt;
	//失败原因
	@Column(length=2000)
	private String reason;
	//任务执行次数
	@Column
	private Integer taskCount;
	
	/**
	 * 视频开始时间
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date videoBegin;
	
	/**
	 * 视频结束时间
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date videoEnd;
	
	/**
	 * 视频名称
	 */
	@Column(length=60)
	private String videoName;
	
	/**
	 * 视频大小
	 */
	@Column
	private Long videoSize;
	
	@Column(length=12)
	private String cyqxh;//	查验区序号
	
	@Column(length=15)
	private String cyqtd;//	查验区通道
	
	private Integer configId;  //videoConfig  id
	@Column
	private String deviceName;
	
	@Column
	private String sfzmhm;

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public Date getVideoBegin() {
		return videoBegin;
	}

	public void setVideoBegin(Date videoBegin) {
		this.videoBegin = videoBegin;
	}

	public Date getVideoEnd() {
		return videoEnd;
	}

	public void setVideoEnd(Date videoEnd) {
		this.videoEnd = videoEnd;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Long getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(Long videoSize) {
		this.videoSize = videoSize;
	}

	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
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

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}
	
	public String getJyjgbh() {
		return jyjgbh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	@Override
	public String toString() {
		return "VideoInfo [jyjgbh=" + jyjgbh + ", hphm=" + hphm + ", hpzl=" + hpzl + ", clsbdh=" + clsbdh + ", lsh="
				+ lsh + ", jycs=" + jycs + ", zt=" + zt + ", reason=" + reason + ", taskCount=" + taskCount
				+ ", videoName=" + videoName + ", videoSize=" + videoSize + ", cyqxh=" + cyqxh + ", cyqtd=" + cyqtd
				+ ", configId=" + configId + ", deviceName=" + deviceName + ", sfzmhm=" + sfzmhm + "id"+"yhm"+"sfzmhm"+"ygh"+"xm]";
	}
	
	

}
