package com.xs.jt.veh.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.annotation.CheckBit;
import com.xs.jt.base.module.entity.BaseEntity;

import net.sf.json.JSONObject;

@Scope("prototype")
@Component("vehCheckLogin")
@Entity
@Table(name = "TM_VehCheckLogin")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
@CheckBit
public class VehCheckLogin extends BaseEntity implements Serializable {

	/**
	 * 已登录
	 */
	public static final Integer JCZT_DL = 0;
	/**
	 * 检验中
	 */
	public static final Integer JCZT_JYZ = 1;
	/**
	 * 检验结束
	 */
	public static final Integer JCZT_JYJS = 2;

	/**
	 * 退办
	 */
	public static final Integer JCZT_TB = 3;
	

	/**
	 * 未开始
	 */
	public static final Integer ZT_WKS = 0;

	/**
	 * 检验中
	 */
	public static final Integer ZT_JCZ = 1;

	/**
	 * 检验结束
	 */
	public static final Integer ZT_JYJS = 2;
	
	/**
	 * 不检测
	 */
	public static final Integer ZT_BJC=-1;
	
	
	private static final long serialVersionUID = -8255217792287102494L;
	/** 检验流水号 */
	@Column(length = 17, nullable = false)
	private String jylsh;
	/** 检验机构编号 */
	@Column(length = 10, nullable = false)
	private String jyjgbh;
	/** 检测线代号 */
	@Column(length = 2)
	private String jcxdh;
	/** 机动车序号 */
	@Column(length = 14)
	private String xh;
	/** 号牌种类 */
	@Column(length = 2)
	private String hpzl;
	/** 号牌号码 */
	@Column(length = 15)
	private String hphm;
	/** 车辆识别代号 */
	@Column(length = 25, nullable = false)
	private String clsbdh;
	/** 发动机/电动机号码 */
	@Column(length = 30)
	private String fdjh;
	/** 车身颜色 */
	@Column(length = 5)
	private String csys;
	/** 使用性质 */
	@Column(length = 1)
	private String syxz;
	/** 初次登记日期 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date ccdjrq;
	/** 最近定检日期 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date jyrq;
	/** 检验有效期止 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date jyyxqz;
	/** 保险终止日期 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date bxzzrq;
	/** 燃料种类 */
	@Column(length = 3)
	private String rlzl;
	/** 功率 */
	@Column(length = 5, precision = 1)
	private Float gl;
	/** 轴数 */
	@Column(length = 1, nullable = false)
	private Integer zs;
	/** 轴距 */
	@Column(length = 5)
	private Integer zj;
	/** 前轮距 */
	@Column(length = 4)
	private Integer qlj;
	/** 后轮距 */
	@Column(length = 4)
	private Integer hlj;
	/** 总质量 */
	@Column(length = 8, nullable = false)
	private Integer zzl;
	/** 整备质量 */
	@Column(length = 8, nullable = false)
	private Integer zbzl;
	/** 出厂日期 */
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date ccrq;
	/** 驱动形式(驱动轴位) */
	@Column(length = 5)
	private String qdxs;

