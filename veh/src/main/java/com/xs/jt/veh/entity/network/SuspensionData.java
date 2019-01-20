package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 悬仪架
 * @author linzewu
 *
 */
@Scope("prototype")
@Component("suspensionData")
@Entity
@Table(name = "TM_SuspensionData")
public class SuspensionData extends BaseDeviceData {

	@Override
	public void setZpd() {
		// TODO Auto-generated method stub

	}
	//左静态
	@Column
	private String zjt;
    //右静态
	@Column
	private String yjt;
	//左动态
	@Column
	private String zdt;
	//右动态
	@Column
	private String ydt;
	//左吸收率
	@Column
	private String zxsl;
	//右吸收率
	@Column
	private String yxsl;
	public String getZjt() {
		return zjt;
	}
	public String getYjt() {
		return yjt;
	}
	public String getZdt() {
		return zdt;
	}
	public String getYdt() {
		return ydt;
	}
	public String getZxsl() {
		return zxsl;
	}
	public String getYxsl() {
		return yxsl;
	}
	public void setZjt(String zjt) {
		this.zjt = zjt;
	}
	public void setYjt(String yjt) {
		this.yjt = yjt;
	}
	public void setZdt(String zdt) {
		this.zdt = zdt;
	}
	public void setYdt(String ydt) {
		this.ydt = ydt;
	}
	public void setZxsl(String zxsl) {
		this.zxsl = zxsl;
	}
	public void setYxsl(String yxsl) {
		this.yxsl = yxsl;
	}
	
	

}
