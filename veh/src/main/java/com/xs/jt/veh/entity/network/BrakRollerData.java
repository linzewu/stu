package com.xs.jt.veh.entity.network;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.veh.common.Constants;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.util.CommonUtil;


@Scope("prototype")
@Component("brakRollerData")
@Entity
@Table(name = "TM_BrakRollerData")
public class BrakRollerData extends BaseDeviceData {
	
	public static final Integer JSZT_ZCJS=0;
	public static final Integer JSZT_ZLBS=1;
	public static final Integer JSZT_YLBS=2;
	public static final Integer JSZT_ZYLBS=3;
	
	/**
	 * 是加载轴
	 */
	public static final Integer SFJZZ_YES=1;
	
	/**
	 * 不是加载轴
	 */
	public static final Integer SFJZZ_NO=0;

	public BrakRollerData() {
		leftData = new ArrayList<Integer>();
		rigthData = new ArrayList<Integer>();
	}

	// 左阻滞力
	@Column
	private Integer zzzl;

	// 右阻滞力
	@Column
	private Integer yzzl;

	// 左制动力
	@Column
	private Integer zzdl;

	// 右制动力
	@Column
	private Integer yzdl;

	// 左最大力差点
	@Column
	private Integer zzdlcd;

	// 右最大力差点
	@Column
	private Integer yzdlcd;

	// 过程差
	@Column
	private Float gcc;

	// 左边过程数据
	@Transient
	private List<Integer> leftData;

	// 右边过程数据
	@Transient
	private List<Integer> rigthData;

	//检测结束的状态 0 正常结束，1左轮抱死 2右轮抱死 3左右轮抱死
	@Column
	private Integer jszt;

	@Column
	private Integer zw;

	// 空载行车制动率
	@Column
	private Float kzxczdl;

	// 空载不平衡率
	@Column
	private Float kzbphl;

	// 加载制动率
	@Column
	private Float jzzzdl;

	// 加载轴荷
	@Column
	private Integer jzzh;

	// 加载不平衡率
	@Column
	private Float jzbphl;

	// 空载制动率限值
	@Column
	private Integer kzzdlxz;

	// 空载制动率判定
	@Column
	private Integer kzzdlpd;

	// 加载制动率限值
	@Column
	private Integer jzzdlxz;

	// 加载制动率判定
	@Column
	private Integer jzzdlpd;

	// 不平衡率限值
	@Column
	private Float bphlxz;

	// 空载不平衡率判定
	@Column
	private Integer kzbphlpd;

	// 加载不平衡率判定
	@Column
	private Integer jzbphlpd;
	
	//是否加载轴
	@Column
	private Integer sfjzz=SFJZZ_NO;
	
	//加载轴荷 左
	@Column
	private Integer jzzlh;
	
	//加载轴荷 右
	@Column
	private Integer jzylh;
	
	//轴荷 左
	@Column
	private Integer zlh;
	
	//轴荷 右
	@Column
	private Integer ylh;
	
	//动态轴荷 左
	@Column
	private Integer zdtlh;
	
	//动态轴荷 右
	@Column
	private Integer ydtlh;
	
	@Column(length = 8000)
	private String leftDataStr;

	@Column(length = 8000)
	private String rigthDataStr;
	
	@Column(length = 8000)
	private String zdtlhStr;

	@Column(length = 8000)
	private String ydtlhStr;
	
	@Column(length = 8000)
	private String jzLeftDataStr;

	@Column(length = 8000)
	private String jzRigthDataStr;
	
	//加载左制动力
	@Column
	private Integer jzzzdli;
	
	//加载右制动力
	@Column
	private Integer jzyzdli;

	//加载左制动力差点
	@Column
	private Integer jzzzdlcd;
	
	//加载右制动力差点
	@Column
	private Integer jzyzdlcd;
	
	
	

	public Integer getJzzzdlcd() {
		return jzzzdlcd;
	}

	public Integer getJzyzdlcd() {
		return jzyzdlcd;
	}

	public void setJzzzdlcd(Integer jzzzdlcd) {
		this.jzzzdlcd = jzzzdlcd;
	}

	public void setJzyzdlcd(Integer jzyzdlcd) {
		this.jzyzdlcd = jzyzdlcd;
	}

