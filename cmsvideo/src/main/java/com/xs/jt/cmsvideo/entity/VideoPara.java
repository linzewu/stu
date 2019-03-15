package com.xs.jt.cmsvideo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
/**
 * 第六章与查验过程音视频管理系统通讯协议
 * @author admin
 *
 */
@Scope("prototype")
@Component("videoPara")
@Entity
@Table(name = "tm_VideoPara")
public class VideoPara extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=13)
	private String cylsh; //	查验流水号
	
	@Column(length=12)
	private String cyqxh;//	查验区序号
	
	@Column(length=15)
	private String cyqtd;//	查验区通道
	
	@Column(length=2)
	private String cllx;//	处理类型  0—查验开始，1—查验结束
	
	@Column(length=20)
	private String cysj;//	查验时间
	
	@Column(length=25)
	private String cycs;//	查验次数
	
	@Column(length=20)
	private String sfzmhm;//	查验员身份证明号码
	
	@Column(length=10)
	private String hphm;//	号牌号码
	
	@Column(length=10)
	private String hpzl;//	号牌种类
	
	@Column(length=20)
	private String clsbdh;//	车辆识别代号

	public String getCylsh() {
		return cylsh;
	}

	public void setCylsh(String cylsh) {
		this.cylsh = cylsh;
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

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getCysj() {
		return cysj;
	}

	public void setCysj(String cysj) {
		this.cysj = cysj;
	}

	public String getCycs() {
		return cycs;
	}

	public void setCycs(String cycs) {
		this.cycs = cycs;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
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

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

}
