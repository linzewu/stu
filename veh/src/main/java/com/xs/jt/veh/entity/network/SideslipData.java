package com.xs.jt.veh.entity.network;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.veh.entity.VehCheckLogin;


@Scope("prototype")
@Component("sideslipData")
@Entity
@Table(name = "TM_SideslipData")
public class SideslipData extends BaseDeviceData {
	
	@Column
	private Float sideslip;
	
	@Transient
	private List<Float> datas;
	
	@Column
	private Integer chpd;
	
	@Column
	private String chxz;
	
	@Column(length=4000)
	private String strData;
	
	

	public Integer getChpd() {
		return chpd;
	}


	public void setChpd(Integer chpd) {
		this.chpd = chpd;
	}

	
	
	public String getChxz() {
		return chxz;
	}


	public void setChxz(String chxz) {
		this.chxz = chxz;
	}


	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}
	
	public void setStrData() {
		
		if(datas!=null&&!datas.isEmpty()){
			StringBuilder sb=new StringBuilder();
			for(Float f:datas){
				sb.append(f);
				sb.append(",");
			}
			
			this.strData = sb.substring(0,sb.length()-1);
			
		}
	}

	public Float getSideslip() {
		return sideslip;
	}

	public List<Float> getDatas() {
		return datas;
	}

	public void setSideslip(Float sideslip) {
		this.sideslip = sideslip;
	}

	public void setDatas(List<Float> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "SideslipData [sideslip=" + sideslip + ", datas=" + datas + "]";
	}
	
	public void setChpd(VehCheckLogin vehCheckLogin) {
		
		if(vehCheckLogin.getZxzxjxs().equals("0")){
			this.chpd = PDJG_WJ;
		}else{
			if (sideslip <= 5 && sideslip >= -5) {
				this.chpd = PDJG_HG;
			} else {
				this.chpd = PDJG_BHG;
			}
		}
		
		
	}
	
	public void setChxz() {
		this.chxz="-5,5";
	}


	@Override
	public void setZpd() {
		this.setZpd(chpd);
	}
	
	

}