	public Integer getJzzzdli() {
		return jzzzdli;
	}

	public Integer getJzyzdli() {
		return jzyzdli;
	}

	public void setJzzzdli(Integer jzzzdli) {
		this.jzzzdli = jzzzdli;
	}

	public void setJzyzdli(Integer jzyzdli) {
		this.jzyzdli = jzyzdli;
	}

	public String getZdtlhStr() {
		return zdtlhStr;
	}

	public String getYdtlhStr() {
		return ydtlhStr;
	}

	public String getJzLeftDataStr() {
		return jzLeftDataStr;
	}

	public String getJzRigthDataStr() {
		return jzRigthDataStr;
	}

	public void setZdtlhStr(String zdtlhStr) {
		this.zdtlhStr = zdtlhStr;
	}

	public void setYdtlhStr(String ydtlhStr) {
		this.ydtlhStr = ydtlhStr;
	}

	public void setJzLeftDataStr(String jzLeftDataStr) {
		this.jzLeftDataStr = jzLeftDataStr;
	}

	public void setJzRigthDataStr(String jzRigthDataStr) {
		this.jzRigthDataStr = jzRigthDataStr;
	}

	public Integer getZdtlh() {
		return zdtlh;
	}

	public Integer getYdtlh() {
		return ydtlh;
	}

	public void setZdtlh(Integer zdtlh) {
		this.zdtlh = zdtlh;
	}

	public void setYdtlh(Integer ydtlh) {
		this.ydtlh = ydtlh;
	}

	public Integer getZlh() {
		return zlh;
	}

	public Integer getYlh() {
		return ylh;
	}

	public void setZlh(Integer zlh) {
		this.zlh = zlh;
	}

	public void setYlh(Integer ylh) {
		this.ylh = ylh;
	}

	public Integer getJzzlh() {
		return jzzlh;
	}

	public Integer getJzylh() {
		return jzylh;
	}

	public void setJzzlh(Integer jzzlh) {
		this.jzzlh = jzzlh;
	}

	public void setJzylh(Integer jzylh) {
		this.jzylh = jzylh;
	}

	public Integer getSfjzz() {
		return sfjzz;
	}

	public void setSfjzz(Integer sfjzz) {
		this.sfjzz = sfjzz;
	}

	public Integer getKzbphlpd() {
		return kzbphlpd;
	}

	public Integer getJzbphlpd() {
		return jzbphlpd;
	}

	public void setKzbphlpd(Integer kzbphlpd) {
		this.kzbphlpd = kzbphlpd;
	}

	public void setJzbphlpd(Integer jzbphlpd) {
		this.jzbphlpd = jzbphlpd;
	}

	public Integer getJzzdlxz() {
		return jzzdlxz;
	}

	public Integer getJzzdlpd() {
		return jzzdlpd;
	}

	public Float getBphlxz() {
		return bphlxz;
	}

	public void setJzzdlxz(Integer jzzdlxz) {
		this.jzzdlxz = jzzdlxz;
	}

	public void setJzzdlpd(Integer jzzdlpd) {
		this.jzzdlpd = jzzdlpd;
	}

	public void setBphlxz(Float bphlxz) {
		this.bphlxz = bphlxz;
	}

	public Integer getKzzdlxz() {
		return kzzdlxz;
	}

	public Integer getKzzdlpd() {
		return kzzdlpd;
	}

	public void setKzzdlxz(Integer kzzdlxz) {
		this.kzzdlxz = kzzdlxz;
	}

	public void setKzzdlpd(Integer kzzdlpd) {
		this.kzzdlpd = kzzdlpd;
	}

	/**
	 * 加载轴制动率
	 * 
	 * @return
	 */
	public Float getJzzzdl() {
		return jzzzdl;
	}

	/**
	 * 加载轴制动率
	 * 
	 * @param jzzzdl
	 */
	public void setJzzzdl(Float jzzzdl) {
		this.jzzzdl = jzzzdl;
	}

	/**
	 * 空载行车制动率
	 * 
	 * @return
	 */
	public Float getKzxczdl() {
		return kzxczdl;
	}

	/**
	 * 空载不平衡率
	 * 
	 * @return
	 */
	public Float getKzbphl() {
		return kzbphl;
	}

