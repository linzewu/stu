package com.xs.jt.srms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SRMSConfig {
	
	@Bean(name = "srmsDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.srms")
	public DataSource srmsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "srmsJdbcTemplate")
	public JdbcTemplate cmsJdbcTemplate(@Qualifier("srmsDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
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
	
	@Bean(name = "imgOraDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.ora.image")
	public DataSource oraImageDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "imgOraJdbcTemplate")
	public JdbcTemplate oraImageJdbcTemplate(@Qualifier("imgOraDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	

}
