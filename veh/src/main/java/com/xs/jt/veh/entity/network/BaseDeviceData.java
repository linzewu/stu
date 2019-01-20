package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.xs.jt.base.module.entity.BaseEntity;
import com.xs.jt.veh.entity.VehCheckLogin;


@MappedSuperclass
public abstract class BaseDeviceData extends BaseEntity {

	// 判定结果 未检
	public static final Integer PDJG_WJ = 0;

	// 合格
	public static final Integer PDJG_HG = 1;

	// 不合格
	public static final Integer PDJG_BHG = 2;

	/**
	 * 数据状态，正常
	 */
	public static final Integer SJZT_ZC = 0;

	/**
	 * 数据状态，复检
	 */
	public static final Integer SJZT_FJ = 1;

	@Column(length = 25)
	private String jylsh;

	@Column(length = 20)
	private String hphm;

	@Column(length = 10)
	private String hpzl;

	@Column
	private Integer jycs;

	@Column(length = 20)
	private String jyxm;

	@Column
	private Integer sjzt;

	@Column
	private Integer dxcs;
	
	@Column
	private Integer zpd;

	public Integer getDxcs() {
		return dxcs;
	}

	public void setDxcs(Integer dxcs) {
		this.dxcs = dxcs;
	}

	public String getJylsh() {
		return jylsh;
	}

	public String getHphm() {
		return hphm;
	}

	public String getHpzl() {
		return hpzl;
	}

	public Integer getJycs() {
		return jycs;
	}

	public String getJyxm() {
		return jyxm;
	}

	public Integer getSjzt() {
		return sjzt;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public void setSjzt(Integer sjzt) {
		this.sjzt = sjzt;
	}
	

	public Integer getZpd() {
		return zpd;
	}

	public void setZpd(Integer zpd) {
		this.zpd = zpd;
	}
	
	public abstract void setZpd();

	public void setBaseDeviceData(VehCheckLogin vehCheckLoginInfo, Integer dxcs, String jyxm) {
		this.hphm = vehCheckLoginInfo.getHphm();
		this.jycs = vehCheckLoginInfo.getJycs();
		this.hpzl = vehCheckLoginInfo.getHpzl();
		this.jylsh = vehCheckLoginInfo.getJylsh();
		this.dxcs = dxcs;
		this.jyxm = jyxm;
		this.sjzt = BrakRollerData.SJZT_ZC;
	}

}
