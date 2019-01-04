package com.xs.jt.veh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;

@Scope("prototype")
@Component("vehCheckLine")
@Entity
@Table(name = "TM_VehCheckLine")
public class VehCheckLine extends BaseEntity {

	/** 检验机构编号 */
	@Column(length = 20)
	private String jczbh;
	/** 检测线代号 */
	@Column(length = 2)
	private String jcxxh;
	/** 检验机构名称 */
	@Column(length = 128)
	private String jczmc;
	/** 检测线名称 */
	@Column(length = 128)
	private String jcxmc;
	/** 检测线类别 */
	@Column(length = 1)
	private String jcxlb;
	/** 检测线控制方式 */
	@Column(length = 1)
	private String jcxczfs;
	/** 制动检测设备名称 */
	@Column(length = 128)
	private String zdsbmc;
	/** 制动检测设备型号 */
	@Column(length = 32)
	private String zdsbxh;
	/** 制动检测设备生产厂家 */
	@Column(length = 128)
	private String zdsbsccj;
	/** 制动检测最少时间 */
	@Column(length = 4)
	private Integer zdjcsj;
	/** 制动检测方式 */
	@Column(length = 1)
	private String zdjcfs;
	/** 平板制式 */
	@Column(length = 32)
	private String pbzs;
	/** 单平板长度 */
	@Column(length = 5)
	private Integer dpbcd;
	/** 平板间距 */
	@Column(length = 5)
	private Integer pbjj;
	/** 滚筒式制动台制式 */
	@Column(length = 1)
	private String gtszdtzs;
	/** 滚筒式制动台停机方式 */
	@Column(length = 1)
	private String gtszdttjfs;
	/** 制动检测设备启用时间 */
	@Column
	private Date zdsbqysj;
	/** 制动检测设备检定有效期止 */
	@Column
	private Date zdsbjdyxqz;
	/** 制动检测设备状态 */
	@Column(length = 1)
	private String zdsbzt;
	/** 灯光检测设备名称 */
	@Column(length = 128)
	private String dgsbmc;
	/** 灯光检测设备型号 */
	@Column(length = 32)
	private String dgsbxh;
	/** 灯光检测设备生产厂家 */
	@Column(length = 128)
	private String dgsbsccj;
	/** 灯光检测最少时间 */
	@Column(length = 4)
	private Integer dgjcsj;
	/** 灯光检测方式 */
	@Column(length = 1)
	private String dgjcfs;
	/** 灯光检测是否有车身偏移修正功能 */
	@Column(length = 1)
	private String dgcspyxz;
	/** 灯光检测设备启用时间 */
	@Column
	private Date dgsbqysj;
	/** 灯光检测设备检定有效期止 */
	@Column
	private Date dgsbjdyxqz;
	/** 灯光检测设备状态 */
	@Column(length = 1)
	private String dgsbzt;
	/** 速度检测设备名称 */
	@Column(length = 128)
	private String sdsbmc;
	/** 速度检测设备型号 */
	@Column(length = 32)
	private String sdsbxh;
	/** 速度检测设备生产厂家 */
	@Column(length = 128)
	private String sdsbsccj;
	/** 速度检测最少时间 */
	@Column(length = 4)
	private Integer sdjcsj;
	/** 速度检测设备启用时间 */
	@Column
	private Date sdsbqysj;
	/** 速度检测设备检定有效期止 */
	@Column
	private Date sdsbjdyxqz;
	/** 速度检测设备状态 */
	@Column(length = 1)
	private String sdsbzt;
	/** 侧滑检测设备名称 */
	@Column(length = 128)
	private String chsbmc;
	/** 侧滑检测设备型号 */
	@Column(length = 32)
	private String chsbxh;
	/** 侧滑检测设备生产厂家 */
	@Column(length = 128)
	private String chsbsccj;
	/** 侧滑检测最少时间 */
	@Column(length = 4)
	private Integer chjcsj;
	/** 侧滑检测设备启用时间 */
	@Column
	private Date chsbqysj;
	/** 侧滑检测设备检定有效期止 */
	@Column
	private Date chsbjdyxqz;
	/** 侧滑检测设备状态 */
	@Column(length = 1)
	private String chsbzt;
	/** 称重设备名称 */
	@Column(length = 128)
	private String czsbmc;
	/** 称重检测设备型号 */
	@Column(length = 32)
	private String czsbxh;
	/** 称重检测设备生产厂家 */
	@Column(length = 128)
	private String czsbsccj;
	/** 称重检测最少时间 */
	@Column(length = 4)
	private Integer czjcsj;
	/** 称重范围 */
	@Column(length = 6)
	private Integer czjb;
	/** 称重检测设备检定有效期止 */
	@Column
	private Date czsbjdyxqz;
	/** 称重检测设备启用时间 */
	@Column
	private Date czsbqysj;
	/** 称重检测设备状态 */
	@Column(length = 1)
	private String czsbzt;
	/** 全线检测时间 */
	@Column(length = 4)
	private Integer qxjcsj;
	/** 工位1 */
	@Column(length = 16)
	private String gw1;
	/** 工位2 */
	@Column(length = 16)
	private String gw2;
	/** 工位3 */
	@Column(length = 16)
	private String gw3;
	/** 工位4 */
	@Column(length = 16)
	private String gw4;
	/** 工位5 */
	@Column(length = 16)
	private String gw5;
	/** 工位6 */
	@Column(length = 16)
	private String gw6;
	/** 工位7 */
	@Column(length = 16)
	private String gw7;
	/** 工位8 */
	@Column(length = 16)
	private String gw8;
	/** 工位9 */
	@Column(length = 16)
	private String gw9;
	/** 备注 */
	@Column(length = 128)
	private String bz;
	/** 发证机关 */
	@Column(length = 10)
	private String fzjg;
	/** 管理部门 */
	@Column(length = 12)
	private String glbm;
	/** 更新日期 */
	@Column
	private Date gxrq;
	/** 制动检验设备编号 */
	@Column(length = 30)
	private String zdsbbh;
	/** 制动检验设备检定/校准证书编号 */
	@Column(length = 30)
	private String zdsbjdzsbh;
	/** 灯光检验设备编号 */
	@Column(length = 30)
	private String dgsbbh;
	/** 灯光检测设备检定/校准证书标号 */
	@Column(length = 30)
	private String dgsbjdzsbh;
	/** 速度检验设备编号 */
	@Column(length = 30)
	private String sdsbbh;
	/** 速度检验设备检定/校准证书标号 */
	@Column(length = 30)
	private String sdsbjdzsbh;
	/** 侧滑检验设备编号 */
	@Column(length = 30)
	private String chsbbh;
	/** 侧滑检验设备检定/校准证书编号 */
	@Column(length = 30)
	private String chsbjdzsbh;
	/** 称重检验设备编号 */
	@Column(length = 30)
	private String czsbbh;
	/** 称重检验设备检定/校准证书编号 */
	@Column(length = 30)
	private String czsbjdzsbh;
	/** 状态标记 */
	@Column(length = 1)
	private String zt;
	/** 暂停原因 */
	@Column(length = 512)
	private String ztyy;

