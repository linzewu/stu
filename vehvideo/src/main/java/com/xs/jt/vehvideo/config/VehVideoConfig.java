package com.xs.jt.vehvideo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VehVideoConfig {
	
	@Bean(name = "vehVideoDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.vehvideo")
	public DataSource vehDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "vehVideoJdbcTemplate")
	public JdbcTemplate vehJdbcTemplate(@Qualifier("vehVideoDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
