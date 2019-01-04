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

@Scope("prototype")
@Component("recordInfoOfJcx")
@Entity
@Table(name = "TM_RecordInfoOfJcx")
public class RecordInfoOfJcx {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id")
	private Integer id;
	
	@Column(length = 20)
	private String jczbh;	//检验机构编号
	@Column(length = 2)
	private String jcxxh;	//检测线代号	
	@Column(length = 128)
	private String jczmc;	//检验机构名称
	@Column(length = 128)
	private String jcxmc;	//检测线名称
	@Column(length = 1)
	private String jcxlb;	//检测线类别
	@Column(length = 1)
	private String jcxczfs;	//检测线控制方式
	@Column(length = 128)
	private String zdsbmc;	//制动检测设备名称
	@Column(length = 32)
	private String zdsbxh;	//制动检测设备型号
	@Column(length = 128)
	private String zdsbsccj;	//制动检测设备生产厂家
	
	@Column
	private Integer zdjcsj;	//制动检测最少时间
	
	@Column(length = 1)
	private String zdjcfs;	//制动检测方式
	@Column(length = 32)
	private String pbzs;	//平板制式
	
	@Column
	private Integer dpbcd;	//单平板长度
	@Column
	private Integer pbjj;	//平板间距
	@Column(length = 1)
	private String gtszdtzs;	//滚筒式制动台制式
	@Column(length = 1)
	private String gtszdttjfs;	//滚筒式制动台停机方式
	@Column
	private Date zdsbqysj;	//制动检测设备启用时间
	@Column
	private Date zdsbjdyxqz;	//制动检测设备检定有效期止
	@Column(length = 1)
	private String zdsbzt;	//制动检测设备状态
	@Column(length = 128)
	private String dgsbmc;	//灯光检测设备名称
	@Column(length = 32)
	private String dgsbxh;	//灯光检测设备型号
	@Column(length = 128)
	private String dgsbsccj;	//灯光检测设备生产厂家
	@Column
	private Integer dgjcsj;	//灯光检测最少时间
	@Column(length = 1)
	private String dgjcfs;	//灯光检测方式
	@Column(length = 1)
	private String dgcspyxz;	//灯光检测是否有车身偏移修正功能
	@Column
	private Date dgsbqysj;	//灯光检测设备启用时间
	@Column
	private Date dgsbjdyxqz;	//灯光检测设备检定有效期止
	@Column(length = 1)
	private String dgsbzt;	//灯光检测设备状态
	@Column(length = 128)
	private String sdsbmc;	//速度检测设备名称
	@Column(length = 32)
	private String sdsbxh;	//速度检测设备型号
	@Column(length = 128)
	private String sdsbsccj;	//速度检测设备生产厂家
	@Column
	private Integer sdjcsj;	//速度检测最少时间
	@Column
	private Date sdsbqysj;	//速度检测设备启用时间
	@Column
	private Date sdsbjdyxqz;	//速度检测设备检定有效期止
	@Column(length = 1)
	private String sdsbzt;	//速度检测设备状态
	@Column(length = 128)
	private String chsbmc;	//侧滑检测设备名称
	@Column(length = 32)
	private String chsbxh;	//侧滑检测设备型号
	@Column(length = 128)
	private String chsbsccj;	//侧滑检测设备生产厂家
	@Column
	private Integer chjcsj;	//侧滑检测最少时间
	@Column
	private Date chsbqysj;	//侧滑检测设备启用时间
	@Column
	private Date chsbjdyxqz;	//侧滑检测设备检定有效期止
	@Column(length = 1)
	private String chsbzt;	//侧滑检测设备状态
	@Column(length = 128)
	private String czsbmc;	//称重设备名称
	@Column(length = 32)
	private String czsbxh;	//称重检测设备型号
	@Column(length = 128)
	private String czsbsccj;	//称重检测设备型号
	@Column
	private Integer czjcsj;	//称重检测最少时间
	@Column
	private Integer czjb;	//称重范围
	@Column
	private Date czsbjdyxqz;	//称重检测设备检定有效期止
	@Column
	private Date czsbqysj;	//称重检测设备启用时间
	@Column(length = 1)
	private String czsbzt;	//称重检测设备状态
	@Column
	private Integer qxjcsj;	//全线检测时间
	@Column(length = 16)
	private String gw1;	//工位1
	@Column(length = 16)
	private String gw2;	//工位2
	@Column(length = 16)
	private String gw3;	//工位3
	@Column(length = 16)
	private String gw4;	//工位4
	@Column(length = 16)
	private String gw5;	//工位5
	@Column(length = 16)
	private String gw6;	//工位6
	@Column(length = 16)
	private String gw7;	//工位7
	@Column(length = 16)
	private String gw8;	//工位8
	@Column(length = 16)
	private String gw9;	//工位9
	@Column(length = 128)
	private String bz;	//备注
	@Column(length = 10)
	private String fzjg;	//发证机关
	@Column(length = 12)
	private String glbm;	//管理部门
	@Column
	private Date gxrq;	//更新日期
	@Column(length = 30)
	private String zdsbbh;	//制动检验设备编号
	@Column(length = 30)
	private String zdsbjdzsbh;	//制动检验设备检定/校准证书编号
	@Column(length = 30)
	private String dgsbbh;	//灯光检验设备编号
	@Column(length = 30)
	private String dgsbjdzsbh;	//灯光检测设备检定/校准证书标号
	@Column(length = 30)
	private String sdsbbh;	//速度检验设备编号
	@Column(length = 30)
	private String sdsbjdzsbh;	//速度检验设备检定/校准证书标号
	@Column(length = 30)
	private String chsbbh;	//侧滑检验设备编号
	@Column(length = 30)
	private String chsbjdzsbh;	//侧滑检验设备检定/校准证书编号
	@Column(length = 30)
	private String czsbbh;	//称重检验设备编号
	@Column(length = 30)
	private String czsbjdzsbh;	//称重检验设备检定/校准证书编号
	@Column(length = 1)
	private String zt;	//状态标记
	@Column(length = 512)
	private String ztyy;	//暂停原因
	
	
	public Integer getId() {
		return id;
	}
	public String getJczbh() {
		return jczbh;
	}
	public String getJcxxh() {
		return jcxxh;
	}
	public String getJczmc() {
		return jczmc;
	}
	public String getJcxmc() {
		return jcxmc;
	}
	public String getJcxlb() {
		return jcxlb;
	}
	public String getJcxczfs() {
		return jcxczfs;
	}
	public String getZdsbmc() {
		return zdsbmc;
	}
	public String getZdsbxh() {
		return zdsbxh;
	}
	public String getZdsbsccj() {
		return zdsbsccj;
	}
	public Integer getZdjcsj() {
		return zdjcsj;
	}
	public String getZdjcfs() {
		return zdjcfs;
	}
	public String getPbzs() {
		return pbzs;
	}
	public Integer getDpbcd() {
		return dpbcd;
	}
	public Integer getPbjj() {
		return pbjj;
	}
	public String getGtszdtzs() {
		return gtszdtzs;
	}
	public String getGtszdttjfs() {
		return gtszdttjfs;
	}
	public Date getZdsbqysj() {
		return zdsbqysj;
	}
	public Date getZdsbjdyxqz() {
		return zdsbjdyxqz;
	}
	public String getZdsbzt() {
		return zdsbzt;
	}
	public String getDgsbmc() {
		return dgsbmc;
	}
	public String getDgsbxh() {
		return dgsbxh;
	}
	public String getDgsbsccj() {
		return dgsbsccj;
	}
	public Integer getDgjcsj() {
		return dgjcsj;
	}
	public String getDgjcfs() {
		return dgjcfs;
	}
	public String getDgcspyxz() {
		return dgcspyxz;
	}
	public Date getDgsbqysj() {
		return dgsbqysj;
	}
	public Date getDgsbjdyxqz() {
		return dgsbjdyxqz;
	}
	public String getDgsbzt() {
		return dgsbzt;
	}
	public String getSdsbmc() {
		return sdsbmc;
	}
	public String getSdsbxh() {
		return sdsbxh;
	}
	public String getSdsbsccj() {
		return sdsbsccj;
	}
	public Integer getSdjcsj() {
		return sdjcsj;
	}
	public Date getSdsbqysj() {
		return sdsbqysj;
	}
	public Date getSdsbjdyxqz() {
		return sdsbjdyxqz;
	}
	public String getSdsbzt() {
		return sdsbzt;
	}
	public String getChsbmc() {
		return chsbmc;
	}
	public String getChsbxh() {
		return chsbxh;
	}
	public String getChsbsccj() {
		return chsbsccj;
	}
	public Integer getChjcsj() {
		return chjcsj;
	}
	public Date getChsbqysj() {
		return chsbqysj;
	}
	public Date getChsbjdyxqz() {
		return chsbjdyxqz;
	}
	public String getChsbzt() {
		return chsbzt;
	}
	public String getCzsbmc() {
		return czsbmc;
	}
	public String getCzsbxh() {
		return czsbxh;
	}
	public String getCzsbsccj() {
		return czsbsccj;
	}
	public Integer getCzjcsj() {
		return czjcsj;
	}
	public Integer getCzjb() {
		return czjb;
	}
	public Date getCzsbjdyxqz() {
		return czsbjdyxqz;
	}
	public Date getCzsbqysj() {
		return czsbqysj;
	}
	public String getCzsbzt() {
		return czsbzt;
	}
	public Integer getQxjcsj() {
		return qxjcsj;
	}
	public String getGw1() {
		return gw1;
	}
	public String getGw2() {
		return gw2;
	}
	public String getGw3() {
		return gw3;
	}
	public String getGw4() {
		return gw4;
	}
	public String getGw5() {
		return gw5;
	}
	public String getGw6() {
		return gw6;
	}
	public String getGw7() {
		return gw7;
	}
	public String getGw8() {
		return gw8;
	}
	public String getGw9() {
		return gw9;
	}
	public String getBz() {
		return bz;
	}
	public String getFzjg() {
		return fzjg;
	}
	public String getGlbm() {
		return glbm;
	}
	public Date getGxrq() {
		return gxrq;
	}
	public String getZdsbbh() {
		return zdsbbh;
	}
	public String getZdsbjdzsbh() {
		return zdsbjdzsbh;
	}
	public String getDgsbbh() {
		return dgsbbh;
	}
	public String getDgsbjdzsbh() {
		return dgsbjdzsbh;
	}
	public String getSdsbbh() {
		return sdsbbh;
	}
	public String getSdsbjdzsbh() {
		return sdsbjdzsbh;
	}
	public String getChsbbh() {
		return chsbbh;
	}
	public String getChsbjdzsbh() {
		return chsbjdzsbh;
	}
	public String getCzsbbh() {
		return czsbbh;
	}
	public String getCzsbjdzsbh() {
		return czsbjdzsbh;
	}
	public String getZt() {
		return zt;
	}
	public String getZtyy() {
		return ztyy;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}
	public void setJcxxh(String jcxxh) {
		this.jcxxh = jcxxh;
	}
	public void setJczmc(String jczmc) {
		this.jczmc = jczmc;
	}
	public void setJcxmc(String jcxmc) {
		this.jcxmc = jcxmc;
	}
	public void setJcxlb(String jcxlb) {
		this.jcxlb = jcxlb;
	}
	public void setJcxczfs(String jcxczfs) {
		this.jcxczfs = jcxczfs;
	}
	public void setZdsbmc(String zdsbmc) {
		this.zdsbmc = zdsbmc;
	}
	public void setZdsbxh(String zdsbxh) {
		this.zdsbxh = zdsbxh;
	}
	public void setZdsbsccj(String zdsbsccj) {
		this.zdsbsccj = zdsbsccj;
	}
	public void setZdjcsj(Integer zdjcsj) {
		this.zdjcsj = zdjcsj;
	}
	public void setZdjcfs(String zdjcfs) {
		this.zdjcfs = zdjcfs;
	}
	public void setPbzs(String pbzs) {
		this.pbzs = pbzs;
	}
	public void setDpbcd(Integer dpbcd) {
		this.dpbcd = dpbcd;
	}
	public void setPbjj(Integer pbjj) {
		this.pbjj = pbjj;
	}
	public void setGtszdtzs(String gtszdtzs) {
		this.gtszdtzs = gtszdtzs;
	}
	public void setGtszdttjfs(String gtszdttjfs) {
		this.gtszdttjfs = gtszdttjfs;
	}
	public void setZdsbqysj(Date zdsbqysj) {
		this.zdsbqysj = zdsbqysj;
	}
	public void setZdsbjdyxqz(Date zdsbjdyxqz) {
		this.zdsbjdyxqz = zdsbjdyxqz;
	}
	public void setZdsbzt(String zdsbzt) {
		this.zdsbzt = zdsbzt;
	}
	public void setDgsbmc(String dgsbmc) {
		this.dgsbmc = dgsbmc;
	}
	public void setDgsbxh(String dgsbxh) {
		this.dgsbxh = dgsbxh;
	}
	public void setDgsbsccj(String dgsbsccj) {
		this.dgsbsccj = dgsbsccj;
	}
	public void setDgjcsj(Integer dgjcsj) {
		this.dgjcsj = dgjcsj;
	}
	public void setDgjcfs(String dgjcfs) {
		this.dgjcfs = dgjcfs;
	}
	public void setDgcspyxz(String dgcspyxz) {
		this.dgcspyxz = dgcspyxz;
	}
	public void setDgsbqysj(Date dgsbqysj) {
		this.dgsbqysj = dgsbqysj;
	}
	public void setDgsbjdyxqz(Date dgsbjdyxqz) {
		this.dgsbjdyxqz = dgsbjdyxqz;
	}
	public void setDgsbzt(String dgsbzt) {
		this.dgsbzt = dgsbzt;
	}
	public void setSdsbmc(String sdsbmc) {
		this.sdsbmc = sdsbmc;
	}
	public void setSdsbxh(String sdsbxh) {
		this.sdsbxh = sdsbxh;
	}
	public void setSdsbsccj(String sdsbsccj) {
		this.sdsbsccj = sdsbsccj;
	}
	public void setSdjcsj(Integer sdjcsj) {
		this.sdjcsj = sdjcsj;
	}
	public void setSdsbqysj(Date sdsbqysj) {
		this.sdsbqysj = sdsbqysj;
	}
	public void setSdsbjdyxqz(Date sdsbjdyxqz) {
		this.sdsbjdyxqz = sdsbjdyxqz;
	}
	public void setSdsbzt(String sdsbzt) {
		this.sdsbzt = sdsbzt;
	}
	public void setChsbmc(String chsbmc) {
		this.chsbmc = chsbmc;
	}
	public void setChsbxh(String chsbxh) {
		this.chsbxh = chsbxh;
	}
	public void setChsbsccj(String chsbsccj) {
		this.chsbsccj = chsbsccj;
	}
	public void setChjcsj(Integer chjcsj) {
		this.chjcsj = chjcsj;
	}
	public void setChsbqysj(Date chsbqysj) {
		this.chsbqysj = chsbqysj;
	}
	public void setChsbjdyxqz(Date chsbjdyxqz) {
		this.chsbjdyxqz = chsbjdyxqz;
	}
	public void setChsbzt(String chsbzt) {
		this.chsbzt = chsbzt;
	}
	public void setCzsbmc(String czsbmc) {
		this.czsbmc = czsbmc;
	}
	public void setCzsbxh(String czsbxh) {
		this.czsbxh = czsbxh;
	}
	public void setCzsbsccj(String czsbsccj) {
		this.czsbsccj = czsbsccj;
	}
	public void setCzjcsj(Integer czjcsj) {
		this.czjcsj = czjcsj;
	}
	public void setCzjb(Integer czjb) {
		this.czjb = czjb;
	}
	public void setCzsbjdyxqz(Date czsbjdyxqz) {
		this.czsbjdyxqz = czsbjdyxqz;
	}
	public void setCzsbqysj(Date czsbqysj) {
		this.czsbqysj = czsbqysj;
	}
	public void setCzsbzt(String czsbzt) {
		this.czsbzt = czsbzt;
	}
	public void setQxjcsj(Integer qxjcsj) {
		this.qxjcsj = qxjcsj;
	}
	public void setGw1(String gw1) {
		this.gw1 = gw1;
	}
	public void setGw2(String gw2) {
		this.gw2 = gw2;
	}
	public void setGw3(String gw3) {
		this.gw3 = gw3;
	}
	public void setGw4(String gw4) {
		this.gw4 = gw4;
	}
	public void setGw5(String gw5) {
		this.gw5 = gw5;
	}
	public void setGw6(String gw6) {
		this.gw6 = gw6;
	}
	public void setGw7(String gw7) {
		this.gw7 = gw7;
	}
	public void setGw8(String gw8) {
		this.gw8 = gw8;
	}
	public void setGw9(String gw9) {
		this.gw9 = gw9;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}
	public void setZdsbbh(String zdsbbh) {
		this.zdsbbh = zdsbbh;
	}
	public void setZdsbjdzsbh(String zdsbjdzsbh) {
		this.zdsbjdzsbh = zdsbjdzsbh;
	}
	public void setDgsbbh(String dgsbbh) {
		this.dgsbbh = dgsbbh;
	}
	public void setDgsbjdzsbh(String dgsbjdzsbh) {
		this.dgsbjdzsbh = dgsbjdzsbh;
	}
	public void setSdsbbh(String sdsbbh) {
		this.sdsbbh = sdsbbh;
	}
	public void setSdsbjdzsbh(String sdsbjdzsbh) {
		this.sdsbjdzsbh = sdsbjdzsbh;
	}
	public void setChsbbh(String chsbbh) {
		this.chsbbh = chsbbh;
	}
	public void setChsbjdzsbh(String chsbjdzsbh) {
		this.chsbjdzsbh = chsbjdzsbh;
	}
	public void setCzsbbh(String czsbbh) {
		this.czsbbh = czsbbh;
	}
	public void setCzsbjdzsbh(String czsbjdzsbh) {
		this.czsbjdzsbh = czsbjdzsbh;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public void setZtyy(String ztyy) {
		this.ztyy = ztyy;
	}
	
	
	
	

}
