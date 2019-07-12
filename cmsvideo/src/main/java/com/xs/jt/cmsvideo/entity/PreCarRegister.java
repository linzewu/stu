package com.xs.jt.cmsvideo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("preCarRegister")
@Entity
@Table(name = "TM_PRE_CAR_REGISTER")
public class PreCarRegister extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "HGZBH", length = 64)
	private String hgzbh;
	
	//取的是发证日期
	@Column(name = "CCRQ", length = 64)
	private String ccrq;
	
	@Column(name = "ZZCMC", length = 64)
	private String zzcmc;
	
	@Column(name = "CLPP1", length = 64)
	private String clpp1;
	
	@Column(name = "CLPP2", length = 64)
	private String clpp2;
	
	@Column(name = "FDJXH", length = 64)
	private String fdjxh;
	
	@Column(name = "RLZL", length = 64)
	private String rlzl;
	
	@Column(name = "PL", length = 64)
	private String pl;
	
	@Column(name = "GL", length = 64)
	private String gl;
	
	@Column(name = "ZXXS", length = 64)
	private String zxxs;
	
	@Column(name = "LTS", length = 64)
	private String lts;
	
	@Column(name = "GBTHPS", length = 64)
	private String gbthps;
	
	@Column(name = "ZS", length = 64)
	private String zs;
	
	@Column(name = "HXNBCD", length = 64)
	private String hxnbcd;
	
	@Column(name = "HXNBKD", length = 64)
	private String hxnbkd;
	
	@Column(name = "HXNBGD", length = 64)
	private String hxnbgd;
	
	@Column(name = "ZZL", length = 64)
	private String zzl;
	
	@Column(name = "HDZZL", length = 64)
	private String hdzzl;
	
	@Column(name = "ZBZL", length = 64)
	private String zbzl;
	
	@Column(name = "ZQYZL", length = 64)
	private String zqyzl;
	
	@Column(name = "QPZK", length = 64)
	private String qpzk;
	
	@Column(name = "BZ", length = 500)
	private String bz;
	
	@Column(name = "CLXH", length = 64)
	private String clxh;
	
	@Column(name = "CLLX", length = 64)
	private String cllx;
	
	@Column(name = "CLSBDH", length = 128)
	private String clsbdh;
	
	@Column(name = "CSYS", length = 64)
	private String csys;
	
	@Column(name = "HPZL", length = 64)
	private String hpzl;
	
	@Column(name = "YWLX", length = 64)
	private String ywlx;
	
	@Column(name = "SYR", length = 128)
	private String syr;
	
	@Column(name = "SFZ", length = 64)
	private String sfz;
	
	@Column(name = "DZ", length = 500)
	private String dz;
	
	@Column(name = "HPHM", length = 128)
	private String hphm;
	
	@Column(name = "HDZK", length = 128)
	private String hdzk;
	
	@Column(name = "GGBH", length = 128)
	private String ggbh;
	
	@Column(name = "SYXZ", length = 128)
	private String syxz;
	
	@Column(name = "FDJH", length = 128)
	private String fdjh;
	
	@Column(name = "QLJ", length = 128)
	private String qlj;
	
	@Column(name = "HLJ", length = 128)
	private String hlj;
	
	@Column(name = "ZJ", length = 128)
	private String zj;
	
	@Column(name="CWKC",length = 128)
	private String cwkc;
	
	@Column(name="CWKK",length = 128)
	private String cwkk;
	
	@Column(name="CWKG",length = 128)
	private String cwkg;
	
	@Column(name="LSH",length = 128,updatable=false)
	private String lsh;
	
	@Column(name="LTGG",length = 128)
	private String ltgg;
	
	@Column(name="HPZK",length = 64)
	private String hpzk;
	
	@Column(name="HBDBQK",length = 64)
	private String hbdbqk;
	
	@Column(name="GCJK",length = 64)
	private String gcjk;
	
	@Column(name="DPID",length = 64)
	private String dpid;
	
	@Column(name="ZZG",length = 64)
	private String zzg;
	
	@Column(name="stationCode",length = 64,updatable=false)
	private String stationCode;
	
	//手机号码
	@Column(name="SJHM",length = 20)
	private String sjhm;
