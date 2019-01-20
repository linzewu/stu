package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("outline")
@Entity
@Table(name = "TM_Outline")
public class Outline extends BaseDeviceData {
	
	public static final String STR_JYXM="外廓尺寸测量";
	
	public static final Integer FLAG_Y=1;
	
	@Column
	private String jyjgbh;
	
	@Column(length=5)
	private String jcxdh;
	
	@Column
	private Integer cwkc;
	@Column
	private Integer cwkk;
	@Column
	private Integer cwkg;
	@Column
	private Integer clwkccpd;
	
	@Column
	private Integer reportFlag;
	
	
	
	public Integer getReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(Integer reportFlag) {
		this.reportFlag = reportFlag;
	}
	public String getJyjgbh() {
		return jyjgbh;
	}
	public String getJcxdh() {
		return jcxdh;
	}
	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}
	public void setJcxdh(String jcxdh) {
		this.jcxdh = jcxdh;
	}
	public Integer getCwkc() {
		return cwkc;
	}
	public Integer getCwkk() {
		return cwkk;
	}
	public Integer getCwkg() {
		return cwkg;
	}
	public Integer getClwkccpd() {
		return clwkccpd;
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
	public void setClwkccpd(Integer clwkccpd) {
		this.clwkccpd = clwkccpd;
	}
	@Override
	public void setZpd() {
		
	}
	
	

}