	/**
	 * 加载轴荷
	 * 
	 * @return
	 */
	public Integer getJzzh() {
		return jzzh;
	}

	/**
	 * 加载不平衡率
	 * 
	 * @return
	 */
	public Float getJzbphl() {
		return jzbphl;
	}

	/**
	 * 空载行车制动力
	 * 
	 * @param kzxczdl
	 */
	public void setKzxczdl(Float kzxczdl) {
		this.kzxczdl = kzxczdl;
	}

	/**
	 * 空载不平衡率
	 * 
	 * @param kzbphl
	 */
	public void setKzbphl(Float kzbphl) {
		this.kzbphl = kzbphl;
	}

	/**
	 * 加载轴荷
	 * 
	 * @param jzzh
	 */
	public void setJzzh(Integer jzzh) {
		this.jzzh = jzzh;
	}

	/**
	 * 加载不平衡率
	 * 
	 * @param jzbphl
	 */
	public void setJzbphl(Float jzbphl) {
		this.jzbphl = jzbphl;
	}

	/**
	 * 轴位
	 * 
	 * @return
	 */
	public Integer getZw() {
		return zw;
	}

	public void setZw(Integer zw) {
		this.zw = zw;
	}

	public Integer getJszt() {
		return jszt;
	}

	public void setJszt(Integer jszt) {
		this.jszt = jszt;
	}

	/**
	 * 左阻滞力
	 * 
	 * @return
	 */
	public Integer getZzzl() {
		return zzzl;
	}

	/**
	 * 右阻滞力
	 * 
	 * @return
	 */
	public Integer getYzzl() {
		return yzzl;
	}

	/**
	 * 左制动力
	 * 
	 * @return
	 */
	public Integer getZzdl() {
		return zzdl;
	}

	/**
	 * 右制动力
	 * 
	 * @return
	 */
	public Integer getYzdl() {
		return yzdl;
	}

	/**
	 * 左最大力差点
	 * 
	 * @return
	 */
	public Integer getZzdlcd() {
		return zzdlcd;
	}

	/**
	 * 右最大力差点
	 * 
	 * @return
	 */
	public Integer getYzdlcd() {
		return yzdlcd;
	}

	/**
	 * 过程差
	 * 
	 * @return
	 */
	public Float getGcc() {
		return gcc;
	}

	/**
	 * 左阻滞力
	 * 
	 * @return
	 */
	public void setZzzl(Integer zzzl) {
		this.zzzl = zzzl;
	}

	/**
	 * 右阻滞力
	 * 
	 * @param yzzl
	 */
	public void setYzzl(Integer yzzl) {
		this.yzzl = yzzl;
	}

	/**
	 * 左制动力
	 * 
	 * @param zzdl
	 */
	public void setZzdl(Integer zzdl) {
		this.zzdl = zzdl;
	}

	/**
	 * 右制动力
	 * 
	 * @param yzdl
	 */
	public void setYzdl(Integer yzdl) {
		this.yzdl = yzdl;
	}

	/**
	 * 左制动力差点
	 * 
	 * @param zzdlcd
	 */
	public void setZzdlcd(Integer zzdlcd) {
		this.zzdlcd = zzdlcd;
	}

	/**
	 * 右制动力差点
	 * 
	 * @param yzdlcd
	 */
	public void setYzdlcd(Integer yzdlcd) {
		this.yzdlcd = yzdlcd;
	}

	/**
	 * 过程差
	 * 
	 * @param gcc
	 */
	public void setGcc(Float gcc) {
		this.gcc = gcc;
	}

	/**
	 * 左轮实时数据
	 * 
	 * @return
	 */
	public List<Integer> getLeftData() {
		return leftData;
	}

	/**
	 * 右轮实时数据
	 * 
	 * @return
	 */
	public List<Integer> getRigthData() {
		return rigthData;
	}

	/**
	 * 左轮实时数据
	 * 
	 * @return
	 */
	public void setLeftData(List<Integer> leftData) {
		this.leftData = leftData;
	}

	/**
	 * 左轮实时数据
	 * 
	 * @return
	 */
	public void setRigthData(List<Integer> rigthData) {
		this.rigthData = rigthData;
	}

