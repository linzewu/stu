package com.xs.jt.srms.config;

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
public class SRMSConfig {
	
	@Bean(name = "srmsDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.srms")
	public DataSource cmsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "srmsJdbcTemplate")
	public JdbcTemplate cmsJdbcTemplate(@Qualifier("srmsDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	

}