//	//是否校车
//	@Column(name="VEH_SFXC",length = 1)
//	private String veh_sfxc;
	
	@Transient
	private boolean sdzt;
	
	@Transient
	private String bh;
	
	

	public String getStationCode() {
		return stationCode;
	}


	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}


	public String getZzg() {
		return zzg;
	}


	public void setZzg(String zzg) {
		this.zzg = zzg;
	}


	public String getGcjk() {
		return gcjk;
	}


	public void setGcjk(String gcjk) {
		this.gcjk = gcjk;
	}


	public String getDpid() {
		return dpid;
	}


	public void setDpid(String dpid) {
		this.dpid = dpid;
	}


	public String getHpzk() {
		return hpzk;
	}


	public void setHpzk(String hpzk) {
		this.hpzk = hpzk;
	}


	public String getHbdbqk() {
		return hbdbqk;
	}


	public void setHbdbqk(String hbdbqk) {
		this.hbdbqk = hbdbqk;
	}


	public String getLtgg() {
		return ltgg;
	}
	
	
	public void setLtgg(String ltgg) {
		this.ltgg = ltgg;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}


	public String getCwkc() {
		return cwkc;
	}

	public void setCwkc(String cwkc) {
		this.cwkc = cwkc;
	}

	public String getCwkk() {
		return cwkk;
	}

	public void setCwkk(String cwkk) {
		this.cwkk = cwkk;
	}

	

	public String getCwkg() {
		return cwkg;
	}

	public void setCwkg(String cwkg) {
		this.cwkg = cwkg;
	}

	public String getQlj() {
		return qlj;
	}

	public void setQlj(String qlj) {
		this.qlj = qlj;
	}

	public String getHlj() {
		return hlj;
	}

	public void setHlj(String hlj) {
		this.hlj = hlj;
	}

	public String getZj() {
		return zj;
	}

	public void setZj(String zj) {
		this.zj = zj;
	}

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getClxh() {
		return clxh;
	}

	public void setClxh(String clxh) {
		this.clxh = clxh;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getCsys() {
		return csys;
	}

	public void setCsys(String csys) {
		this.csys = csys;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	

	public String getSyr() {
		return syr;
	}

	public void setSyr(String syr) {
		this.syr = syr;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getHdzk() {
		return hdzk;
	}

	public void setHdzk(String hdzk) {
		this.hdzk = hdzk;
	}

	public String getGgbh() {
		return ggbh;
	}

	public void setGgbh(String ggbh) {
		this.ggbh = ggbh;
	}

	public String getSyxz() {
		return syxz;
	}

	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}

	public String getFdjh() {
		return fdjh;
	}

	public void setFdjh(String fdjh) {
		this.fdjh = fdjh;
	}

	public String getHgzbh() {
		return hgzbh;
	}

	public void setHgzbh(String hgzbh) {
		this.hgzbh = hgzbh;
	}

	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	public String getZzcmc() {
		return zzcmc;
	}

	public void setZzcmc(String zzcmc) {
		this.zzcmc = zzcmc;
	}

	public String getClpp1() {
		return clpp1;
	}

	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}

	public String getClpp2() {
		return clpp2;
	}

	public void setClpp2(String clpp2) {
		this.clpp2 = clpp2;
	}

	public String getFdjxh() {
		return fdjxh;
	}

	public void setFdjxh(String fdjxh) {
		this.fdjxh = fdjxh;
	}

	public String getRlzl() {
		return rlzl;
	}

	public void setRlzl(String rlzl) {
		this.rlzl = rlzl;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getZxxs() {
		return zxxs;
	}

	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
	}

	public String getLts() {
		return lts;
	}

	public void setLts(String lts) {
		this.lts = lts;
	}

	public String getGbthps() {
		return gbthps;
	}

	public void setGbthps(String gbthps) {
		this.gbthps = gbthps;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getHxnbcd() {
		return hxnbcd;
	}

	public void setHxnbcd(String hxnbcd) {
		this.hxnbcd = hxnbcd;
	}

	public String getHxnbkd() {
		return hxnbkd;
	}

	public void setHxnbkd(String hxnbkd) {
		this.hxnbkd = hxnbkd;
	}

	public String getHxnbgd() {
		return hxnbgd;
	}

	public void setHxnbgd(String hxnbgd) {
		this.hxnbgd = hxnbgd;
	}

	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}

	public String getHdzzl() {
		return hdzzl;
	}

	public void setHdzzl(String hdzzl) {
		this.hdzzl = hdzzl;
	}

	public String getZbzl() {
		return zbzl;
	}

	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}

	public String getZqyzl() {
		return zqyzl;
	}

	public void setZqyzl(String zqyzl) {
		this.zqyzl = zqyzl;
	}

	public String getQpzk() {
		return qpzk;
	}

	public void setQpzk(String qpzk) {
		this.qpzk = qpzk;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}


	public boolean isSdzt() {
		return sdzt;
	}


	public void setSdzt(boolean sdzt) {
		this.sdzt = sdzt;
	}


	public String getSjhm() {
		return sjhm;
	}


	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}


	public String getBh() {
		return bh;
	}


	public void setBh(String bh) {
		this.bh = bh;
	}


//	public String getVeh_sfxc() {
//		return veh_sfxc;
//	}
//
//
//	public void setVeh_sfxc(String veh_sfxc) {
//		this.veh_sfxc = veh_sfxc;
//	}
	
	
}
