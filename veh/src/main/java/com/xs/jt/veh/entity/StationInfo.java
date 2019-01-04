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
@Component("stationInfo")
@Entity
@Table(name = "TM_StationInfo")
public class StationInfo {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private Integer id;

	/**检验机构编号*/
	@Column(length=20)
	private String jczbh;
	/**检验机构名称*/
	@Column(length=128)
	private String jczmc;
	/**是否与公安网联网*/
	@Column(length=1)
	private String sflw;
	/**资格许可证书编号*/
	@Column(length=32)
	private String rdsbh;
	/**资格许可有效期始*/
	@Column 
	private Date rdyxqs;
	/**资格许可有效期止*/
	@Column 
	private Date rdyxqz;
	/**设计日检测能力(汽车辆)*/
	@Column(length=4)
	private Integer shejirjcnl;
	/**实际日检测能力(汽车辆)*/
	@Column(length=4)
	private Integer shijirjcnl;
	/**检测人员总数*/
	@Column(length=4)
	private Integer jcryzs;
	/**外检工位人数*/
	@Column(length=4)
	private Integer wjgwrs;
	/**录入工位人数*/
	@Column(length=4)
	private Integer lrgwrs;
	/**引车员人数*/
	@Column(length=4)
	private Integer ycyrs;
	/**底盘工位人数*/
	@Column(length=4)
	private Integer dpgwrs;
	/**总检工位人数*/
	@Column(length=4)
	private Integer zjgwrs;
	/**其他工位人数*/
	@Column(length=4)
	private Integer qtgwrs;
	/**通过省级质检部门考核人数*/
	@Column(length=4)
	private Integer tgszjbmkhrs;
	/**未通过省级质检部门考核人数*/
	@Column(length=4)
	private Integer wtgszjbmkhrs;
	/**发证机关*/
	@Column(length=10)
	private String fzjg;
	/**管理部门*/
	@Column(length=12)
	private String glbm;
	/**更新日期*/
	@Column 
	private Date gxrq;
	/**备注*/
	@Column(length=4000)
	private String bz;
	/**设计日检测能力(摩托辆)*/
	@Column(length=4)
	private Integer shejirjcmtsl;
	/**实际日检测能力(摩托辆)*/
	@Column(length=4)
	private Integer shijirjcmtsl;
	/**审核标记*/
	@Column(length=2)
	private String shbj;
	/**使用管理部门*/
	@Column(length=4000)
	private String syglbm;
	/**审核意见*/
	@Column(length=4000)
	private String shyj;
	/**状态标记*/
	@Column(length=1)
	private String zt;
	/**暂停原因*/
	@Column(length=512)
	private String ztyy;
	/**单位地址*/
	@Column(length=300)
	private String dwdz;
	/**邮政编码*/
	@Column(length=6)
	private String yzbm;
	/**许可检验范围*/
	@Column(length=100)
	private String xkjyfw;
	/**资格许可发放单位*/
	@Column(length=300)
	private String rdsffdw;
	/**法人代表*/
	@Column(length=30)
	private String frdb;
	/**法人代表身份证号*/
	@Column(length=18)
	private String frdbsfzh;
	/**法人代表联系电话*/
	@Column(length=15)
	private String frdblxdh;
	/**负责人*/
	@Column(length=30)
	private String fzr;
	/**负责人身份证号*/
	@Column(length=18)
	private String fzrsfzh;
	/**负责人联系电话*/
	@Column(length=15)
	private String fzrlxdh;
	/**日常联系人*/
	@Column(length=30)
	private String rclxr;
	/**日常联系人身份证号*/
	@Column(length=18)
	private String rclxrsfzh;
	/**日常联系人联系电话*/
	@Column(length=15)
	private String rclxrlxdh;
	
	
	public Integer getId() {
		return id;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJczbh() {
		return jczbh;
	}
	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}
	public String getJczmc() {
		return jczmc;
	}
	public void setJczmc(String jczmc) {
		this.jczmc = jczmc;
	}
	public String getSflw() {
		return sflw;
	}
	public void setSflw(String sflw) {
		this.sflw = sflw;
	}
	public String getRdsbh() {
		return rdsbh;
	}
	public void setRdsbh(String rdsbh) {
		this.rdsbh = rdsbh;
	}
	public Date getRdyxqs() {
		return rdyxqs;
	}
	public void setRdyxqs(Date rdyxqs) {
		this.rdyxqs = rdyxqs;
	}
	public Date getRdyxqz() {
		return rdyxqz;
	}
	public void setRdyxqz(Date rdyxqz) {
		this.rdyxqz = rdyxqz;
	}
	public Integer getShejirjcnl() {
		return shejirjcnl;
	}
	public void setShejirjcnl(Integer shejirjcnl) {
		this.shejirjcnl = shejirjcnl;
	}
	public Integer getShijirjcnl() {
		return shijirjcnl;
	}
	public void setShijirjcnl(Integer shijirjcnl) {
		this.shijirjcnl = shijirjcnl;
	}
	public Integer getJcryzs() {
		return jcryzs;
	}
	public void setJcryzs(Integer jcryzs) {
		this.jcryzs = jcryzs;
	}
	public Integer getWjgwrs() {
		return wjgwrs;
	}
	public void setWjgwrs(Integer wjgwrs) {
		this.wjgwrs = wjgwrs;
	}
	public Integer getLrgwrs() {
		return lrgwrs;
	}
	public void setLrgwrs(Integer lrgwrs) {
		this.lrgwrs = lrgwrs;
	}
	public Integer getYcyrs() {
		return ycyrs;
	}
	public void setYcyrs(Integer ycyrs) {
		this.ycyrs = ycyrs;
	}
	public Integer getDpgwrs() {
		return dpgwrs;
	}
	public void setDpgwrs(Integer dpgwrs) {
		this.dpgwrs = dpgwrs;
	}
	public Integer getZjgwrs() {
		return zjgwrs;
	}
	public void setZjgwrs(Integer zjgwrs) {
		this.zjgwrs = zjgwrs;
	}
	public Integer getQtgwrs() {
		return qtgwrs;
	}
	public void setQtgwrs(Integer qtgwrs) {
		this.qtgwrs = qtgwrs;
	}
	public Integer getTgszjbmkhrs() {
		return tgszjbmkhrs;
	}
	public void setTgszjbmkhrs(Integer tgszjbmkhrs) {
		this.tgszjbmkhrs = tgszjbmkhrs;
	}
	public Integer getWtgszjbmkhrs() {
		return wtgszjbmkhrs;
	}
	public void setWtgszjbmkhrs(Integer wtgszjbmkhrs) {
		this.wtgszjbmkhrs = wtgszjbmkhrs;
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
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getShejirjcmtsl() {
		return shejirjcmtsl;
	}
	public void setShejirjcmtsl(Integer shejirjcmtsl) {
		this.shejirjcmtsl = shejirjcmtsl;
	}
	public Integer getShijirjcmtsl() {
		return shijirjcmtsl;
	}
	public void setShijirjcmtsl(Integer shijirjcmtsl) {
		this.shijirjcmtsl = shijirjcmtsl;
	}
	public String getShbj() {
		return shbj;
	}
	public void setShbj(String shbj) {
		this.shbj = shbj;
	}
	public String getSyglbm() {
		return syglbm;
	}
	public void setSyglbm(String syglbm) {
		this.syglbm = syglbm;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
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
	public String getDwdz() {
		return dwdz;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	public String getYzbm() {
		return yzbm;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getXkjyfw() {
		return xkjyfw;
	}
	public void setXkjyfw(String xkjyfw) {
		this.xkjyfw = xkjyfw;
	}
	public String getRdsffdw() {
		return rdsffdw;
	}
	public void setRdsffdw(String rdsffdw) {
		this.rdsffdw = rdsffdw;
	}
	public String getFrdb() {
		return frdb;
	}
	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}
	public String getFrdbsfzh() {
		return frdbsfzh;
	}
	public void setFrdbsfzh(String frdbsfzh) {
		this.frdbsfzh = frdbsfzh;
	}
	public String getFrdblxdh() {
		return frdblxdh;
	}
	public void setFrdblxdh(String frdblxdh) {
		this.frdblxdh = frdblxdh;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public String getFzrsfzh() {
		return fzrsfzh;
	}
	public void setFzrsfzh(String fzrsfzh) {
		this.fzrsfzh = fzrsfzh;
	}
	public String getFzrlxdh() {
		return fzrlxdh;
	}
	public void setFzrlxdh(String fzrlxdh) {
		this.fzrlxdh = fzrlxdh;
	}
	public String getRclxr() {
		return rclxr;
	}
	public void setRclxr(String rclxr) {
		this.rclxr = rclxr;
	}
	public String getRclxrsfzh() {
		return rclxrsfzh;
	}
	public void setRclxrsfzh(String rclxrsfzh) {
		this.rclxrsfzh = rclxrsfzh;
	}
	public String getRclxrlxdh() {
		return rclxrlxdh;
	}
	public void setRclxrlxdh(String rclxrlxdh) {
		this.rclxrlxdh = rclxrlxdh;
	}

}
