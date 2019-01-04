package com.xs.jt.veh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Scope("prototype")
@Component("recordInfoOfCheckStaff")
@Entity
@Table(name = "TM_RecordInfoOfCheckStaff")
public class RecordInfoOfCheckStaff {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id")
	private Integer id;
	
	@Column(length = 18)
	private String sfzmhm;	//身份证明号码
	
	@Column(length = 30)
	private String xm;	//姓名
	@Column(length = 300)
	private String rylb;	//人员类别
	
	@Column(length = 12)
	private String glbm;	//管理部门
	
	@Column(length = 10)
	private String fzjg;	//发证机关
	
	@Column(length = 20)
	private String jczbh;	//检验机构编号
	
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gxrq;	//更新日期
	
	@Column(length = 4000)
	private String bz;	//备注
	
	@Column(length = 1)
	private String shbj;	//审核标记
	
	@Column(length = 4000)
	private String shyj;	//审核意见
	
	@Column(length = 1)
	private String zt;	//状态标记
	
	@Column(length = 32)
	private String sgzbh;	//上岗证编号
	
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date sgzyxqz;	//上岗证有效期止
	
	@Column(length = 300)
	private String sgzffdw;	//上岗证发放单位
	
	@Column(length = 1024)
	private String syglbm;	//使用管理部门

	public Integer getId() {
		return id;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public String getXm() {
		return xm;
	}

	public String getRylb() {
		return rylb;
	}

	public String getGlbm() {
		return glbm;
	}

	public String getFzjg() {
		return fzjg;
	}

	public String getJczbh() {
		return jczbh;
	}

	public Date getGxrq() {
		return gxrq;
	}

	public String getBz() {
		return bz;
	}

	public String getShbj() {
		return shbj;
	}

	public String getShyj() {
		return shyj;
	}

	public String getZt() {
		return zt;
	}

	public String getSgzbh() {
		return sgzbh;
	}

	public Date getSgzyxqz() {
		return sgzyxqz;
	}

	public String getSgzffdw() {
		return sgzffdw;
	}

	public String getSyglbm() {
		return syglbm;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public void setRylb(String rylb) {
		this.rylb = rylb;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}

	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setShbj(String shbj) {
		this.shbj = shbj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public void setSgzbh(String sgzbh) {
		this.sgzbh = sgzbh;
	}

	public void setSgzyxqz(Date sgzyxqz) {
		this.sgzyxqz = sgzyxqz;
	}

	public void setSgzffdw(String sgzffdw) {
		this.sgzffdw = sgzffdw;
	}

	public void setSyglbm(String syglbm) {
		this.syglbm = syglbm;
	}

}