	public String getLeftDataStr() {
		return leftDataStr;
	}

	public String getRigthDataStr() {
		return rigthDataStr;
	}

	public void setLeftDataStr(String leftDataStr) {
		this.leftDataStr = leftDataStr;
	}

	public void setRigthDataStr(String rigthDataStr) {
		this.rigthDataStr = rigthDataStr;
	}

	
	@Override
	public String toString() {
		return "BrakRollerData [zzzl=" + zzzl + ", yzzl=" + yzzl + ", zzdl=" + zzdl + ", yzdl=" + yzdl + ", zzdlcd="
				+ zzdlcd + ", yzdlcd=" + yzdlcd + ", gcc=" + gcc + ", leftData=" + leftData + ", rigthData=" + rigthData
				+ ", jszt=" + jszt + ", zw=" + zw + ", kzxczdl=" + kzxczdl + ", kzbphl=" + kzbphl + ", jzzzdl=" + jzzzdl
				+ ", jzzh=" + jzzh + ", jzbphl=" + jzbphl + ", kzzdlxz=" + kzzdlxz + ", kzzdlpd=" + kzzdlpd
				+ ", jzzdlxz=" + jzzdlxz + ", jzzdlpd=" + jzzdlpd + ", bphlxz=" + bphlxz + ", kzbphlpd=" + kzbphlpd
				+ ", jzbphlpd=" + jzbphlpd + ", sfjzz=" + sfjzz + ", jzzlh=" + jzzlh + ", jzylh=" + jzylh + ", zlh="
				+ zlh + ", ylh=" + ylh + ", zdtlh=" + zdtlh + ", ydtlh=" + ydtlh + ", leftDataStr=" + leftDataStr
				+ ", rigthDataStr=" + rigthDataStr + ", zdtlhStr=" + zdtlhStr + ", ydtlhStr=" + ydtlhStr
				+ ", jzLeftDataStr=" + jzLeftDataStr + ", jzRigthDataStr=" + jzRigthDataStr + ", jzzzdli=" + jzzzdli
				+ ", jzyzdli=" + jzyzdli + ", jzzzdlcd=" + jzzzdlcd + ", jzyzdlcd=" + jzyzdlcd + "]";
	}

	/**
	 * 空载限制标准
	 * 
	 * @param vehCheckLogin
	 */
	public void setKzzdlxz(VehCheckLogin vehCheckLogin) {

		String cllx = vehCheckLogin.getCllx();
		Integer zbzl = vehCheckLogin.getZbzl();
		
		String qdxs=vehCheckLogin.getQdxs();
		
		if(this.getJyxm().indexOf("L")== 0) {
			this.kzzdlxz = 50;
			return;
		}
		
		Integer zw=this.zw;
		if(zw==2&&(qdxs.equals("3")||qdxs.equals("4")||qdxs.equals("34"))){
			zw=1;
		}
		

		if (cllx.indexOf("N") == 0 && zw > 1) {
			this.kzzdlxz = 60;
			return;
		}

		if (zw == 1 && (cllx.indexOf("K") == 0 || zbzl < 3500)) {
			this.kzzdlxz = 60;
			return;
		}

		if (zw > 1 && (cllx.indexOf("K") == 0 && zbzl < 3500)) {
			this.kzzdlxz = 20;
			return;
		}
		
		if (zw > 1 && (cllx.indexOf("K") == 0 && zbzl >= 3500)) {
			this.kzzdlxz = 40;
			return;
		}

		// 普通摩托车
		if (zw == 1 && (cllx.indexOf("M11") == 0 || cllx.indexOf("M21") == 0)) {
			this.kzzdlxz = 60;
			return;
		}
		// 普通摩托车
		if (zw > 1 && (cllx.indexOf("M11") == 0 || cllx.indexOf("M21") == 0)) {
			this.kzzdlxz = 55;
			return;
		}

		// 轻便摩托车
		if (zw == 1 && (cllx.indexOf("M12") == 0 || cllx.indexOf("M22") == 0)) {
			this.kzzdlxz = 60;
			return;
		}

		// 轻便摩托车
		if (zw > 1 && (cllx.indexOf("M12") == 0 || cllx.indexOf("M22") == 0)) {
			this.kzzdlxz = 50;
			return;
		}
		
		//挂车
		if((cllx.indexOf("G") == 0)||(cllx.indexOf("B") == 0)){
			this.kzzdlxz = 55;
			return;
		}
		

		// 其他汽车
		if (zw == 1 ) {
			this.kzzdlxz = 60;
			return;
		}

		// 其他汽车
		if (zw > 1 ) {
			this.kzzdlxz = 50;
			return;
		}
	}

