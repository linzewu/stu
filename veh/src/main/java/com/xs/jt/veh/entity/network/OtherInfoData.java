package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.util.CommonUtil;



@Scope("prototype")
@Component("otherInfoData")
@Entity
@Table(name = "TM_OtherInfoDataOfAnjian")
public class OtherInfoData extends BaseEntity {

	@Column(length = 32)
	private String jylsh;

	@Column(length = 32)
	private String jyjgbh;

	@Column(length = 10)
	private String jcxdh;

	@Column(length = 15)
	private String hphm;

	@Column(length = 2)
	private String hpzl;

	/**
	 * 车辆识别代号
	 */
	@Column(length = 32)
	private String clsbdh;

	/**
	 * 引车员姓名
	 */
	@Column(length = 64)
	private String ycyxm;

	/**
	 * 引车员身份证
	 */
	@Column(length = 32)
	private String ycysfzh;

	/**
	 * 整车制动率
	 */
	@Column
	private Float zczdl;

	/**
	 * 整车制动判定
	 */
	@Column(length = 2)
	private String zczdpd;

	/**
	 * 主车制动检验结果
	 */
	@Column(length = 2)
	private String zczdjyjg;

	/**
	 * 整车判定
	 */
	@Column(length = 2)
	private String zcpd;

	/**
	 * 总检验次数
	 */
	@Column
	private Integer zjccs;

	/**
	 * 检验的整车整备质量
	 */
	@Column
	private Integer jczczbzl;

	/**
	 * 标准的整车整备质量
	 */
	@Column
	private Integer bzzczbzl;

	/**
	 * 整车整备质量百分比
	 */
	@Column
	private Float zczbzlbfb;

	/**
	 * 制动力和
	 */
	@Column
	private Integer zdlh;


	/**
	 * 整车制动率判定
	 */
	@Column
	private Float zczdlxz;
	
	@Column
	private Integer zbzlpd;
	
	@Transient
	private VehCheckLogin vehCheckLoginInfo;



	public Integer getZbzlpd() {
		return zbzlpd;
	}


	public void setZbzlpd(Integer zbzlpd) {
		this.zbzlpd = zbzlpd;
	}


	public Float getZczdlxz() {
		return zczdlxz;
	}


	public void setZczdlxz(Float zczdlxz) {
		this.zczdlxz = zczdlxz;
	}

	public Integer getZdlh() {
		return zdlh;
	}

	public void setZdlh(Integer zdlh) {
		this.zdlh = zdlh;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public String getJcxdh() {
		return jcxdh;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public String getYcyxm() {
		return ycyxm;
	}

	public String getYcysfzh() {
		return ycysfzh;
	}

	public Float getZczdl() {
		return zczdl;
	}

	public String getZczdpd() {
		return zczdpd;
	}

	public String getZczdjyjg() {
		return zczdjyjg;
	}

	public String getZcpd() {
		return zcpd;
	}

	public Integer getZjccs() {
		return zjccs;
	}

	public Integer getJczczbzl() {
		return jczczbzl;
	}

	public Integer getBzzczbzl() {
		return bzzczbzl;
	}

	public Float getZczbzlbfb() {
		return zczbzlbfb;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setJcxdh(String jcxdh) {
		this.jcxdh = jcxdh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public void setYcyxm(String ycyxm) {
		this.ycyxm = ycyxm;
	}

	public void setYcysfzh(String ycysfzh) {
		this.ycysfzh = ycysfzh;
	}

	public void setZczdl(Float zczdl) {
		this.zczdl = zczdl;
	}
	
	public void setZczdl() {
		if(jczczbzl==null||jczczbzl==0){
			return;
		}
		this.zczdl = CommonUtil.MathRound((float) (zdlh/(jczczbzl*0.98))*100);
	}

	public void setZczdpd(String zczdpd) {
		this.zczdpd = zczdpd;
	}

	public void setZczdjyjg(String zczdjyjg) {
		this.zczdjyjg = zczdjyjg;
	}

	public void setZcpd(String zcpd) {
		this.zcpd = zcpd;
	}

	public void setZjccs(Integer zjccs) {
		this.zjccs = zjccs;
	}

	public void setJczczbzl(Integer jczczbzl) {
		this.jczczbzl = jczczbzl;
	}

	public void setBzzczbzl(Integer bzzczbzl) {
		this.bzzczbzl = bzzczbzl;
	}

	public void setZczbzlbfb(Float zczbzlbfb) {
		this.zczbzlbfb = zczbzlbfb;
	}

	public void setBaseInfo(VehCheckLogin vehCheckLoginInfo) {
		this.hphm = vehCheckLoginInfo.getHphm();
		this.hpzl = vehCheckLoginInfo.getHpzl();
		this.jylsh = vehCheckLoginInfo.getJylsh();
		this.clsbdh = vehCheckLoginInfo.getClsbdh();
		this.jcxdh = vehCheckLoginInfo.getJcxdh();
		this.jyjgbh = vehCheckLoginInfo.getJyjgbh();
		this.ycysfzh = vehCheckLoginInfo.getYcysfzh();
		this.ycyxm = vehCheckLoginInfo.getYcy();
		this.vehCheckLoginInfo=vehCheckLoginInfo;
	}

	public void setZczdlxz() {
		this.zczdlxz=60f;
		String cllx =vehCheckLoginInfo.getCllx();
		Integer zzl=vehCheckLoginInfo.getZzl();
		Integer zbzl=vehCheckLoginInfo.getZbzl();
		//专项作业车
		if((cllx.indexOf("Z") == 0)&&zzl<=zbzl*1.2){
			this.zczdlxz = 50f;
		}
	}

	public void setZczdlpd() {
		if(this.zczdlxz==null||this.zczdl==null){
			return;
		}
		this.zczdpd=(zczdl>=zczdlxz?BaseDeviceData.PDJG_HG:BaseDeviceData.PDJG_BHG).toString();
		this.zcpd=zczdpd;
	}

}
