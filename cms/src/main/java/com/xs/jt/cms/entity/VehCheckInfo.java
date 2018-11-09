package com.xs.jt.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("policeCheckInfo")
@Entity
@Table(name = "TM_VHE_CHECKINFO")
public class VehCheckInfo extends BaseEntity{
	
	@Column(length=100)
	private String lsh;
	@Column
	private Integer cycs;
	@Column(length=20)
	private String hphm;
	@Column(length=20)
	private String hpzl;
	@Column(length=100)
	private String syxz;
	@Column(length=20)
	private String xczl;
	@Column(length=100)
	private String clsbdh;
	@Column(length=100)
	private String ywlx;
	@Column(length=100)
	private String jczbh;
	@Column(length=100)
	private String cllx;
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date cysj;
	@Column(length=20)
	private String cyr;
	@Column(length=50)
	private String cyjg;
	@Column(length=500)
	private String bz;
	@Column(length=10)
	private String csys;
	@Column(length=20)
	private String jy1;
	@Column(length=20)
	private String jy2;
	@Column(length=20)
	private String jy3;
	@Column(length=20)
	private String jy4;
	@Column(length=20)
	private String jy5;
	@Column(length=20)
	private String jy6;
	@Column(length=20)
	private String jy7;
	@Column(length=20)
	private String jy8;
	@Column(length=20)
	private String jy9;
	@Column(length=20)
	private String jy10;
	@Column(length=20)
	private String jy11;
	@Column(length=20)
	private String jy12;
	@Column(length=20)
	private String jy13;
	@Column(length=20)
	private String jy14;
	@Column(length=20)
	private String jy15;
	@Column(length=20)
	private String jy16;
	@Column(length=20)
	private String jy17;
	@Column(length=20)
	private String jy18;
	@Column(length=20)
	private String jy19;
	@Column(length=20)
	private String jy20;
	@Column(length=20)
	private String jy21;
	@Column(length=20)
	private String jy22;
	@Column(length=20)
	private String jy23;
	@Column(length=20)
	private String jy24;
	@Column(length=20)
	private String jy25;
	@Column(length=20)
	private String jy26;
	@Column(length=20)
	private String jy27;
	@Column(length=20)
	private String jy28;
	@Column(length=20)
	private String jy29;
	@Column(length=20)
	private String jy30;
	@Column(length=20)
	private String jy31;
	@Column(length=20)
	private String jy32;
	@Column(length=20)
	private String jy33;
	@Column(length=20)
	private String jy34;
	@Column(length=20)
	private String jy35;
	@Column(length=20)
	private String jy36;
	@Column(length=20)
	private String jy37;
	@Column(length=20)
	private String jy38;
	@Column(length=20)
	private String jy39;
	@Column(length=20)
	private String jy40;
	@Column(length=20)
	private String jy41;
	@Column(length=20)
	private String jy42;
	@Column(length=20)
	private String jy43;
	@Column(length=20)
	private String jy44;
	@Column(length=20)
	private String jy45;
	@Column(length=20)
	private String jy46;
	@Column(length=20)
	private String jy47;
	@Column(length=20)
	private String jy48;
	@Column(length=20)
	private String jy49;
	@Column(length=20)
	private String jy50;
	
