package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("externalCheck")
@Entity
@Table(name = "TM_ExternalCheck")
public class ExternalCheck extends BaseEntity {
	
	
	public static final String PD_WJ="0";
	public static final String PD_HG="1";
	public static final String PD_BHG="2";
	
	@Column(length = 25)
	@NotNull
	private String jylsh;

	@Column(length = 20)
	@NotNull
	private String hphm;

	@Column(length = 10)
	@NotNull
	private String hpzl;
	
	@Column(length = 20)
	@NotNull
	private String jyjgbh;

	@Column
	@NotNull
	private Integer jycs;

	@Column(length=2)
	private String item1;
	
	@Column(length=2)
	private String item2;
	
	@Column(length=2)
	private String item3;
	
	@Column(length=2)
	private String item4;
	
	@Column(length=2)
	private String item5;
	
	@Column(length=2)
	private String item6;
	
	@Column(length=2)
	private String item7;
	
	@Column(length=2)
	private String item8;
	
	@Column(length=2)
	private String item9;
	
	@Column(length=2)
	private String item10;
	
	@Column(length=2)
	private String item11;
	
	@Column(length=2)
	private String item12;
	
	@Column(length=2)
	private String item13;
	
	@Column(length=2)
	private String item14;
	
	@Column(length=2)
	private String item15;
	
	@Column(length=2)
	private String item16;
	
	@Column(length=2)
	private String item17;
	
	@Column(length=2)
	private String item18;
	
	@Column(length=2)
	private String item19;
	
	@Column(length=2)
	private String item20;
	
	@Column(length=2)
	private String item21;
	
	@Column(length=2)
	private String item22;
	
	@Column(length=2)
	private String item23;
	
	@Column(length=2)
	private String item24;
	
	@Column(length=2)
	private String item25;
	
	@Column(length=2)
	private String item26;
	
	@Column(length=2)
	private String item27;
	
	@Column(length=2)
	private String item28;
	
	@Column(length=2)
	private String item29;
	
	@Column(length=2)
	private String item30;
	
	@Column(length=2)
	private String item31;
	
	@Column(length=2)
	private String item32;
	
	@Column(length=2)
	private String item33;
	
	@Column(length=2)
	private String item34;
	
	@Column(length=2)
	private String item35;
	
	@Column(length=2)
	private String item36;
	
	@Column(length=2)
	private String item37;
	
	@Column(length=2)
	private String item38;
	
	@Column(length=2)
	private String item39;
	
	@Column(length=2)
	private String item40;
	
	@Column(length=2)
	private String item41;
	
	@Column(length=2)
	private String item42;
	
	@Column(length=2)
	private String item43;
	
	@Column(length=2)
	private String item44;
	
	@Column(length=2)
	private String item45;
	
	@Column(length=2)
	private String item46;
	
	@Column(length=2)
	private String item47;
	
	@Column(length=2)
	private String item48;
	
	@Column(length=2)
	private String item49;
	
	@Column(length=2)
	private String item50;
	
	@Column(length=2)
	private String item80;
	
	@Column(length=30)
	private String wgjcjyy;
	
	@Column(length=30)
	private String wgjcjyysfzh;
	
	@Column(length=30)
	private String dpjcjyy;
	
	@Column(length=30)
	private String dpjyysfzh;
	
	@Column(length=30)
	private String dpdtjyy;
	
	@Column(length=30)
	private String dpdtjyysfzh;
	
	@Column
	private Integer cwkc;
	
	@Column
	private Integer cwkk;
	
	@Column
	private Integer cwkg;
	
	@Column
	private Integer zbzl;
	
	
	

	public Integer getCwkc() {
		return cwkc;
	}

	public Integer getCwkk() {
		return cwkk;
	}

	public Integer getCwkg() {
		return cwkg;
	}

	public Integer getZbzl() {
		return zbzl;
	}

	public void setCwkc(Integer cwkc) {
		this.cwkc = cwkc;
	}

	public void setCwkk(Integer cwkk) {
		this.cwkk = cwkk;
	}

	public void setCwkg(Integer cwkg) {
		this.cwkg = cwkg;
	}

	public void setZbzl(Integer zbzl) {
		this.zbzl = zbzl;
	}

	public String getWgjcjyy() {
		return wgjcjyy;
	}

	public String getWgjcjyysfzh() {
		return wgjcjyysfzh;
	}

	public String getDpjcjyy() {
		return dpjcjyy;
	}

	public String getDpjyysfzh() {
		return dpjyysfzh;
	}

	public String getDpdtjyy() {
		return dpdtjyy;
	}

	public String getDpdtjyysfzh() {
		return dpdtjyysfzh;
	}

	public void setWgjcjyy(String wgjcjyy) {
		this.wgjcjyy = wgjcjyy;
	}

	public void setWgjcjyysfzh(String wgjcjyysfzh) {
		this.wgjcjyysfzh = wgjcjyysfzh;
	}

	public void setDpjcjyy(String dpjcjyy) {
		this.dpjcjyy = dpjcjyy;
	}

	public void setDpjyysfzh(String dpjyysfzh) {
		this.dpjyysfzh = dpjyysfzh;
	}

	public void setDpdtjyy(String dpdtjyy) {
		this.dpdtjyy = dpdtjyy;
	}

	public void setDpdtjyysfzh(String dpdtjyysfzh) {
		this.dpdtjyysfzh = dpdtjyysfzh;
	}

	public String getItem1() {
		return item1;
	}

	public String getItem2() {
		return item2;
	}

	public String getItem3() {
		return item3;
	}

