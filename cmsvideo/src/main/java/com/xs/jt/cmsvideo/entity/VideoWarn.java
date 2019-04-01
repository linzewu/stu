package com.xs.jt.cmsvideo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("videoWarn")
@Entity
@Table(name = "tm_Video_Warn")
public class VideoWarn extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 视频时间低于秒数
	public final static String SPYJ_SJDY = "1";
	// 视频时间高于秒数
	public final static String SPYJ_SJGY = "2";
	// 视频大小低于兆数
	public final static String SPYJ_DXDY = "3";
	// 视频大小高于兆数
	public final static String SPYJ_DXGY = "4";
	
	@Column(length = 128)
	private String lsh;
	@Column
	private Integer jycs;
	@Column
	private Integer vid;
	@Column
	private Integer type;
	@Column
	private String remark;
	@Column
	private String deviceName;
	@Column
	private String jyjgbh;

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

}
