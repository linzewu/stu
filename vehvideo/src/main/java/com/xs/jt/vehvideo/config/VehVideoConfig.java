package com.xs.jt.vehvideo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class VehVideoConfig {
	
	
	@Bean(name = "vehVideoDataSource")
	//默认数据源
	@Primary
	//将properties中以mysql为前缀的参数值，写入方法返回的对象中
	@ConfigurationProperties(prefix="spring.datasource.vehvideo")
	public DataSource mysqDataSource() {
		//通过DataSourceBuilder构建数据源
		DataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
		return ds;
	}

	
	@Primary
	@Bean(name = "vehVideoJdbcTemplate")
	public JdbcTemplate vehJdbcTemplate(@Qualifier("vehVideoDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
  
  
  }
