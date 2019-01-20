package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.veh.common.Constants;
import com.xs.jt.veh.entity.VehCheckLogin;


@Scope("prototype")
@Component("lightData")
@Entity
@Table(name = "TM_LightData")
public class LightData extends BaseDeviceData {

	private static Log logger = LogFactory.getLog(LightData.class);

	/**
	 * 光型 远
	 */
	public static final char GX_YGD = 'Y';

	/**
	 * 光型 近
	 */
	public static final char GX_JGD = 'J';

	/**
	 * 灯型 主灯
	 */
	public static final Integer DX_ZD = 0;

	/**
	 * 灯型 副灯
	 */
	public static final Integer DX_FD = 1;

	/**
	 * 位置 左
	 */
	public static final char WZ_Z = 'L';

	/**
	 * 位置 右
	 */
	public static final char WZ_Y = 'R';

	/**
	 * 水平偏差
	 */
	@Column(length = 32)
	private String sppc;

	/**
	 * 垂直偏差
	 */
	@Column(length = 32)
	private String czpc;

	/**
	 * 光强
	 */
	@Column
	private Integer gq;

	/**
	 * 灯高
	 */
	@Column
	private Integer dg;

	@Column
	private char wz;

	@Column
	private char gx;

	@Column
	private Integer dx;

	@Column
	private Float czpy;

	// 光强限值
	@Column
	private Integer gqxz;

	// 垂直偏移限值 逗号分隔
	@Column(length = 32)
	private String czpyxz;

	// 光强判定
	@Column
	private Integer gqpd;

	// 垂直偏移判定
	@Column
	private Integer czpypd;

	public Integer getGqpd() {
		return gqpd;
	}

	public Integer getCzpypd() {
		return czpypd;
	}

	public void setGqpd(Integer gqpd) {
		this.gqpd = gqpd;
	}

	public void setCzpypd(Integer czpypd) {
		this.czpypd = czpypd;
	}

	public Integer getGqxz() {
		return gqxz;
	}

	public String getCzpyxz() {
		if(dg<1000) {
			this.czpyxz ="-300,-50";
		}else {
			this.czpyxz ="-350,-100";
		}
		return czpyxz;
	}

	public void setGqxz(Integer gqxz) {
		this.gqxz = gqxz;
	}

	public void setCzpyxz(String czpyxz) {
		this.czpyxz = czpyxz;
	}

	@Override
	public String toString() {
		return "LightData [sppc=" + sppc + ", czpc=" + czpc + ", gq=" + gq + ", dg=" + dg + ", wz=" + wz + ", gx=" + gx
				+ ", dx=" + dx + "]";
	}

	public Float getCzpy() {
		return czpy;
	}

	public void setCzpy(Float czpy) {
		this.czpy = czpy;
	}

	public Integer getDx() {
		return dx;
	}

	public void setDx(Integer dx) {
		this.dx = dx;
	}

	public char getWz() {
		return wz;
	}

	public char getGx() {
		return gx;
	}

	public String getSppc() {
		return sppc;
	}

	public String getCzpc() {
		return czpc;
	}

	public Integer getGq() {
		return gq;
	}

	public Integer getDg() {
		return dg;
	}

	public void setWz(char wz) {
		this.wz = wz;
	}

	public void setGx(char gx) {
		this.gx = gx;
	}

	public void setSppc(String sppc) {
		this.sppc = sppc;
	}

	public void setCzpc(String czpc) {
		this.czpc = czpc;
	}

	public void setGq(Integer gq) {
		this.gq = gq;
	}

	public void setDg(Integer dg) {
		this.dg = dg;
	}

	public String getJyxm() {

		if (wz == WZ_Z && dx == DX_ZD) {
			return "H1";
		}
		if (wz == WZ_Z && dx == DX_FD) {
			return "H2";
		}

		if (wz == WZ_Y && dx == DX_ZD) {
			return "H4";
		}
		if (wz == WZ_Y && dx == DX_FD) {
			return "H3";
		}
		return null;
	}

	public void setCzpy() {
		try {
			if (this.czpc == null || this.dg == null) {
				return;
			}
			Integer l = Integer.parseInt(this.czpc.trim());
			Integer h = this.dg;
			if (h == 0) {
				return;
			}
			this.czpy = 1 + (l * 1f / h * 1f);

			this.czpy = (float) (Math.round(czpy * 100)) / 100;
		} catch (Exception e) {
			logger.error("设置垂直偏移出错", e);
		}

	}

	public void setGqpd() {
		if (this.gqxz == null || this.gqxz == null || gq == null) {
			return;
		}
		this.gqpd = gq >= this.gqxz ? Constants.PDJG_HG : Constants.PDJG_BHG;
	}

