package com.xs.jt.cms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CMSConfig {
	
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
	
	@Bean(name = "oraJdbcTemplate")
	public JdbcTemplate oraJdbcTemplate(@Qualifier("oraDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Primary
	@Bean(name = "cmsJdbcTemplate")
	public JdbcTemplate cmsJdbcTemplate(@Qualifier("cmsDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	

}
