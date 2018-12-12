package com.xs.jt.base.module.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.entity.BaseEntity;

/**
 * 签名照
 * @author linzewu
 *
 */
@Scope("prototype")
@Component("signaturePhoto")
@Entity
@Table(name = "TM_SIGNATUREPHOTO")
public class SignaturePhoto extends BaseEntity {
	@Column(length=32)
	private String yhm;
	
	@Lob
	@Column
	private byte[] photo;
	
	//有效期
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
	private Date yxq;
	
	@Transient
	private String imageStr;
	

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getImageStr() {
		return imageStr;
	}

	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}

	public Date getYxq() {
		return yxq;
	}

	public void setYxq(Date yxq) {
		this.yxq = yxq;
	}
	

}