	public String getJczbh() {
		return jczbh;
	}

	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}

	public String getJcxxh() {
		return jcxxh;
	}

	public void setJcxxh(String jcxxh) {
		this.jcxxh = jcxxh;
	}

	public String getJczmc() {
		return jczmc;
	}

	public void setJczmc(String jczmc) {
		this.jczmc = jczmc;
	}

	public String getJcxmc() {
		return jcxmc;
	}

	public void setJcxmc(String jcxmc) {
		this.jcxmc = jcxmc;
	}

	public String getJcxlb() {
		return jcxlb;
	}

	public void setJcxlb(String jcxlb) {
		this.jcxlb = jcxlb;
	}

	public String getJcxczfs() {
		return jcxczfs;
	}

	public void setJcxczfs(String jcxczfs) {
		this.jcxczfs = jcxczfs;
	}

	public String getZdsbmc() {
		return zdsbmc;
	}

	public void setZdsbmc(String zdsbmc) {
		this.zdsbmc = zdsbmc;
	}

	public String getZdsbxh() {
		return zdsbxh;
	}

	public void setZdsbxh(String zdsbxh) {
		this.zdsbxh = zdsbxh;
	}

	public String getZdsbsccj() {
		return zdsbsccj;
	}

	public void setZdsbsccj(String zdsbsccj) {
		this.zdsbsccj = zdsbsccj;
	}

	public Integer getZdjcsj() {
		return zdjcsj;
	}

	public void setZdjcsj(Integer zdjcsj) {
		this.zdjcsj = zdjcsj;
	}

	public String getZdjcfs() {
		return zdjcfs;
	}

	public void setZdjcfs(String zdjcfs) {
		this.zdjcfs = zdjcfs;
	}

	public String getPbzs() {
		return pbzs;
	}

	public void setPbzs(String pbzs) {
		this.pbzs = pbzs;
	}

	public Integer getDpbcd() {
		return dpbcd;
	}

	public void setDpbcd(Integer dpbcd) {
		this.dpbcd = dpbcd;
	}

	public Integer getPbjj() {
		return pbjj;
	}

	public void setPbjj(Integer pbjj) {
		this.pbjj = pbjj;
	}

	public String getGtszdtzs() {
		return gtszdtzs;
	}

	public void setGtszdtzs(String gtszdtzs) {
		this.gtszdtzs = gtszdtzs;
	}

	public String getGtszdttjfs() {
		return gtszdttjfs;
	}

	public void setGtszdttjfs(String gtszdttjfs) {
		this.gtszdttjfs = gtszdttjfs;
	}

	public Date getZdsbqysj() {
		return zdsbqysj;
	}

	public void setZdsbqysj(Date zdsbqysj) {
		this.zdsbqysj = zdsbqysj;
	}

	public Date getZdsbjdyxqz() {
		return zdsbjdyxqz;
	}

	public void setZdsbjdyxqz(Date zdsbjdyxqz) {
		this.zdsbjdyxqz = zdsbjdyxqz;
	}

	public String getZdsbzt() {
		return zdsbzt;
	}

	public void setZdsbzt(String zdsbzt) {
		this.zdsbzt = zdsbzt;
	}

	public String getDgsbmc() {
		return dgsbmc;
	}

	public void setDgsbmc(String dgsbmc) {
		this.dgsbmc = dgsbmc;
	}

	public String getDgsbxh() {
		return dgsbxh;
	}

	public void setDgsbxh(String dgsbxh) {
		this.dgsbxh = dgsbxh;
	}

	public String getDgsbsccj() {
		return dgsbsccj;
	}

	public void setDgsbsccj(String dgsbsccj) {
		this.dgsbsccj = dgsbsccj;
	}

	public Integer getDgjcsj() {
		return dgjcsj;
	}

	public void setDgjcsj(Integer dgjcsj) {
		this.dgjcsj = dgjcsj;
	}

	public String getDgjcfs() {
		return dgjcfs;
	}

	public void setDgjcfs(String dgjcfs) {
		this.dgjcfs = dgjcfs;
	}

	public String getDgcspyxz() {
		return dgcspyxz;
	}

	public void setDgcspyxz(String dgcspyxz) {
		this.dgcspyxz = dgcspyxz;
	}

	public Date getDgsbqysj() {
		return dgsbqysj;
	}

	public void setDgsbqysj(Date dgsbqysj) {
		this.dgsbqysj = dgsbqysj;
	}

	public Date getDgsbjdyxqz() {
		return dgsbjdyxqz;
	}

	public void setDgsbjdyxqz(Date dgsbjdyxqz) {
		this.dgsbjdyxqz = dgsbjdyxqz;
	}

	public String getDgsbzt() {
		return dgsbzt;
	}

	public void setDgsbzt(String dgsbzt) {
		this.dgsbzt = dgsbzt;
	}

	public String getSdsbmc() {
		return sdsbmc;
	}

	public void setSdsbmc(String sdsbmc) {
		this.sdsbmc = sdsbmc;
	}

	public String getSdsbxh() {
		return sdsbxh;
	}

	public void setSdsbxh(String sdsbxh) {
		this.sdsbxh = sdsbxh;
	}

	public String getSdsbsccj() {
		return sdsbsccj;
	}

	public void setSdsbsccj(String sdsbsccj) {
		this.sdsbsccj = sdsbsccj;
	}

	public Integer getSdjcsj() {
		return sdjcsj;
	}

	public void setSdjcsj(Integer sdjcsj) {
		this.sdjcsj = sdjcsj;
	}

	public Date getSdsbqysj() {
		return sdsbqysj;
	}

	public void setSdsbqysj(Date sdsbqysj) {
		this.sdsbqysj = sdsbqysj;
	}

	public Date getSdsbjdyxqz() {
		return sdsbjdyxqz;
	}

	public void setSdsbjdyxqz(Date sdsbjdyxqz) {
		this.sdsbjdyxqz = sdsbjdyxqz;
	}

	public String getSdsbzt() {
		return sdsbzt;
	}

	public void setSdsbzt(String sdsbzt) {
		this.sdsbzt = sdsbzt;
	}

	public String getChsbmc() {
		return chsbmc;
	}

	public void setChsbmc(String chsbmc) {
		this.chsbmc = chsbmc;
	}

	public String getChsbxh() {
		return chsbxh;
	}

	public void setChsbxh(String chsbxh) {
		this.chsbxh = chsbxh;
	}

	public String getChsbsccj() {
		return chsbsccj;
	}

	public void setChsbsccj(String chsbsccj) {
		this.chsbsccj = chsbsccj;
	}

	public Integer getChjcsj() {
		return chjcsj;
	}

	public void setChjcsj(Integer chjcsj) {
		this.chjcsj = chjcsj;
	}

	public Date getChsbqysj() {
		return chsbqysj;
	}

	public void setChsbqysj(Date chsbqysj) {
		this.chsbqysj = chsbqysj;
	}

	public Date getChsbjdyxqz() {
		return chsbjdyxqz;
	}

	public void setChsbjdyxqz(Date chsbjdyxqz) {
		this.chsbjdyxqz = chsbjdyxqz;
	}

	public String getChsbzt() {
		return chsbzt;
	}

	public void setChsbzt(String chsbzt) {
		this.chsbzt = chsbzt;
	}

	public String getCzsbmc() {
		return czsbmc;
	}

	public void setCzsbmc(String czsbmc) {
		this.czsbmc = czsbmc;
	}

	public String getCzsbxh() {
		return czsbxh;
	}

	public void setCzsbxh(String czsbxh) {
		this.czsbxh = czsbxh;
	}

	public String getCzsbsccj() {
		return czsbsccj;
	}

	public void setCzsbsccj(String czsbsccj) {
		this.czsbsccj = czsbsccj;
	}

	public Integer getCzjcsj() {
		return czjcsj;
	}

	public void setCzjcsj(Integer czjcsj) {
		this.czjcsj = czjcsj;
	}

	public Integer getCzjb() {
		return czjb;
	}

	public void setCzjb(Integer czjb) {
		this.czjb = czjb;
	}

	public Date getCzsbjdyxqz() {
		return czsbjdyxqz;
	}

	public void setCzsbjdyxqz(Date czsbjdyxqz) {
		this.czsbjdyxqz = czsbjdyxqz;
	}

	public Date getCzsbqysj() {
		return czsbqysj;
	}

	public void setCzsbqysj(Date czsbqysj) {
		this.czsbqysj = czsbqysj;
	}

	public String getCzsbzt() {
		return czsbzt;
	}

	public void setCzsbzt(String czsbzt) {
		this.czsbzt = czsbzt;
	}

	public Integer getQxjcsj() {
		return qxjcsj;
	}

	public void setQxjcsj(Integer qxjcsj) {
		this.qxjcsj = qxjcsj;
	}

	public String getGw1() {
		return gw1;
	}

	public void setGw1(String gw1) {
		this.gw1 = gw1;
	}

	public String getGw2() {
		return gw2;
	}

	public void setGw2(String gw2) {
		this.gw2 = gw2;
	}

	public String getGw3() {
		return gw3;
	}

	public void setGw3(String gw3) {
		this.gw3 = gw3;
	}

	public String getGw4() {
		return gw4;
	}

	public void setGw4(String gw4) {
		this.gw4 = gw4;
	}

	public String getGw5() {
		return gw5;
	}

	public void setGw5(String gw5) {
		this.gw5 = gw5;
	}

	public String getGw6() {
		return gw6;
	}

	public void setGw6(String gw6) {
		this.gw6 = gw6;
	}

	public String getGw7() {
		return gw7;
	}

	public void setGw7(String gw7) {
		this.gw7 = gw7;
	}

	public String getGw8() {
		return gw8;
	}

	public void setGw8(String gw8) {
		this.gw8 = gw8;
	}

	public String getGw9() {
		return gw9;
	}

	public void setGw9(String gw9) {
		this.gw9 = gw9;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public Date getGxrq() {
		return gxrq;
	}

	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}

	public String getZdsbbh() {
		return zdsbbh;
	}

	public void setZdsbbh(String zdsbbh) {
		this.zdsbbh = zdsbbh;
	}

	public String getZdsbjdzsbh() {
		return zdsbjdzsbh;
	}

	public void setZdsbjdzsbh(String zdsbjdzsbh) {
		this.zdsbjdzsbh = zdsbjdzsbh;
	}

	public String getDgsbbh() {
		return dgsbbh;
	}

	public void setDgsbbh(String dgsbbh) {
		this.dgsbbh = dgsbbh;
	}

	public String getDgsbjdzsbh() {
		return dgsbjdzsbh;
	}

	public void setDgsbjdzsbh(String dgsbjdzsbh) {
		this.dgsbjdzsbh = dgsbjdzsbh;
	}

	public String getSdsbbh() {
		return sdsbbh;
	}

	public void setSdsbbh(String sdsbbh) {
		this.sdsbbh = sdsbbh;
	}

	public String getSdsbjdzsbh() {
		return sdsbjdzsbh;
	}

	public void setSdsbjdzsbh(String sdsbjdzsbh) {
		this.sdsbjdzsbh = sdsbjdzsbh;
	}

	public String getChsbbh() {
		return chsbbh;
	}

	public void setChsbbh(String chsbbh) {
		this.chsbbh = chsbbh;
	}

	public String getChsbjdzsbh() {
		return chsbjdzsbh;
	}

	public void setChsbjdzsbh(String chsbjdzsbh) {
		this.chsbjdzsbh = chsbjdzsbh;
	}

	public String getCzsbbh() {
		return czsbbh;
	}

	public void setCzsbbh(String czsbbh) {
		this.czsbbh = czsbbh;
	}

	public String getCzsbjdzsbh() {
		return czsbjdzsbh;
	}

	public void setCzsbjdzsbh(String czsbjdzsbh) {
		this.czsbjdzsbh = czsbjdzsbh;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getZtyy() {
		return ztyy;
	}

	public void setZtyy(String ztyy) {
		this.ztyy = ztyy;
	}

}
