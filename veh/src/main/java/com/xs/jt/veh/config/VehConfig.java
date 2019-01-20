package com.xs.jt.veh.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VehConfig {
	
	@Bean(name = "vehDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.veh")
	public DataSource cmsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "vehJdbcTemplate")
	public JdbcTemplate cmsJdbcTemplate(@Qualifier("vehDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	

}