	/**
	 * 加载限制标准
	 * 
	 * @param vehCheckLogin
	 */
	public void setJzzdlxz(VehCheckLogin vehCheckLogin) {

		String cllx = vehCheckLogin.getCllx();
		Integer zbzl = vehCheckLogin.getZbzl();
		Integer zs = vehCheckLogin.getZs();
		
		String qdxs=vehCheckLogin.getQdxs();
		
		Integer zw=this.zw;
		if(zw==2&&(qdxs.equals("3")||qdxs.equals("4")||qdxs.equals("34"))){
			zw=1;
		}

		// 三轴以上车辆需要加载
		if (zs < 3) {
			return;
		}

		// 客车及3.5T以下车辆 前轴
		if (zw == 1 && (cllx.indexOf("K") == 0 || zbzl < 3500)) {
			this.jzzdlxz = 60;
			return;
		}
		// 客车及3.5T以下车辆 后轴
		if (zw > 1 && (cllx.indexOf("K") == 0 && zbzl < 3500)) {
			this.jzzdlxz = 20;
			return;
		}
		
		//挂车
		if((cllx.indexOf("G") == 0)||(cllx.indexOf("B") == 0)){
			this.kzzdlxz = 55;
			return;
		}

		// 其他汽车
		if (zw == 1) {
			this.jzzdlxz = 60;
			return;
		}

		// 其他汽车
		if (zw > 1 ) {
			this.kzzdlxz = 50;
			return;
		}
	}

	public void setBphlxz(VehCheckLogin vehCheckLogin) {

		String jylb = vehCheckLogin.getJylb();
		// 轴荷
		Integer zh = zlh +ylh;
		
		if(this.getZdtlh()!=null&&this.getYdtlh()!=null){
			zh=this.getZdtlh()+this.getYdtlh();
		}

		Integer zdl = this.zzdl + this.yzdl;

		Float temp = (float) (zh * 0.98 * 0.6);
		
		String qdxs=vehCheckLogin.getQdxs();
		
		Integer zw=this.zw;
		if(zw==2&&(qdxs.equals("3")||qdxs.equals("4")||qdxs.equals("34"))){
			zw=1;
		}

		// 注册登记
		if (jylb.equals("00")) {
			if (zw == 1) {
				this.bphlxz = 20f;
			} else {
				if (zdl >= temp) {
					this.bphlxz = 24f;
				} else {
					this.bphlxz = 8f;
				}
			}
		} else {
			if (zw == 1) {
				this.bphlxz = 24f;
			} else {
				if (zdl >= temp) {
					this.bphlxz = 30f;
				} else {
					this.bphlxz = 10f;
				}
			}
		}
	}

	public void setKzzdlpd() {
		if (this.kzzdlxz == null || this.kzxczdl == null) {
			return;
		}
		if (kzxczdl >= this.kzzdlxz) {
			this.kzzdlpd = Constants.PDJG_HG;
		} else {
			this.kzzdlpd = Constants.PDJG_BHG;
		}
	}

	/**
	 * 加载制动率判定
	 */
	public void setJzzdlpd() {
		if (this.jzzdlxz == null || this.jzzzdl == null) {
			return;
		}
		if (jzzzdl >= this.jzzdlxz) {
			this.jzzdlpd = Constants.PDJG_HG;
		} else {
			this.jzzdlpd = Constants.PDJG_BHG;
		}
	}

	/**
	 * 设置加载不平衡率判定
	 */
	public void setJzbphlpd() {
		if (this.jzbphl == null || this.bphlxz == null) {
			return;
		}
		if (this.jzbphl <= this.bphlxz) {
			this.jzbphlpd = Constants.PDJG_HG;
		} else {
			this.jzbphlpd = Constants.PDJG_BHG;
		}
	}

