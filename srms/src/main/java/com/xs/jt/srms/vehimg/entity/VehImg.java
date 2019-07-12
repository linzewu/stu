package com.xs.jt.srms.vehimg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Scope("prototype")
@Component("tmVehImg")
@Entity
@Table(name = "TM_VEH_IMG", schema = "ARC_VEH_IMG")
public class VehImg {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id")
	private Integer id;
	
	@Column(length=50)
	private String lsh;
	
	@Column(length=10)
	private String ywlx;
	
	@Column(length=50)
	private String clsbdh;
	
	@Column(length=50)
	private String hphm;
	
	@Column(length=10)
	private String hpzl;
	
	@Column(length=10)
	private String zplx;
	
	@Column
	private Date pssj;
	
	@Lob
	//@Basic(fetch = FetchType.LAZY)
	@Column(name = "ZP", columnDefinition = "BLOB", nullable = true)
	@JsonIgnore
	private byte[] zp;
	
	@Column(length=10)
	private String zpzt;
	
	
	@Transient
	private MultipartFile remoteFile;

	public VehImg(Integer id, String lsh, String ywlx, String clsbdh, String hphm, String hpzl, String zplx,
			Date pssj) {
		super();
		this.id = id;
		this.lsh = lsh;
		this.ywlx = ywlx;
		this.clsbdh = clsbdh;
		this.hphm = hphm;
		this.hpzl = hpzl;
		this.zplx = zplx;
		this.pssj = pssj;
	}
	
	public VehImg(){};
	
	

	public String getZpzt() {
		return zpzt;
	}

	public void setZpzt(String zpzt) {
		this.zpzt = zpzt;
	}

	public String getZplx() {
		return zplx;
	}

	public void setZplx(String zplx) {
		this.zplx = zplx;
	}

	public byte[] getZp() {
		return zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}


	public Date getPssj() {
		return pssj;
	}


	public void setPssj(Date pssj) {
		this.pssj = pssj;
	}


	public String getLsh() {
		return lsh;
	}

	public String getYwlx() {
		return ywlx;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}


	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MultipartFile getRemoteFile() {
		return remoteFile;
	}

	public void setRemoteFile(MultipartFile remoteFile) {
		this.remoteFile = remoteFile;
	}
	
	
	
}