	public String getItem4() {
		return item4;
	}

	public String getItem5() {
		return item5;
	}

	public String getItem6() {
		return item6;
	}

	public String getItem7() {
		return item7;
	}

	public String getItem8() {
		return item8;
	}

	public String getItem9() {
		return item9;
	}

	public String getItem10() {
		return item10;
	}

	public String getItem11() {
		return item11;
	}

	public String getItem12() {
		return item12;
	}

	public String getItem13() {
		return item13;
	}

	public String getItem14() {
		return item14;
	}

	public String getItem15() {
		return item15;
	}

	public String getItem16() {
		return item16;
	}

	public String getItem17() {
		return item17;
	}

	public String getItem18() {
		return item18;
	}

	public String getItem19() {
		return item19;
	}

	public String getItem20() {
		return item20;
	}

	public String getItem21() {
		return item21;
	}

	public String getItem22() {
		return item22;
	}

	public String getItem23() {
		return item23;
	}

	public String getItem24() {
		return item24;
	}

	public String getItem25() {
		return item25;
	}

	public String getItem26() {
		return item26;
	}

	public String getItem27() {
		return item27;
	}

	public String getItem28() {
		return item28;
	}

	public String getItem29() {
		return item29;
	}

	public String getItem30() {
		return item30;
	}

	public String getItem31() {
		return item31;
	}

	public String getItem32() {
		return item32;
	}

	public String getItem33() {
		return item33;
	}

	public String getItem34() {
		return item34;
	}

	public String getItem35() {
		return item35;
	}

	public String getItem36() {
		return item36;
	}

	public String getItem37() {
		return item37;
	}

	public String getItem38() {
		return item38;
	}

	public String getItem39() {
		return item39;
	}

	public String getItem40() {
		return item40;
	}

	public String getItem41() {
		return item41;
	}

	public String getItem42() {
		return item42;
	}

	public String getItem43() {
		return item43;
	}

	public String getItem44() {
		return item44;
	}

	public String getItem45() {
		return item45;
	}

	public String getItem46() {
		return item46;
	}

	public String getItem47() {
		return item47;
	}

	public String getItem48() {
		return item48;
	}

	public String getItem49() {
		return item49;
	}

	public String getItem50() {
		return item50;
	}

	public String getItem80() {
		return item80;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public void setItem7(String item7) {
		this.item7 = item7;
	}

	public void setItem8(String item8) {
		this.item8 = item8;
	}

	public void setItem9(String item9) {
		this.item9 = item9;
	}

	public void setItem10(String item10) {
		this.item10 = item10;
	}

	public void setItem11(String item11) {
		this.item11 = item11;
	}

	public void setItem12(String item12) {
		this.item12 = item12;
	}

	public void setItem13(String item13) {
		this.item13 = item13;
	}

	public void setItem14(String item14) {
		this.item14 = item14;
	}

	public void setItem15(String item15) {
		this.item15 = item15;
	}

	public void setItem16(String item16) {
		this.item16 = item16;
	}

	public void setItem17(String item17) {
		this.item17 = item17;
	}

	public void setItem18(String item18) {
		this.item18 = item18;
	}

	public void setItem19(String item19) {
		this.item19 = item19;
	}

	public void setItem20(String item20) {
		this.item20 = item20;
	}

	public void setItem21(String item21) {
		this.item21 = item21;
	}

	public void setItem22(String item22) {
		this.item22 = item22;
	}

	public void setItem23(String item23) {
		this.item23 = item23;
	}

	public void setItem24(String item24) {
		this.item24 = item24;
	}

	public void setItem25(String item25) {
		this.item25 = item25;
	}

	public void setItem26(String item26) {
		this.item26 = item26;
	}

	public void setItem27(String item27) {
		this.item27 = item27;
	}

	public void setItem28(String item28) {
		this.item28 = item28;
	}

	public void setItem29(String item29) {
		this.item29 = item29;
	}

	public void setItem30(String item30) {
		this.item30 = item30;
	}

	public void setItem31(String item31) {
		this.item31 = item31;
	}

	public void setItem32(String item32) {
		this.item32 = item32;
	}

	public void setItem33(String item33) {
		this.item33 = item33;
	}

	public void setItem34(String item34) {
		this.item34 = item34;
	}

	public void setItem35(String item35) {
		this.item35 = item35;
	}

	public void setItem36(String item36) {
		this.item36 = item36;
	}

	public void setItem37(String item37) {
		this.item37 = item37;
	}

	public void setItem38(String item38) {
		this.item38 = item38;
	}

	public void setItem39(String item39) {
		this.item39 = item39;
	}

	public void setItem40(String item40) {
		this.item40 = item40;
	}

	public void setItem41(String item41) {
		this.item41 = item41;
	}

	public void setItem42(String item42) {
		this.item42 = item42;
	}

	public void setItem43(String item43) {
		this.item43 = item43;
	}

	public void setItem44(String item44) {
		this.item44 = item44;
	}

	public void setItem45(String item45) {
		this.item45 = item45;
	}

	public void setItem46(String item46) {
		this.item46 = item46;
	}

	public void setItem47(String item47) {
		this.item47 = item47;
	}

	public void setItem48(String item48) {
		this.item48 = item48;
	}

	public void setItem49(String item49) {
		this.item49 = item49;
	}

	public void setItem50(String item50) {
		this.item50 = item50;
	}

	public void setItem80(String item80) {
		this.item80 = item80;
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

	public String getJyjgbh() {
		return jyjgbh;
	}

	public Integer getJycs() {
		return jycs;
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

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

}