	/**
	 * 设置空载不平衡率判定
	 */
	public void setKzbphlpd() {

		if (this.kzbphl == null || this.bphlxz == null) {
			return;
		}

		if (this.kzbphl <= this.bphlxz) {
			this.kzbphlpd = Constants.PDJG_HG;
		} else {
			this.kzbphlpd = Constants.PDJG_BHG;
		}
	}

	/**
	 * 空载 设置不平衡率
	 * 
	 * @return
	 */
	public void setKzbphl(VehCheckLogin vehCheckLogin) {

		if (yzdl == null || zzdl == null) {
			return;
		}
		
		Float zdzdl=null;
		String qdxs=vehCheckLogin.getQdxs();
		Integer zw=this.zw;
		if(zw==2&&(qdxs.equals("3")||qdxs.equals("4")||qdxs.equals("34"))){
			zw=1;
		}

		
		if(zw>1&&kzxczdl<60){
			zdzdl= (zlh + ylh)*0.98f;
		}else{
			zdzdl =  zzdl > yzdl ? zzdl.floatValue() : yzdl.floatValue();
		}

		
		
		Float bphl = (float) (Math.abs(zzdlcd-yzdlcd) * 1.0 / zdzdl * 1.0) * 100;
		this.kzbphl = CommonUtil.MathRound1(bphl);
	}
	
	
	/**
	 * 加载 设置不平衡率
	 * 
	 * @return
	 */
	public void setJzbphl(VehCheckLogin vehCheckLogin) {

		if (jzyzdli == null || jzzzdli == null ) {
			return;
		}
		
		Float zdzdl=null;
		
		String qdxs=vehCheckLogin.getQdxs();
		Integer zw=this.zw;
		if(zw==2&&(qdxs.equals("3")||qdxs.equals("4")||qdxs.equals("34"))){
			zw=1;
		}
		
		if(zw>1&&jzzzdl<60){
			zdzdl= (jzzlh+jzylh)*0.98f;
		}else{
			zdzdl =  jzzzdli > jzyzdli ? jzzzdli.floatValue() : jzyzdli.floatValue();
		}
		
		Float bphl = (float) (Math.abs(jzzzdlcd-jzyzdlcd) * 1.0 / zdzdl * 1.0) * 100;
		this.jzbphl = CommonUtil.MathRound1(bphl);

	}

	/**
	 * 设置空载行车制动率
	 */
	public void setKzxczdl(VehCheckLogin vehCheckLogin) {		Integer zh = zlh + ylh;
		
		String cllx =vehCheckLogin.getCllx();
		
		Integer zzl = vehCheckLogin.getZzl();
		
		//如果动态轮荷有值 而且是乘用车 则取动态轮荷
		if(this.getZdtlh()!=null&&this.getYdtlh()!=null&&cllx.indexOf("K")==0){
			zh=this.getZdtlh()+this.getYdtlh();
		}
		
		//貌似新国标
		if(this.getZdtlh()!=null&&this.getYdtlh()!=null&&zzl<=3500){
			zh=this.getZdtlh()+this.getYdtlh();
		}
		
		Integer zdl = this.getZzdl() + this.getYzdl();
		Float xczdl = (float) (zdl * 1.0 /(zh * 0.98)) * 100;
		this.kzxczdl = CommonUtil.MathRound1(xczdl);
	}
	
	/**
	 * 设置加载制动率
	 * @param weighData
	 */
	public void setJzzdl() {

		if (sfjzz==SFJZZ_NO||this.getJzzlh()==null||this.getJzylh()==null||this.getJzzzdli()==null||this.getJzyzdli()==null) {
			return;
		}
		Integer zh = this.getJzzlh()+this.getJzylh();
		Integer zdl = this.getJzzzdli() + this.getJzyzdli();
		Float xczdl = (float) (zdl * 1.0 / (zh * 0.98)) * 100;
		this.jzzzdl = CommonUtil.MathRound1(xczdl);
	}

	@Override
	public void setZpd() {
		if (this.kzbphlpd == PDJG_BHG || this.kzzdlpd == PDJG_BHG) {
			this.setZpd(PDJG_BHG);
		}else{
			this.setZpd(PDJG_HG);
		}
	}
	
	

}
