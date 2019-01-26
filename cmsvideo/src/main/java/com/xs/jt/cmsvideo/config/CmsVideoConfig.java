package com.xs.jt.cmsvideo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CmsVideoConfig {
	
	@Bean(name = "cmsVideoDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.cmsvideo")
	public DataSource cmsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "cmsVideoJdbcTemplate")
	public JdbcTemplate cmsJdbcTemplate(@Qualifier("cmsVideoDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	

}
