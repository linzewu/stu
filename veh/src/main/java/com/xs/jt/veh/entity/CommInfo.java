package com.xs.jt.veh.entity;

import com.xs.jt.base.module.entity.BaseEntity;

public class CommInfo extends BaseEntity {

	private String comm;

	private int rate;

	private int databits;

	private int stopbits;

	private int parity;

	public String getComm() {
		return comm;
	}

	public int getRate() {
		return rate;
	}

	public int getDatabits() {
		return databits;
	}

	public int getStopbits() {
		return stopbits;
	}

	public int getParity() {
		return parity;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public void setDatabits(int databits) {
		this.databits = databits;
	}

	public void setStopbits(int stopbits) {
		this.stopbits = stopbits;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

}