	public void setDgpdxz(VehCheckLogin vehCheckLogin) {
		String cllx = vehCheckLogin.getCllx();
		String jylb = vehCheckLogin.getJylb();
		String qzdz = vehCheckLogin.getQzdz();
		// 近光灯不判断
		if (this.getGx() == LightData.GX_JGD) {
			return;
		}
		// 新车注册
		if (jylb.equals("00")) {
			// 农用车（三轮汽车）
			if (cllx.indexOf("N") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(8000);
				} else {
					this.setGqxz(6000);
				}
			}
			// 普通摩托车
			else if (cllx.indexOf("M11") == 0 || cllx.indexOf("M21") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(10000);
				} else {
					this.setGqxz(8000);
				}
			}
			// 轻便摩托车
			else if (cllx.indexOf("M12") == 0 || cllx.indexOf("M22") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(4000);
				} else {
					this.setGqxz(3000);
				}
			}
			// 其他汽车
			else {
				// 二灯远近光
				if (qzdz.equals("03")) {
					this.setGqxz(18000);
				} else if (qzdz.equals("01") || qzdz.equals("02")) {
					this.setGqxz(15000);
				}
			}
		} else {
			// 在用车注册
			// 农用车（三轮汽车）
			if (cllx.indexOf("N") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(6000);
				} else {
					this.setGqxz(5000);
				}
			}
			// 普通摩托车
			else if (cllx.indexOf("M11") == 0 || cllx.indexOf("M21") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(8000);
				} else {
					this.setGqxz(6000);
				}
			}
			// 轻便摩托车
			else if (cllx.indexOf("M12") == 0 || cllx.indexOf("M22") == 0) {
				// 一灯制
				if (qzdz.equals("05")) {
					this.setGqxz(3000);
				} else {
					this.setGqxz(2500);
				}
			}
			// 其他汽车
			else {
				// 二灯远近光
				if (qzdz.equals("03")) {
					this.setGqxz(15000);
				} else if (qzdz.equals("01") || qzdz.equals("02")) {
					this.setGqxz(12000);
				}
			}
		}
	}

	public void setCzpyxz(VehCheckLogin vehCheckLogin) {
		String cllx = vehCheckLogin.getCllx();
		String syxz = vehCheckLogin.getSyxz();
		// 非运营 小型 微型载客汽车 三轮汽车 除外 不需要判定
		if ((cllx.indexOf("K3") == 0 || cllx.indexOf("K4") == 0 || cllx.indexOf("N") == 0) && syxz.equals("A")) {
			return;
		}
		/*if (this.gx == GX_JGD) {
			if (cllx.indexOf("K") == 0) {
				this.czpyxz = "0.7,0.9";
			} else {
				this.czpyxz = "0.6,0.8";
			}
		}
		if (this.gx == GX_YGD) {
			this.czpyxz = "0.8,0.95";
		}*/
		
		if(dg>1000) {
			this.czpyxz ="-350,-50";
		}else {
			this.czpyxz ="-350,-100";
		}
		
	}

	public void setCzpypd() {

		/*if (this.czpy == null || this.czpyxz == null) {
			return;
		}

		String[] xz = this.getCzpyxz().split(",");

		if (Float.parseFloat(xz[0].trim()) <= this.getCzpy() && Float.parseFloat(xz[1].trim()) >= this.getCzpy()) {
			this.czpypd = CheckDataManager.PDJG_HG;
		} else {
			this.czpypd = CheckDataManager.PDJG_BHG;
		}*/
		
		if (this.czpc == null || this.czpyxz == null) {
			return;
		}

		String[] xz = this.getCzpyxz().split(",");
		
		Integer xz1=Integer.parseInt(xz[0].trim());
		Integer xz2 = Integer.parseInt(xz[1].trim());
		
		String czpcNew=czpc.trim();
		if(czpcNew.indexOf("+")==0) {
			czpcNew=czpcNew.substring(1);
		}
		Integer numCzpc = Integer.parseInt(czpcNew);

		if (xz1 <= numCzpc && xz2 >= numCzpc) {
			this.czpypd = Constants.PDJG_HG;
		} else {
			this.czpypd = Constants.PDJG_BHG;
		}
	}

	/**
	 * 总判定结果
	 */
	@Override
	public void setZpd() {
		if (gqpd == PDJG_BHG||czpypd==PDJG_BHG) {
			this.setZpd(PDJG_BHG);
		} else {
			this.setZpd(PDJG_HG);
		}
	}
}
