package com.xs.jt.veh.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("vehInfo")
@Entity
@Table(name = "TM_VehInfo")
public class VehInfo extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3077135966542772509L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;
	
	@Column(length=14)
	private String xh;
	
	@Column(length=2)
	private String hpzl;
	
	@Column(length=15)
	private String hphm;
	
	@Column(length=32)
	private String clpp1;
	
	@Column(length=32)
	private String clxh;
	@Column(length=32)
	private String clpp2;
	
	@Column(length=1)
	private String gcjk;
	
	@Column(length=3)
	private String zzg;
	
	@Column(length=64)
	private String zzcmc;
	
	@Column(length=25)
	private String clsbdh;
	
	@Column(length=30)
	private String fdjh;
	
	@Column(length=3)
	private String cllx;
	
	@Column(length=5)
	private String csys;
	
	@Column(length=1)
	private String syxz;
	
	@Column(length=18)
	private String sfzmhm;
	
	@Column(length=1)
	private String sfzmmc;
	
	@Column(length=128)
	private String syr;
	
	@Column
	private Date ccdjrq;
	
	@Column
	private Date djrq;
	
	@Column
	private Date yxqz;
	
	@Column
	private Date qzbfqz;
	
	@Column(length=10)
	private String fzjg;
	
	@Column(length=10)
	private String glbm;
	
	@Column
	private Date bxzzrq;
	
	@Column(length=6)
	private String zt;
	
	@Column(length=1)
	private String dybj;
	
	@Column(length=64)
	private String fdjxh;
	
	@Column(length=3)
	private String rlzl;
	
	@Column(length=6)
	private Integer pl;
	
	@Column(length=5,precision=1)
	private Float gl;
	
	@Column(length=1)
	private String zxxs;
	
	@Column(length=5)
	private Integer cwkc;
	
	@Column(length=4)
	private Integer cwkk;
	
	@Column(length=4)
	private Integer cwkg;
	
	@Column(length=5)
	private Integer hxnbcd;
	@Column(length=4)
	private Integer hxnbkd;
	@Column(length=4)
	private Integer hxnbgd;
	
	@Column(length=3)
	private Integer gbthps;
	
	@Column(length=1)
	private Integer zs;
	
	@Column(length=5)
	private Integer zj;
	
	@Column(length=4)
	private Integer qlj;
	
	@Column(length=4)
	private Integer hlj;
	
	@Column(length=64)
	private String ltgg;
	
	@Column(length=2)
	private Integer lts;
	
	@Column(length=8)
	private Integer zzl;
	
	@Column(length=8)
	private Integer zbzl;
	
	@Column(length=8)
	private Integer hdzzl;
	@Column(length=3)
	private Integer hdzk;
	
	@Column(length=8)
	private Integer zqyzl;
	
	@Column(length=1)
	private Integer qpzk;
	
	@Column(length=2)
	private Integer hpzk;
	
	@Column(length=128)
	private String hbdbqk;
	
	@Column
	private Date ccrq;
	
	@Column(length=2)
	private String clyt;
	
	@Column(length=1)
	private String ytsx;
	
	@Column(length=20)
	private String xszbh;
	
	@Column(length=20)
	private String jyhgbzbh;
	
	@Column(length=10)
	private String xzqh;
	
	@Column(length=10)
	private String zsxzqh;
	
	@Column(length=10)
	private String zzxzqh;
	
	@Column(length=4000)
	private String sgcssbwqk;
	
	@Column(length=1)
	private String sfmj;
	
	@Column(length=4000)
	private String bmjyy;

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getClpp1() {
		return clpp1;
	}

	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}

	public String getClxh() {
		return clxh;
	}

	public void setClxh(String clxh) {
		this.clxh = clxh;
	}

	public String getClpp2() {
		return clpp2;
	}

	public void setClpp2(String clpp2) {
		this.clpp2 = clpp2;
	}

	public String getGcjk() {
		return gcjk;
	}

	public void setGcjk(String gcjk) {
		this.gcjk = gcjk;
	}

	public String getZzg() {
		return zzg;
	}

	public void setZzg(String zzg) {
		this.zzg = zzg;
	}

	public String getZzcmc() {
		return zzcmc;
	}

	public void setZzcmc(String zzcmc) {
		this.zzcmc = zzcmc;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getFdjh() {
		return fdjh;
	}

	public void setFdjh(String fdjh) {
		this.fdjh = fdjh;
	}

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getCsys() {
		return csys;
	}

	public void setCsys(String csys) {
		this.csys = csys;
	}

	public String getSyxz() {
		return syxz;
	}

	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public String getSfzmmc() {
		return sfzmmc;
	}

	public void setSfzmmc(String sfzmmc) {
		this.sfzmmc = sfzmmc;
	}

	public String getSyr() {
		return syr;
	}

	public void setSyr(String syr) {
		this.syr = syr;
	}

	public Date getCcdjrq() {
		return ccdjrq;
	}

	public void setCcdjrq(Date ccdjrq) {
		this.ccdjrq = ccdjrq;
	}

	public Date getDjrq() {
		return djrq;
	}

	public void setDjrq(Date djrq) {
		this.djrq = djrq;
	}

	public Date getYxqz() {
		return yxqz;
	}

	public void setYxqz(Date yxqz) {
		this.yxqz = yxqz;
	}

	public Date getQzbfqz() {
		return qzbfqz;
	}

	public void setQzbfqz(Date qzbfqz) {
		this.qzbfqz = qzbfqz;
	}

	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public Date getBxzzrq() {
		return bxzzrq;
	}

	public void setBxzzrq(Date bxzzrq) {
		this.bxzzrq = bxzzrq;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getDybj() {
		return dybj;
	}

	public void setDybj(String dybj) {
		this.dybj = dybj;
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

	public Integer getPl() {
		return pl;
	}

	public void setPl(Integer pl) {
		this.pl = pl;
	}

	public Float getGl() {
		return gl;
	}

	public void setGl(Float gl) {
		this.gl = gl;
	}

	public String getZxxs() {
		return zxxs;
	}

	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
	}

	public Integer getCwkc() {
		return cwkc;
	}

	public void setCwkc(Integer cwkc) {
		this.cwkc = cwkc;
	}

	public Integer getCwkk() {
		return cwkk;
	}

	public void setCwkk(Integer cwkk) {
		this.cwkk = cwkk;
	}

	public Integer getCwkg() {
		return cwkg;
	}

	public void setCwkg(Integer cwkg) {
		this.cwkg = cwkg;
	}

	public Integer getHxnbcd() {
		return hxnbcd;
	}

	public void setHxnbcd(Integer hxnbcd) {
		this.hxnbcd = hxnbcd;
	}

	public Integer getHxnbkd() {
		return hxnbkd;
	}

	public void setHxnbkd(Integer hxnbkd) {
		this.hxnbkd = hxnbkd;
	}

	public Integer getHxnbgd() {
		return hxnbgd;
	}

	public void setHxnbgd(Integer hxnbgd) {
		this.hxnbgd = hxnbgd;
	}

	public Integer getGbthps() {
		return gbthps;
	}

	public void setGbthps(Integer gbthps) {
		this.gbthps = gbthps;
	}

	public Integer getZs() {
		return zs;
	}

	public void setZs(Integer zs) {
		this.zs = zs;
	}

	public Integer getZj() {
		return zj;
	}

	public void setZj(Integer zj) {
		this.zj = zj;
	}

	public Integer getQlj() {
		return qlj;
	}

	public void setQlj(Integer qlj) {
		this.qlj = qlj;
	}

	public Integer getHlj() {
		return hlj;
	}

	public void setHlj(Integer hlj) {
		this.hlj = hlj;
	}

	public String getLtgg() {
		return ltgg;
	}

	public void setLtgg(String ltgg) {
		this.ltgg = ltgg;
	}

	public Integer getLts() {
		return lts;
	}

	public void setLts(Integer lts) {
		this.lts = lts;
	}

	public Integer getZzl() {
		return zzl;
	}

	public void setZzl(Integer zzl) {
		this.zzl = zzl;
	}

	public Integer getZbzl() {
		return zbzl;
	}

	public void setZbzl(Integer zbzl) {
		this.zbzl = zbzl;
	}

	public Integer getHdzzl() {
		return hdzzl;
	}

	public void setHdzzl(Integer hdzzl) {
		this.hdzzl = hdzzl;
	}

	public Integer getHdzk() {
		return hdzk;
	}

	public void setHdzk(Integer hdzk) {
		this.hdzk = hdzk;
	}

	public Integer getZqyzl() {
		return zqyzl;
	}

	public void setZqyzl(Integer zqyzl) {
		this.zqyzl = zqyzl;
	}

	public Integer getQpzk() {
		return qpzk;
	}

	public void setQpzk(Integer qpzk) {
		this.qpzk = qpzk;
	}

	public Integer getHpzk() {
		return hpzk;
	}

	public void setHpzk(Integer hpzk) {
		this.hpzk = hpzk;
	}

	public String getHbdbqk() {
		return hbdbqk;
	}

	public void setHbdbqk(String hbdbqk) {
		this.hbdbqk = hbdbqk;
	}

	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}

	public String getClyt() {
		return clyt;
	}

	public void setClyt(String clyt) {
		this.clyt = clyt;
	}

	public String getYtsx() {
		return ytsx;
	}

	public void setYtsx(String ytsx) {
		this.ytsx = ytsx;
	}

	public String getXszbh() {
		return xszbh;
	}

	public void setXszbh(String xszbh) {
		this.xszbh = xszbh;
	}

	public String getJyhgbzbh() {
		return jyhgbzbh;
	}

	public void setJyhgbzbh(String jyhgbzbh) {
		this.jyhgbzbh = jyhgbzbh;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getZsxzqh() {
		return zsxzqh;
	}

	public void setZsxzqh(String zsxzqh) {
		this.zsxzqh = zsxzqh;
	}

	public String getZzxzqh() {
		return zzxzqh;
	}

	public void setZzxzqh(String zzxzqh) {
		this.zzxzqh = zzxzqh;
	}

	public String getSgcssbwqk() {
		return sgcssbwqk;
	}

	public void setSgcssbwqk(String sgcssbwqk) {
		this.sgcssbwqk = sgcssbwqk;
	}

	public String getSfmj() {
		return sfmj;
	}

	public void setSfmj(String sfmj) {
		this.sfmj = sfmj;
	}

	public String getBmjyy() {
		return bmjyy;
	}

	public void setBmjyy(String bmjyy) {
		this.bmjyy = bmjyy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
