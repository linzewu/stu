package com.xs.jt.veh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.axis2.context.externalize.SafeSerializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("roadCheck")
@Entity
@Table(name = "TM_RoadCheck")
public class RoadCheck implements SafeSerializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column
	private String jylsh;
	@Column
	private String jyjgbh;
	@Column
	private String jcxdh;
	@Column
	private String jycs;
	@Column
	private String jyxm;
	@Column
	private String hpzl;
	@Column
	private String hphm;
	@Column
	private String clsbdh;
	@Column
	private String lsy;
	@Column
	private Float zdcsd;
	@Column
	private Float zdxtsj;
	@Column
	private String zdwdx;
	@Column
	private Float xckzzdjl;
	@Column
	private  Float xcmzzdjl;
	@Column
	private Float xckzmfdd;
	@Column
	private Float xcmzmfdd;
	@Column
	private Integer xczdczlz;
	@Column
 	private Integer lszdpd;
	@Column
	private Integer yjzdcsd;
	@Column
	private Float yjkzzdjl;
	@Column
	private  Float yjkzmfdd;
	@Column
	private Float yjmzzdjl;
	@Column
	private Float yjmzmfdd;
	@Column
	private Integer yjzdczlfs;
	@Column
	private Integer yjzdczlz;
	@Column
	private Integer yjzdpd;
	@Column
	private Integer zcpd;
	@Column
	private Integer lszczdpd;
	@Column
	private Float csdscz;
	@Column
	private Integer csbpd;
	@Column
	private Integer lsjg;
	
	//路试初速度判断
	@Column
	private Integer lscsdpd;
	
	//路试除速度限制
	@Column
	private Integer lscsdxz;
	
	//路试空载制动距离限制
	@Column
	private Float lskzzdjlxz;
	
	@Column
	private Integer lskzzdjlpd;
	
	//路试满载制动距离限制
	@Column
	private Float lsmzzdjlxz;
	
	@Column
	private Integer lsmzzdjlpd;
	
	
	
	//路试空载制动距离限制
	@Column
	private Float lskzmfddxz;
	
	@Column
	private Integer lskzmfddpd;
	
	//路试满载制动距离限制
	@Column
	private Float lsmzmfddxz;
	
	@Column
	private Integer lsmzmfddpd;
	
	@Column
	private Float lsxtsjxz;

	@Column
	private Integer lsxtsjpd;
	
	
	
	
	public Float getLskzmfddxz() {
		return lskzmfddxz;
	}
	public Integer getLskzmfddpd() {
		return lskzmfddpd;
	}
	public Float getLsmzmfddxz() {
		return lsmzmfddxz;
	}
	public Integer getLsmzmfddpd() {
		return lsmzmfddpd;
	}
	public Float getLsxtsjxz() {
		return lsxtsjxz;
	}
	public void setLskzmfddxz(Float lskzmfddxz) {
		this.lskzmfddxz = lskzmfddxz;
	}
	public void setLskzmfddpd(Integer lskzmfddpd) {
		this.lskzmfddpd = lskzmfddpd;
	}
	public void setLsmzmfddxz(Float lsmzmfddxz) {
		this.lsmzmfddxz = lsmzmfddxz;
	}
	public void setLsmzmfddpd(Integer lsmzmfddpd) {
		this.lsmzmfddpd = lsmzmfddpd;
	}
	public void setLsxtsjxz(Float lsxtsjxz) {
		this.lsxtsjxz = lsxtsjxz;
	}
	public Float getLskzzdjlxz() {
		return lskzzdjlxz;
	}
	public Integer getLskzzdjlpd() {
		return lskzzdjlpd;
	}
	public Float getLsmzzdjlxz() {
		return lsmzzdjlxz;
	}
	public Integer getLsmzzdjlpd() {
		return lsmzzdjlpd;
	}
	public void setLskzzdjlxz(Float lskzzdjlxz) {
		this.lskzzdjlxz = lskzzdjlxz;
	}
	public void setLskzzdjlpd(Integer lskzzdjlpd) {
		this.lskzzdjlpd = lskzzdjlpd;
	}
	public void setLsmzzdjlxz(Float lsmzzdjlxz) {
		this.lsmzzdjlxz = lsmzzdjlxz;
	}
	public void setLsmzzdjlpd(Integer lsmzzdjlpd) {
		this.lsmzzdjlpd = lsmzzdjlpd;
	}
	public Integer getLscsdxz() {
		return lscsdxz;
	}
	public void setLscsdxz(Integer lscsdxz) {
		this.lscsdxz = lscsdxz;
	}
	public Integer getId() {
		return id;
	}
	public Integer getLscsdpd() {
		return lscsdpd;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLscsdpd(Integer lscsdpd) {
		this.lscsdpd = lscsdpd;
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
	public String getJycs() {
		return jycs;
	}
	public String getJyxm() {
		return jyxm;
	}
	public String getHpzl() {
		return hpzl;
	}
	public String getHphm() {
		return hphm;
	}
	public String getClsbdh() {
		return clsbdh;
	}
	public String getLsy() {
		return lsy;
	}
	public Float getZdxtsj() {
		return zdxtsj;
	}
	public String getZdwdx() {
		return zdwdx;
	}
	public Float getXckzmfdd() {
		return xckzmfdd;
	}
	public Float getXcmzmfdd() {
		return xcmzmfdd;
	}
	public Integer getXczdczlz() {
		return xczdczlz;
	}
	public Integer getLszdpd() {
		return lszdpd;
	}
	public Integer getYjzdcsd() {
		return yjzdcsd;
	}
	public Float getYjkzmfdd() {
		return yjkzmfdd;
	}
	public Float getYjmzmfdd() {
		return yjmzmfdd;
	}
	public Integer getYjzdczlfs() {
		return yjzdczlfs;
	}
	public Integer getYjzdczlz() {
		return yjzdczlz;
	}
	public Integer getYjzdpd() {
		return yjzdpd;
	}
	public Integer getZcpd() {
		return zcpd;
	}
	public Integer getLszczdpd() {
		return lszczdpd;
	}
	public Integer getCsbpd() {
		this.setCsbpd();
		return csbpd;
	}
	public Integer getLsjg() {
		return lsjg;
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
	public void setJycs(String jycs) {
		this.jycs = jycs;
	}
	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}
	public void setLsy(String lsy) {
		this.lsy = lsy;
	}
	public void setZdxtsj(Float zdxtsj) {
		this.zdxtsj = zdxtsj;
	}
	public void setZdwdx(String zdwdx) {
		this.zdwdx = zdwdx;
	}
	public void setXckzmfdd(Float xckzmfdd) {
		this.xckzmfdd = xckzmfdd;
	}
	public void setXcmzmfdd(Float xcmzmfdd) {
		this.xcmzmfdd = xcmzmfdd;
	}
	public void setXczdczlz(Integer xczdczlz) {
		this.xczdczlz = xczdczlz;
	}
	public void setLszdpd(Integer lszdpd) {
		this.lszdpd = lszdpd;
	}
	public void setYjzdcsd(Integer yjzdcsd) {
		this.yjzdcsd = yjzdcsd;
	}
	public void setYjkzmfdd(Float yjkzmfdd) {
		this.yjkzmfdd = yjkzmfdd;
	}
	public void setYjmzmfdd(Float yjmzmfdd) {
		this.yjmzmfdd = yjmzmfdd;
	}
	public void setYjzdczlfs(Integer yjzdczlfs) {
		this.yjzdczlfs = yjzdczlfs;
	}
	public void setYjzdczlz(Integer yjzdczlz) {
		this.yjzdczlz = yjzdczlz;
	}
	public void setYjzdpd(Integer yjzdpd) {
		this.yjzdpd = yjzdpd;
	}
	public void setZcpd(Integer zcpd) {
		this.zcpd = zcpd;
	}
	public void setLszczdpd(Integer lszczdpd) {
		this.lszczdpd = lszczdpd;
	}
	public void setCsbpd(Integer csbpd) {
		this.csbpd = csbpd;
	}
	public void setLsjg(Integer lsjg) {
		this.lsjg = lsjg;
	}
	
	
	
	public Float getZdcsd() {
		return zdcsd;
	}
	public void setZdcsd(Float zdcsd) {
		this.zdcsd = zdcsd;
	}
	public Integer getLsxtsjpd() {
		return lsxtsjpd;
	}
	public void setLsxtsjpd(Integer lsxtsjpd) {
		this.lsxtsjpd = lsxtsjpd;
	}
	public void setCsbpd() {
		if(csdscz!=null){
			if(csdscz>=32.8&&csdscz<=40){
				this.csbpd = 1;
			}else{
				this.csbpd = 2;
			}
		}
	}
	
	public void setLscsdxz(String cllx,Integer zzl){
		//三轮汽车
		if(cllx.indexOf("N")>=0){
			this.lscsdxz=20;
		}else if(cllx.indexOf("K")>=0){
			//乘用车
			this.lscsdxz=50;
		}else if(cllx.indexOf("H5")>=0&&zzl<3500){
			//低速货车 小于3.5T
			this.lscsdxz=30;
		}else if(zzl<3500){
			this.lscsdxz=50;
		}else{
			this.lscsdxz=30;
		}
	}
	
	/**
	 * 设置路试空载制动距离限值
	 * @param cllx
	 * @param zzl
	 */
	public void setLskzzdjlxz(String cllx,Integer zzl){
		//三轮汽车
		if(cllx.indexOf("N")>=0){
			this.lskzzdjlxz=5.0f;
		}else if(cllx.indexOf("K")>=0){
			//乘用车
			this.lskzzdjlxz=19.0f;
		}else if(cllx.indexOf("H5")>=0&&zzl<3500){
			//低速货车 小于3.5T
			this.lskzzdjlxz=8.0f;
		}else if(zzl<3500){
			this.lskzzdjlxz=21.0f;
		}else{
			this.lskzzdjlxz=9.0f;
		}
	}
	
	/**
	 * 设置路试满载限值
	 * @param cllx
	 * @param zzl
	 */
	public void setLsmzzdjlxz(String cllx,Integer zzl){
		//三轮汽车
		if(cllx.indexOf("N")>=0){
			this.lsmzzdjlxz=5.0f;
		}else if(cllx.indexOf("K")>=0){
			//乘用车
			this.lsmzzdjlxz=20.0f;
		}else if(cllx.indexOf("H5")>=0&&zzl<3500){
			//低速货车 小于3.5T
			this.lsmzzdjlxz=9.0f;
		}else if(zzl<3500){
			this.lsmzzdjlxz=22.0f;
		}else{
			this.lsmzzdjlxz=10.0f;
		}
	}
	
	/**
	 * 设置路试空载MFDD限值
	 * @param cllx
	 * @param zzl
	 */
	public void setLskzmfddxz(String cllx,Integer zzl){
		//三轮汽车
		if(cllx.indexOf("N")>=0){
			this.lskzmfddxz=3.8f;
		}else if(cllx.indexOf("K")>=0){
			//乘用车
			this.lskzmfddxz=6.2f;
		}else if(cllx.indexOf("H5")>=0&&zzl<3500){
			//低速货车 小于3.5T
			this.lskzmfddxz=5.6f;
		}else if(zzl<3500){
			this.lskzmfddxz=5.8f;
		}else{
			this.lskzmfddxz=5.4f;
		}
	}
	
	/**
	 * 设置路试空载MFDD限值
	 * @param cllx
	 * @param zzl
	 */
	public void setLsmzmfddxz(String cllx,Integer zzl){
		//三轮汽车
		if(cllx.indexOf("N")>=0){
			this.lsmzmfddxz=3.8f;
		}else if(cllx.indexOf("K")>=0){
			//乘用车
			this.lsmzmfddxz=5.9f;
		}else if(cllx.indexOf("H5")>=0&&zzl<3500){
			//低速货车 小于3.5T
			this.lsmzmfddxz=5.2f;
		}else if(zzl<3500){
			this.lsmzmfddxz=5.4f;
		}else{
			this.lsmzmfddxz=5.0f;
		}
	}
	
	/**
	 * 设置路试协调时间判断
	 * @param cllx
	 * @param zzl
	 */
	public void setLsxtsjxz(String zzly){
		//三轮汽车
		if("0".equals(zzly)){
			this.lsxtsjxz=0.6f;
		}else {
			this.lsxtsjxz=0.35f;
		}
	}
	
	
	public void setLscsdpd(){
		if(zdcsd!=null&&lscsdxz!=null){
			if(zdcsd>=lscsdxz){
				this.lscsdpd=1;
			}else{
				this.lscsdpd=2;
			}
		}
	}
	
	
	public void setLskzzdjlpd(){
		if(this.lskzzdjlxz!=null&&xckzzdjl!=null){
			if(xckzzdjl<=lskzzdjlxz){
				lskzzdjlpd=1;
			}else{
				lskzzdjlpd=2;
			}
		}
	}
	
	public void setLsmzzdjlpd(){
		if(this.lsmzzdjlxz!=null&&xcmzzdjl!=null){
			if(xcmzzdjl<=lsmzzdjlxz){
				lsmzzdjlpd=1;
			}else{
				lsmzzdjlpd=2;
			}
		}
	}
	
	public void setLsxtsjpd(){
		if(this.lsxtsjxz!=null&&zdxtsj!=null){
			if(zdxtsj<=lsxtsjxz){
				lsxtsjpd=1;
			}else{
				lsxtsjpd=2;
			}
		}
	}
	
	public void setLskzmfddpd(){
		if(this.lskzmfddxz!=null&&xckzmfdd!=null){
			if(xckzmfdd>=lskzmfddxz){
				lskzmfddpd=1;
			}else{
				lskzmfddpd=2;
			}
		}
	}
	
	public void setLskmzmfddpd(){
		if(this.lsmzmfddxz!=null&&xcmzmfdd!=null){
			if(xcmzmfdd>=lsmzmfddxz){
				lsmzmfddpd=1;
			}else{
				lsmzmfddpd=2;
			}
		}
	}
	public Float getXckzzdjl() {
		return xckzzdjl;
	}
	public Float getXcmzzdjl() {
		return xcmzzdjl;
	}
	public Float getYjkzzdjl() {
		return yjkzzdjl;
	}
	public Float getYjmzzdjl() {
		return yjmzzdjl;
	}
	public Float getCsdscz() {
		return csdscz;
	}
	public void setXckzzdjl(Float xckzzdjl) {
		this.xckzzdjl = xckzzdjl;
	}
	public void setXcmzzdjl(Float xcmzzdjl) {
		this.xcmzzdjl = xcmzzdjl;
	}
	public void setYjkzzdjl(Float yjkzzdjl) {
		this.yjkzzdjl = yjkzzdjl;
	}
	public void setYjmzzdjl(Float yjmzzdjl) {
		this.yjmzzdjl = yjmzzdjl;
	}
	public void setCsdscz(Float csdscz) {
		this.csdscz = csdscz;
	}
	
	
}