	/** 驻车轴数 */
	@Column(length = 1)
	private Integer zczs;
	/** 驻车轴位 */
	@Column(length = 5)
	private String zczw;
	/** 主轴数 */
	@Column(length = 1)
	private Integer zzs;
	/** 制动力源 */
	@Column(length = 1)
	private String zzly;
	/** 前照灯制 */
	@Column(length = 2)
	private String qzdz;
	/** 远光单独调整 */
	@Column(length = 1)
	private String ygddtz;
	/** 转向轴（前轴）悬架形式 */
	@Column(length = 1)
	private String zxzxjxs;
	/** 里程表读数 */
	@Column(length = 8)
	private Integer lcbds;
	/** 检验项目 */
	@Column(length = 100, nullable = false)
	private String jyxm;
	/** 检验类别 */
	@Column(length = 2)
	private String jylb;
	/** 不合格项 */
	@Column(length = 50)
	private String bhgx;
	/** 登录时间 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dlsj;
	/** 检验次数 */
	@Column(length = 2)
	private Integer jycs;
	/** 登录员 */
	@Column(length = 30)
	private String dly;
	/** 引车员 */
	@Column(length = 30)
	private String ycy;
	/** 外检员 */
	@Column(length = 30)
	private String wjy;
	/** 动态检验员 */
	@Column(length = 30)
	private String dtjyy;
	/** 底盘检验员 */
	@Column(length = 30)
	private String dpjyy;
	/** 车辆品牌 */
	@Column(length = 32)
	private String clpp1;
	/** 车辆型号 */
	@Column(length = 32)
	private String clxh;
	/** 机动车所有人 */
	@Column(length = 128)
	private String syr;
	/** 车辆类型 */
	@Column(length = 3)
	private String cllx;
	/** 车外廓长 */
	@Column(length = 5)
	private Integer cwkc;
	/** 车外廓宽 */
	@Column(length = 4)
	private Integer cwkk;
	/** 车外廓高 */
	@Column(length = 4)
	private Integer cwkg;
	/** 车辆用途 */
	@Column(length = 2)
	private String clyt;
	/** 用途属性 */
	@Column(length = 1)
	private String ytsx;
	/** 登录员（身份证号） */
	@Column(length = 30)
	private String dlysfzh;
	/** 引车员（身份证号） */
	@Column(length = 30)
	private String ycysfzh;
	/** 外检员（身份证号） */
	@Column(length = 30)
	private String wjysfzh;
	/** 动态检验员（身份证号） */
	@Column(length = 30)
	private String dtjyysfzh;
	/** 底盘检验员（身份证号） */
	@Column(length = 30)
	private String dpjyysfzh;
	/** 车辆所属类别 */
	@Column(length = 2)
	private String clsslb;
	/** 检测线类别 */
	@Column(length = 1)
	private String jcxlb;
	/** 送检人（姓名） */
	@Column(length = 30)
	private String sjr;
	/** 送检人身份证号 */
	@Column(length = 30)
	private String sjrsfzh;
	
	@Column(nullable=false)
	private int vehcsbj;

	@Column
	private Integer hdzk;

	/**
	 * 检测状态
	 */
	@Column
	private Integer vehjczt;

	/**
	 * 上线状态
	 */
	@Column
	private Integer vehsxzt;

	/**
	 * 外检状态
	 */
	@Column
	private Integer vehwjzt;
	
	@Column
	private Integer vehdpzt;
	
	@Column
	private Integer vehdtdpzt;
	
	@Column
	private Integer vehlszt;

	@Column
	private Integer checkType;
	
	//上线时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date upLineDate;
	
	//外检时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-d ", timezone = "GMT+8")
	private Date externalCheckDate;
	
	@Column
	private String jyjl;
	
	@Column(length=200)
	private String fjjyxm;
	
	
	//外廓状态
	@Column
	private Integer vehwkzt;
	
	
	//整备质量状态
	@Column
	private Integer vehzbzlzt;
	
	
	

	public Integer getVehzbzlzt() {
		return vehzbzlzt;
	}


	public void setVehzbzlzt(Integer vehzbzlzt) {
		this.vehzbzlzt = vehzbzlzt;
	}


	public Integer getVehwkzt() {
		return vehwkzt;
	}


	public void setVehwkzt(Integer vehwkzt) {
		this.vehwkzt = vehwkzt;
	}


	public int getVehcsbj() {
		return vehcsbj;
	}


	public void setVehcsbj(int vehcsbj) {
		this.vehcsbj = vehcsbj;
	}


	public String getFjjyxm() {
		return fjjyxm;
	}


	public void setFjjyxm(String fjjyxm) {
		this.fjjyxm = fjjyxm;
	}


	public String getJyjl() {
		return jyjl;
	}


	public void setJyjl(String jyjl) {
		this.jyjl = jyjl;
	}


	public Integer getVehlszt() {
		return vehlszt;
	}


	public void setVehlszt(Integer vehlszt) {
		this.vehlszt = vehlszt;
	}


	public Integer getVehdpzt() {
		return vehdpzt;
	}


