package com.xs.jt.base.module.common;

import java.util.Map;

public class Message {
	
	public final static String STATE_SUCCESS="success";
	
	public final static String STATE_ERROR="error";
	
	private String state;
	
	private String message;
	
	private Map<String,Object> data;

	public String getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	

}
