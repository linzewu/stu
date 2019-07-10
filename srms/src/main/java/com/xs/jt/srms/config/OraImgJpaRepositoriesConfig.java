package com.xs.jt.srms.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryImgORA", 
		transactionManagerRef = "transactionManagerImgORA", 
		basePackages = { "com.xs.jt.srms.vehimg.dao" })
@ConfigurationProperties(prefix = "spring.datasource.ora.image")
public class OraImgJpaRepositoriesConfig {
	
	private Map<String, String> properties = new HashMap<String, String>() ;
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	@Resource
	@Qualifier("imgOraDataSource")
	private DataSource imgOraDataSource;

	@Bean(name = "entityManagerImgORA")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryImgORA(builder).getObject().createEntityManager();
	}

	@Resource
	private JpaProperties jpaProperties;

	private Map<String, Object> getVendorProperties() {
		this.jpaProperties.setProperties(properties);
		 return jpaProperties.getHibernateProperties(new HibernateSettings());
	}

	/** * 设置实体类所在位置 */
	@Bean(name = "entityManagerFactoryImgORA")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryImgORA(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(imgOraDataSource)
				.packages(new String[] { "com.xs.jt.srms.vehimg.entity" })
				.persistenceUnit("primaryPersistenceUnit").properties(getVendorProperties()).build();
	}

	@Bean(name = "transactionManagerImgORA")
	public PlatformTransactionManager transactionManagerImgORA(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactoryImgORA(builder).getObject());
	}
}