	public void setVehdpzt(Integer vehdpzt) {
		this.vehdpzt = vehdpzt;
	}


	public Integer getVehdtdpzt() {
		return vehdtdpzt;
	}


	public void setVehdtdpzt(Integer vehdtdpzt) {
		this.vehdtdpzt = vehdtdpzt;
	}


	public Date getUpLineDate() {
		return upLineDate;
	}

	public Date getExternalCheckDate() {
		return externalCheckDate;
	}

	public void setUpLineDate(Date upLineDate) {
		this.upLineDate = upLineDate;
	}

	public void setExternalCheckDate(Date externalCheckDate) {
		this.externalCheckDate = externalCheckDate;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getVehjczt() {
		return vehjczt;
	}

	public Integer getVehsxzt() {
		return vehsxzt;
	}

	public Integer getVehwjzt() {
		return vehwjzt;
	}

	public void setVehjczt(Integer vehjczt) {
		this.vehjczt = vehjczt;
	}

	public void setVehsxzt(Integer vehsxzt) {
		this.vehsxzt = vehsxzt;
	}

	public void setVehwjzt(Integer vehwjzt) {
		this.vehwjzt = vehwjzt;
	}

	public Integer getHdzk() {
		return hdzk;
	}

	public void setHdzk(Integer hdzk) {
		this.hdzk = hdzk;
	}

	public String getJylsh() {
		return jylsh;
	}

	public void setJylsh(String jylsh) {
		this.jylsh = jylsh;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public String getJcxdh() {
		return jcxdh;
	}

	public void setJcxdh(String jcxdh) {
		this.jcxdh = jcxdh;
	}

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

	public Date getCcdjrq() {
		return ccdjrq;
	}

	public void setCcdjrq(Date ccdjrq) {
		this.ccdjrq = ccdjrq;
	}

	public Date getJyrq() {
		return jyrq;
	}

	public void setJyrq(Date jyrq) {
		this.jyrq = jyrq;
	}

	public Date getJyyxqz() {
		return jyyxqz;
	}

	public void setJyyxqz(Date jyyxqz) {
		this.jyyxqz = jyyxqz;
	}

	public Date getBxzzrq() {
		return bxzzrq;
	}

	public void setBxzzrq(Date bxzzrq) {
		this.bxzzrq = bxzzrq;
	}

	public String getRlzl() {
		return rlzl;
	}

	public void setRlzl(String rlzl) {
		this.rlzl = rlzl;
	}

	public Float getGl() {
		return gl;
	}

	public void setGl(Float gl) {
		this.gl = gl;
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

	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}

	public String getQdxs() {
		return qdxs;
	}

	public void setQdxs(String qdxs) {
		this.qdxs = qdxs;
	}

	public Integer getZczs() {
		return zczs;
	}

	public void setZczs(Integer zczs) {
		this.zczs = zczs;
	}

	public String getZczw() {
		return zczw;
	}

	public void setZczw(String zczw) {
		this.zczw = zczw;
	}

	public Integer getZzs() {
		return zzs;
	}

	public void setZzs(Integer zzs) {
		this.zzs = zzs;
	}

	public String getZzly() {
		return zzly;
	}

	public void setZzly(String zzly) {
		this.zzly = zzly;
	}

	public String getQzdz() {
		return qzdz;
	}

	public void setQzdz(String qzdz) {
		this.qzdz = qzdz;
	}

	public String getYgddtz() {
		return ygddtz;
	}

	public void setYgddtz(String ygddtz) {
		this.ygddtz = ygddtz;
	}

	public String getZxzxjxs() {
		return zxzxjxs;
	}

	public void setZxzxjxs(String zxzxjxs) {
		this.zxzxjxs = zxzxjxs;
	}

	public Integer getLcbds() {
		return lcbds;
	}

	public void setLcbds(Integer lcbds) {
		this.lcbds = lcbds;
	}

	public String getJyxm() {
		return jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public String getJylb() {
		return jylb;
	}

	public void setJylb(String jylb) {
		this.jylb = jylb;
	}

	public String getBhgx() {
		return bhgx;
	}

	public void setBhgx(String bhgx) {
		this.bhgx = bhgx;
	}

	public Date getDlsj() {
		return dlsj;
	}

	public void setDlsj(Date dlsj) {
		this.dlsj = dlsj;
	}

	public Integer getJycs() {
		return jycs;
	}

	public void setJycs(Integer jycs) {
		this.jycs = jycs;
	}

	public String getDly() {
		return dly;
	}

	public void setDly(String dly) {
		this.dly = dly;
	}

	public String getYcy() {
		return ycy;
	}

	public void setYcy(String ycy) {
		this.ycy = ycy;
	}

	public String getWjy() {
		return wjy;
	}

	public void setWjy(String wjy) {
		this.wjy = wjy;
	}

	public String getDtjyy() {
		return dtjyy;
	}

	public void setDtjyy(String dtjyy) {
		this.dtjyy = dtjyy;
	}

	public String getDpjyy() {
		return dpjyy;
	}

	public void setDpjyy(String dpjyy) {
		this.dpjyy = dpjyy;
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

	public String getSyr() {
		return syr;
	}

	public void setSyr(String syr) {
		this.syr = syr;
	}

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
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

	public String getDlysfzh() {
		return dlysfzh;
	}

	public void setDlysfzh(String dlysfzh) {
		this.dlysfzh = dlysfzh;
	}

	public String getYcysfzh() {
		return ycysfzh;
	}

	public void setYcysfzh(String ycysfzh) {
		this.ycysfzh = ycysfzh;
	}

	public String getWjysfzh() {
		return wjysfzh;
	}

	public void setWjysfzh(String wjysfzh) {
		this.wjysfzh = wjysfzh;
	}

	public String getDtjyysfzh() {
		return dtjyysfzh;
	}

	public void setDtjyysfzh(String dtjyysfzh) {
		this.dtjyysfzh = dtjyysfzh;
	}

	public String getDpjyysfzh() {
		return dpjyysfzh;
	}

	public void setDpjyysfzh(String dpjyysfzh) {
		this.dpjyysfzh = dpjyysfzh;
	}

	public String getClsslb() {
		return clsslb;
	}

	public void setClsslb(String clsslb) {
		this.clsslb = clsslb;
	}

	public String getJcxlb() {
		return jcxlb;
	}

	public void setJcxlb(String jcxlb) {
		this.jcxlb = jcxlb;
	}

	public String getSjr() {
		return sjr;
	}

	public void setSjr(String sjr) {
		this.sjr = sjr;
	}

	public String getSjrsfzh() {
		return sjrsfzh;
	}

	public void setSjrsfzh(String sjrsfzh) {
		this.sjrsfzh = sjrsfzh;
	}


	@Override
	public String toString() {
		return "VehCheckLogin [jylsh=" + jylsh + ", jyjgbh=" + jyjgbh + ", jcxdh=" + jcxdh + ", xh=" + xh + ", hpzl="
				+ hpzl + ", hphm=" + hphm + ", clsbdh=" + clsbdh + ", fdjh=" + fdjh + ", csys=" + csys + ", syxz="
				+ syxz + ", rlzl=" + rlzl + ", gl=" + gl + ", zs=" + zs + ", zj=" + zj + ", qlj=" + qlj + ", hlj=" + hlj
				+ ", zzl=" + zzl + ", zbzl=" + zbzl + ", qdxs=" + qdxs + ", zczs=" + zczs + ", zczw=" + zczw + ", zzs="
				+ zzs + ", zzly=" + zzly + ", qzdz=" + qzdz + ", ygddtz=" + ygddtz + ", zxzxjxs=" + zxzxjxs + ", lcbds="
				+ lcbds + ", jyxm=" + jyxm + ", jylb=" + jylb + ", bhgx=" + bhgx + ", jycs=" + jycs + ", dly=" + dly
				+ ", ycy=" + ycy + ", wjy=" + wjy + ", dtjyy=" + dtjyy + ", dpjyy=" + dpjyy + ", clpp1=" + clpp1
				+ ", clxh=" + clxh + ", syr=" + syr + ", cllx=" + cllx + ", cwkc=" + cwkc + ", cwkk=" + cwkk + ", cwkg="
				+ cwkg + ", clyt=" + clyt + ", ytsx=" + ytsx + ", dlysfzh=" + dlysfzh + ", ycysfzh=" + ycysfzh
				+ ", wjysfzh=" + wjysfzh + ", dtjyysfzh=" + dtjyysfzh + ", dpjyysfzh=" + dpjyysfzh + ", clsslb="
				+ clsslb + ", jcxlb=" + jcxlb + ", sjr=" + sjr + ", sjrsfzh=" + sjrsfzh + ", vehcsbj=" + vehcsbj
				+ ", hdzk=" + hdzk + ", vehjczt=" + vehjczt + ", vehsxzt=" + vehsxzt + ", vehwjzt=" + vehwjzt
				+ ", vehdpzt=" + vehdpzt + ", vehdtdpzt=" + vehdtdpzt + ", vehlszt=" + vehlszt + ", checkType="
				+ checkType + ", jyjl=" + jyjl + ", fjjyxm=" + fjjyxm + ", vehwkzt=" + vehwkzt + ", vehzbzlzt="
				+ vehzbzlzt + "]";
	}


	/**@Override
	public String toString() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return super.toString();**/
		/*return "VehCheckLogin [jylsh=" + jylsh + ", jyjgbh=" + jyjgbh + ", jcxdh=" + jcxdh + ", xh=" + xh + ", hpzl="
				+ hpzl + ", hphm=" + hphm + ", clsbdh=" + clsbdh + ", fdjh=" + fdjh + ", csys=" + csys + ", syxz="
				+ syxz + ", ccdjrq=" + ccdjrq + ", jyrq=" + jyrq + ", jyyxqz=" + jyyxqz + ", bxzzrq=" + bxzzrq
				+ ", rlzl=" + rlzl + ", gl=" + gl + ", zs=" + zs + ", zj=" + zj + ", qlj=" + qlj + ", hlj=" + hlj
				+ ", zzl=" + zzl + ", zbzl=" + zbzl + ", ccrq=" + ccrq + ", qdxs=" + qdxs + ", zczs=" + zczs + ", zczw="
				+ zczw + ", zzs=" + zzs + ", zzly=" + zzly + ", qzdz=" + qzdz + ", ygddtz=" + ygddtz + ", zxzxjxs="
				+ zxzxjxs + ", lcbds=" + lcbds + ", jyxm=" + jyxm + ", jylb=" + jylb + ", bhgx=" + bhgx + ", dlsj="
				+ dlsj + ", jycs=" + jycs + ", dly=" + dly + ", ycy=" + ycy + ", wjy=" + wjy + ", dtjyy=" + dtjyy
				+ ", dpjyy=" + dpjyy + ", clpp1=" + clpp1 + ", clxh=" + clxh + ", syr=" + syr + ", cllx=" + cllx
				+ ", cwkc=" + cwkc + ", cwkk=" + cwkk + ", cwkg=" + cwkg + ", clyt=" + clyt + ", ytsx=" + ytsx
				+ ", dlysfzh=" + dlysfzh + ", ycysfzh=" + ycysfzh + ", wjysfzh=" + wjysfzh + ", dtjyysfzh=" + dtjyysfzh
				+ ", dpjyysfzh=" + dpjyysfzh + ", clsslb=" + clsslb + ", jcxlb=" + jcxlb + ", sjr=" + sjr + ", sjrsfzh="
				+ sjrsfzh + ", hdzk=" + hdzk + ", vehjczt=" + vehjczt + ", vehsxzt=" + vehsxzt + ", vehwjzt=" + vehwjzt
				+ ", vehdpzt=" + vehdpzt + ", vehdtdpzt=" + vehdtdpzt + ", vehlszt=" + vehlszt + ", checkType="
				+ checkType + ", upLineDate=" + upLineDate + ", externalCheckDate=" + externalCheckDate + "]";*/
	//}
	
	

}
