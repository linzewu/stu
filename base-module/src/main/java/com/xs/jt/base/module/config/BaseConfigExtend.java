package com.xs.jt.base.module.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="base.config.extend")
public class BaseConfigExtend {
	
	private String excludeList;

	public String getExcludeList() {
		return excludeList;
	}

	public void setExcludeList(String excludeList) {
		this.excludeList = excludeList;
	}
	
	

}
