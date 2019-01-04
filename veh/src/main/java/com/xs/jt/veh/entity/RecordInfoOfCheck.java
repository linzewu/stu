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
@Component("recordInfoOfCheck")
@Entity
@Table(name = "TM_RecordInfoOfCheck")
public class RecordInfoOfCheck {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id")
	private Integer id;
	
	@Column(length = 20)
	private String jczbh;	//检验机构编号
	@Column(length = 128)
	private String jczmc;	//检验机构名称
	@Column(length = 1)
	private String sflw;	//是否与公安网联网
	@Column(length = 32)
	private String rdsbh;	//	资格许可证书编号
	@Column
	private Date rdyxqs;	//	资格许可有效期始
	@Column
	private Date rdyxqz;	//	资格许可有效期止
	@Column
	private Integer shejirjcnl;	//	设计日检测能力(汽车辆)
	@Column
	private Integer shijirjcnl;	//	实际日检测能力(汽车辆)
	@Column
	private Integer jcryzs;	//	检测人员总数
	@Column
	private Integer wjgwrs;	//	外检工位人数
	@Column
	private Integer lrgwrs;	//	录入工位人数
	@Column
	private Integer ycyrs;	//	引车员人数
	@Column
	private Integer dpgwrs;	//	底盘工位人数
	@Column
	private Integer zjgwrs;	//	总检工位人数
	@Column
	private Integer qtgwrs;	//	其他工位人数
	@Column
	private Integer tgszjbmkhrs;	//	通过省级质检部门考核人数
	@Column
	private Integer wtgszjbmkhrs;	//	未通过省级质检部门考核人数
	@Column(length = 10)
	private String fzjg;	//	发证机关
	@Column(length = 12)
	private String glbm;	//	管理部门
	@Column
	private Date gxrq;	//	更新日期
	@Column(length = 4000)
	private String bz;	//	备注
	@Column
	private Integer shejirjcmtsl;	//	设计日检测能力(摩托辆)
	@Column
	private Integer shijirjcmtsl;	//	实际日检测能力(摩托辆)
	@Column(length = 2)
	private String shbj;	//	审核标记
	@Column(length = 4000)
	private String syglbm;	//	使用管理部门
	@Column(length = 4000)
	private String shyj;	//	审核意见
	@Column(length = 1)
	private String zt;	//	状态标记 
	@Column(length = 512)
	private String ztyy;	//	暂停原因
	@Column(length = 300)
	private String dwdz;	//	单位地址
	@Column(length = 6)
	private String yzbm;	//	邮政编码
	@Column(length = 100)
	private String xkjyfw;	//	许可检验范围
	@Column(length = 300)
	private String rdsffdw;	//	资格许可发放单位
	@Column(length = 30)
	private String frdb;	//	法人代表
	@Column(length = 18)
	private String frdbsfzh;	//	法人代表身份证号
	@Column(length = 15)
	private String frdblxdh;	//	法人代表联系电话
	@Column(length = 30)
	private String fzr;	//	负责人
	@Column(length = 18)
	private String fzrsfzh;	//	负责人身份证号
	@Column(length = 15)
	private String fzrlxdh;	//	负责人联系电话
	@Column(length = 30)
	private String rclxr;	//	日常联系人
	@Column(length = 18)
	private String rclxrsfzh;	//	日常联系人身份证号
	@Column(length = 15)
	private String rclxrlxdh;	//	日常联系人联系电话
	public String getJczbh() {
		return jczbh;
	}
	public String getJczmc() {
		return jczmc;
	}
	public String getSflw() {
		return sflw;
	}
	public String getRdsbh() {
		return rdsbh;
	}
	public Date getRdyxqs() {
		return rdyxqs;
	}
	public Date getRdyxqz() {
		return rdyxqz;
	}
	public Integer getShejirjcnl() {
		return shejirjcnl;
	}
	public Integer getShijirjcnl() {
		return shijirjcnl;
	}
	public Integer getJcryzs() {
		return jcryzs;
	}
	public Integer getWjgwrs() {
		return wjgwrs;
	}
	public Integer getLrgwrs() {
		return lrgwrs;
	}
	public Integer getYcyrs() {
		return ycyrs;
	}
	public Integer getDpgwrs() {
		return dpgwrs;
	}
	public Integer getZjgwrs() {
		return zjgwrs;
	}
	public Integer getQtgwrs() {
		return qtgwrs;
	}
	public Integer getTgszjbmkhrs() {
		return tgszjbmkhrs;
	}
	public Integer getWtgszjbmkhrs() {
		return wtgszjbmkhrs;
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
	public String getBz() {
		return bz;
	}
	public Integer getShejirjcmtsl() {
		return shejirjcmtsl;
	}
	public Integer getShijirjcmtsl() {
		return shijirjcmtsl;
	}
	public String getShbj() {
		return shbj;
	}
	public String getSyglbm() {
		return syglbm;
	}
	public String getShyj() {
		return shyj;
	}
	public String getZt() {
		return zt;
	}
	public String getZtyy() {
		return ztyy;
	}
	public String getDwdz() {
		return dwdz;
	}
	public String getYzbm() {
		return yzbm;
	}
	public String getXkjyfw() {
		return xkjyfw;
	}
	public String getRdsffdw() {
		return rdsffdw;
	}
	public String getFrdb() {
		return frdb;
	}
	public String getFrdbsfzh() {
		return frdbsfzh;
	}
	public String getFrdblxdh() {
		return frdblxdh;
	}
	public String getFzr() {
		return fzr;
	}
	public String getFzrsfzh() {
		return fzrsfzh;
	}
	public String getFzrlxdh() {
		return fzrlxdh;
	}
	public String getRclxr() {
		return rclxr;
	}
	public String getRclxrsfzh() {
		return rclxrsfzh;
	}
	public String getRclxrlxdh() {
		return rclxrlxdh;
	}
	public void setJczbh(String jczbh) {
		this.jczbh = jczbh;
	}
	public void setJczmc(String jczmc) {
		this.jczmc = jczmc;
	}
	public void setSflw(String sflw) {
		this.sflw = sflw;
	}
	public void setRdsbh(String rdsbh) {
		this.rdsbh = rdsbh;
	}
	public void setRdyxqs(Date rdyxqs) {
		this.rdyxqs = rdyxqs;
	}
	public void setRdyxqz(Date rdyxqz) {
		this.rdyxqz = rdyxqz;
	}
	public void setShejirjcnl(Integer shejirjcnl) {
		this.shejirjcnl = shejirjcnl;
	}
	public void setShijirjcnl(Integer shijirjcnl) {
		this.shijirjcnl = shijirjcnl;
	}
	public void setJcryzs(Integer jcryzs) {
		this.jcryzs = jcryzs;
	}
	public void setWjgwrs(Integer wjgwrs) {
		this.wjgwrs = wjgwrs;
	}
	public void setLrgwrs(Integer lrgwrs) {
		this.lrgwrs = lrgwrs;
	}
	public void setYcyrs(Integer ycyrs) {
		this.ycyrs = ycyrs;
	}
	public void setDpgwrs(Integer dpgwrs) {
		this.dpgwrs = dpgwrs;
	}
	public void setZjgwrs(Integer zjgwrs) {
		this.zjgwrs = zjgwrs;
	}
	public void setQtgwrs(Integer qtgwrs) {
		this.qtgwrs = qtgwrs;
	}
	public void setTgszjbmkhrs(Integer tgszjbmkhrs) {
		this.tgszjbmkhrs = tgszjbmkhrs;
	}
	public void setWtgszjbmkhrs(Integer wtgszjbmkhrs) {
		this.wtgszjbmkhrs = wtgszjbmkhrs;
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
	public void setBz(String bz) {
		this.bz = bz;
	}
	public void setShejirjcmtsl(Integer shejirjcmtsl) {
		this.shejirjcmtsl = shejirjcmtsl;
	}
	public void setShijirjcmtsl(Integer shijirjcmtsl) {
		this.shijirjcmtsl = shijirjcmtsl;
	}
	public void setShbj(String shbj) {
		this.shbj = shbj;
	}
	public void setSyglbm(String syglbm) {
		this.syglbm = syglbm;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public void setZtyy(String ztyy) {
		this.ztyy = ztyy;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public void setXkjyfw(String xkjyfw) {
		this.xkjyfw = xkjyfw;
	}
	public void setRdsffdw(String rdsffdw) {
		this.rdsffdw = rdsffdw;
	}
	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}
	public void setFrdbsfzh(String frdbsfzh) {
		this.frdbsfzh = frdbsfzh;
	}
	public void setFrdblxdh(String frdblxdh) {
		this.frdblxdh = frdblxdh;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public void setFzrsfzh(String fzrsfzh) {
		this.fzrsfzh = fzrsfzh;
	}
	public void setFzrlxdh(String fzrlxdh) {
		this.fzrlxdh = fzrlxdh;
	}
	public void setRclxr(String rclxr) {
		this.rclxr = rclxr;
	}
	public void setRclxrsfzh(String rclxrsfzh) {
		this.rclxrsfzh = rclxrsfzh;
	}
	public void setRclxrlxdh(String rclxrlxdh) {
		this.rclxrlxdh = rclxrlxdh;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