	//核定载客
	@Column
	private Integer hdzk;
	
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date cysjEnd;
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public Integer getCycs() {
		return cycs;
	}
	public void setCycs(Integer cycs) {
		this.cycs = cycs;
	}
	public Date getCysj() {
		return cysj;
	}
	public void setCysj(Date cysj) {
		this.cysj = cysj;
	}
	public String getCyr() {
		return cyr;
	}
	public void setCyr(String cyr) {
		this.cyr = cyr;
	}
	public String getCyjg() {
		return cyjg;
	}
	public void setCyjg(String cyjg) {
		this.cyjg = cyjg;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getJy1() {
		return jy1;
	}
	public void setJy1(String jy1) {
		this.jy1 = jy1;
	}
	public String getJy2() {
		return jy2;
	}
	public void setJy2(String jy2) {
		this.jy2 = jy2;
	}
	public String getJy3() {
		return jy3;
	}
	public void setJy3(String jy3) {
		this.jy3 = jy3;
	}
	public String getJy4() {
		return jy4;
	}
	public void setJy4(String jy4) {
		this.jy4 = jy4;
	}
	public String getJy5() {
		return jy5;
	}
	public void setJy5(String jy5) {
		this.jy5 = jy5;
	}
	public String getJy6() {
		return jy6;
	}
	public void setJy6(String jy6) {
		this.jy6 = jy6;
	}
	public String getJy7() {
		return jy7;
	}
	public void setJy7(String jy7) {
		this.jy7 = jy7;
	}
	public String getJy8() {
		return jy8;
	}
	public void setJy8(String jy8) {
		this.jy8 = jy8;
	}
	public String getJy9() {
		return jy9;
	}
	public void setJy9(String jy9) {
		this.jy9 = jy9;
	}
	public String getJy10() {
		return jy10;
	}
	public void setJy10(String jy10) {
		this.jy10 = jy10;
	}
	public String getJy11() {
		return jy11;
	}
	public void setJy11(String jy11) {
		this.jy11 = jy11;
	}
	public String getJy12() {
		return jy12;
	}
	public void setJy12(String jy12) {
		this.jy12 = jy12;
	}
	public String getJy13() {
		return jy13;
	}
	public void setJy13(String jy13) {
		this.jy13 = jy13;
	}
	public String getJy14() {
		return jy14;
	}
	public void setJy14(String jy14) {
		this.jy14 = jy14;
	}
	public String getJy15() {
		return jy15;
	}
	public void setJy15(String jy15) {
		this.jy15 = jy15;
	}
	public String getJy16() {
		return jy16;
	}
	public void setJy16(String jy16) {
		this.jy16 = jy16;
	}
	public String getJy17() {
		return jy17;
	}
	public void setJy17(String jy17) {
		this.jy17 = jy17;
	}
	public String getJy18() {
		return jy18;
	}
	public void setJy18(String jy18) {
		this.jy18 = jy18;
	}
	public String getJy19() {
		return jy19;
	}
	public void setJy19(String jy19) {
		this.jy19 = jy19;
	}
	public String getJy20() {
		return jy20;
	}
	public void setJy20(String jy20) {
		this.jy20 = jy20;
	}
	public String getJy21() {
		return jy21;
	}
	public void setJy21(String jy21) {
		this.jy21 = jy21;
	}
	public String getJy22() {
		return jy22;
	}
	public void setJy22(String jy22) {
		this.jy22 = jy22;
	}
	public String getJy23() {
		return jy23;
	}
	public void setJy23(String jy23) {
		this.jy23 = jy23;
	}
	public String getJy24() {
		return jy24;
	}
	public void setJy24(String jy24) {
		this.jy24 = jy24;
	}
	public String getJy25() {
		return jy25;
	}
	public void setJy25(String jy25) {
		this.jy25 = jy25;
	}
	public String getJy26() {
		return jy26;
	}
	public void setJy26(String jy26) {
		this.jy26 = jy26;
	}
	public String getJy27() {
		return jy27;
	}
	public void setJy27(String jy27) {
		this.jy27 = jy27;
	}
	public String getJy28() {
		return jy28;
	}
	public void setJy28(String jy28) {
		this.jy28 = jy28;
	}
	public String getJy29() {
		return jy29;
	}
	public void setJy29(String jy29) {
		this.jy29 = jy29;
	}
	public String getJy30() {
		return jy30;
	}
	public void setJy30(String jy30) {
		this.jy30 = jy30;
	}
	public String getJy31() {
		return jy31;
	}
	public void setJy31(String jy31) {
		this.jy31 = jy31;
	}
	public String getJy32() {
		return jy32;
	}
	public void setJy32(String jy32) {
		this.jy32 = jy32;
	}
	public String getJy33() {
		return jy33;
	}
	public void setJy33(String jy33) {
		this.jy33 = jy33;
	}
	public String getJy34() {
		return jy34;
	}
	public void setJy34(String jy34) {
		this.jy34 = jy34;
	}
	public String getJy35() {
		return jy35;
	}
	public void setJy35(String jy35) {
		this.jy35 = jy35;
	}
	public String getJy36() {
		return jy36;
	}
	public void setJy36(String jy36) {
		this.jy36 = jy36;
	}
	public String getJy37() {
		return jy37;
	}
	public void setJy37(String jy37) {
		this.jy37 = jy37;
	}
	public String getJy38() {
		return jy38;
	}
	public void setJy38(String jy38) {
		this.jy38 = jy38;
	}
	public String getJy39() {
		return jy39;
	}
	public void setJy39(String jy39) {
		this.jy39 = jy39;
	}
	public String getJy40() {
		return jy40;
	}
	public void setJy40(String jy40) {
		this.jy40 = jy40;
	}
	public String getJy41() {
		return jy41;
	}
	public void setJy41(String jy41) {
		this.jy41 = jy41;
	}
	public String getJy42() {
		return jy42;
	}
	public void setJy42(String jy42) {
		this.jy42 = jy42;
	}
	public String getJy43() {
		return jy43;
	}
	public void setJy43(String jy43) {
		this.jy43 = jy43;
	}
	public String getJy44() {
		return jy44;
	}
	public void setJy44(String jy44) {
		this.jy44 = jy44;
	}
	public String getJy45() {
		return jy45;
	}
	public void setJy45(String jy45) {
		this.jy45 = jy45;
	}
	public String getJy46() {
		return jy46;
	}
	public void setJy46(String jy46) {
		this.jy46 = jy46;
	}
	public String getJy47() {
		return jy47;
	}
	public void setJy47(String jy47) {
		this.jy47 = jy47;
	}
	public String getJy48() {
		return jy48;
	}
	public void setJy48(String jy48) {
		this.jy48 = jy48;
	}
	public String getJy49() {
		return jy49;
	}
	public void setJy49(String jy49) {
		this.jy49 = jy49;
	}
	public String getJy50() {
		return jy50;
	}
	public void setJy50(String jy50) {
		this.jy50 = jy50;
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
	public String getSyxz() {
		return syxz;
	}
	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}
	public String getXczl() {
		return xczl;
	}
	public void setXczl(String xczl) {
		this.xczl = xczl;
	}
	public String getClsbdh() {
		return clsbdh;
	}
	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getCllx() {
		return cllx;
	}
	public void setCllx(String cllx) {
		this.cllx = cllx;
	}
	public String getJczbh() {
		return jczbh;
	}
	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}
	public String getCsys() {
		return csys;
	}
	public void setCsys(String csys) {
		this.csys = csys;
	}
	public Date getCysjEnd() {
		return cysjEnd;
	}
	public void setCysjEnd(Date cysjEnd) {
		this.cysjEnd = cysjEnd;
	}
	public Integer getHdzk() {
		return hdzk;
	}
	public void setHdzk(Integer hdzk) {
		this.hdzk = hdzk;
	}
	
	

}
