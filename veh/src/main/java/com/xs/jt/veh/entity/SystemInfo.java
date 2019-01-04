package com.xs.jt.veh.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("systemInfo")
public class SystemInfo {
	
	@Value("${jkxlh}")
	private String jkxlh;
	
	@Value("${jyjgbh}")
	private String jyjgbh;
	
	@Value("${ip}")
	private String jgxtip;
	
	@Value("${port}")
	private String jgxtdk;
	
	@Value("${db.url}")
	private String dbInfo;
	
	@Value("${isNetwork}")
	private String isNetwork;
	
	@Value("${plusLoadFlag}")
	private String plusLoadFlag;
	
	@Value("${czpypdFlag}")
	private String czpypdFlag;
	
	@Value("${lic.startData}")
	private String startData;
	
	@Value("${lic.endData}")
	private String endData;
	
	@Value("${jyjgmc}")
	private String jyjgmc;
	
	

	public String getJyjgmc() {
		return jyjgmc;
	}

	public void setJyjgmc(String jyjgmc) {
		this.jyjgmc = jyjgmc;
	}

	public String getStartData() {
		return startData;
	}

	public String getEndData() {
		return endData;
	}

	public void setStartData(String startData) {
		this.startData = startData;
	}

	public void setEndData(String endData) {
		this.endData = endData;
	}

	public String getIsNetwork() {
		return isNetwork;
	}

	public String getPlusLoadFlag() {
		return plusLoadFlag;
	}

	public String getCzpypdFlag() {
		return czpypdFlag;
	}

	public void setIsNetwork(String isNetwork) {
		this.isNetwork = isNetwork;
	}

	public void setPlusLoadFlag(String plusLoadFlag) {
		this.plusLoadFlag = plusLoadFlag;
	}

	public void setCzpypdFlag(String czpypdFlag) {
		this.czpypdFlag = czpypdFlag;
	}

	public String getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(String dbInfo) {
		this.dbInfo = dbInfo;
	}

	public String getJkxlh() {
		return jkxlh;
	}

	public String getJyjgbh() {
		return jyjgbh;
	}

	public String getJgxtip() {
		return jgxtip;
	}

	public String getJgxtdk() {
		return jgxtdk;
	}


	public void setJkxlh(String jkxlh) {
		this.jkxlh = jkxlh;
	}

	public void setJyjgbh(String jyjgbh) {
		this.jyjgbh = jyjgbh;
	}

	public void setJgxtip(String jgxtip) {
		this.jgxtip = jgxtip;
	}

	public void setJgxtdk(String jgxtdk) {
		this.jgxtdk = jgxtdk;
	}

}
