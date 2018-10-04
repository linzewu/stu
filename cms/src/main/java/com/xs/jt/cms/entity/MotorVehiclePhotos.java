package com.xs.jt.cms.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("motorVehiclePhotos")
@Entity
@Table(name = "TM_MOTORVEHICLEPHOTOS")
public class MotorVehiclePhotos extends BaseEntity{
	@Column(length=100)
	private String lsh;
	@Column(length=20)
	private String hphm;
	@Column(length=40)
	private String hpzl;
	@Column
	private Integer prid;
	@Column(length=50)
	private String zpzl;
	@Lob
	@Column
	private byte[] photo;
	@Column(length=100)
	private String clsbdh;
	@Column
	private Integer jccs;
	
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
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
	public Integer getPrid() {
		return prid;
	}
	public void setPrid(Integer prid) {
		this.prid = prid;
	}
	public String getZpzl() {
		return zpzl;
	}
	public void setZpzl(String zpzl) {
		this.zpzl = zpzl;
	}	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}	
	public String getClsbdh() {
		return clsbdh;
	}
	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	public Integer getJccs() {
		return jccs;
	}
	public void setJccs(Integer jccs) {
		this.jccs = jccs;
	}
	

}
