package com.xs.jt.cms.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DBConfig {
	
	@Bean(name = "cmsDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.cms")
	public DataSource cmsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "oraDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.ora")
	public DataSource oraDataSource() {
		return DataSourceBuilder.create().build();
	}

}
