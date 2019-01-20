package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("speedData")
@Entity
@Table(name = "TM_SpeedData")
public class SpeedData extends BaseDeviceData {

	@Column
	private Float speed;

	@Column
	private Integer sdpd;

	@Column
	private String sdxz;
	
	

	public Integer getSdpd() {
		return sdpd;
	}

	public String getSdxz() {
		return sdxz;
	}

	public void setSdpd(Integer sdpd) {
		this.sdpd = sdpd;
	}

	public void setSdxz(String sdxz) {
		this.sdxz = sdxz;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	public void setSdpd() {

		if (sdxz == null || "".equals(sdxz)) {
			return;
		}
		String[] temp = sdxz.split(",");
		Float min = Float.valueOf(temp[0]);
		Float max = Float.valueOf(temp[1]);
		
		if(this.speed>=min&&this.speed<=max){
			this.sdpd=PDJG_HG;
		}else{
			this.sdpd=PDJG_BHG;
		}

	}

	public void setSdxz() {
		this.sdxz = "32.8,40";
	}

	@Override
	public void setZpd() {
		this.setZpd(sdpd);
		
	}

}
