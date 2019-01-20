package com.xs.jt.veh.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("curbWeightData")
@Entity
@Table(name = "TM_CurbWeightData")
public class CurbWeightData  extends BaseDeviceData {
	
	@Column
	private Integer zbzl;
	
	@Column
	private Integer zbzlpd;
	

	@Override
	public void setZpd() {
		this.setZpd(this.getZbzlpd());
	}

	

	public Integer getZbzl() {
		return zbzl;
	}



	public Integer getZbzlpd() {
		return zbzlpd;
	}



	public void setZbzl(Integer zbzl) {
		this.zbzl = zbzl;
		setZpd();
	}



	public void setZbzlpd(Integer zbzlpd) {
		this.zbzlpd = zbzlpd;
	}
	
	

}
