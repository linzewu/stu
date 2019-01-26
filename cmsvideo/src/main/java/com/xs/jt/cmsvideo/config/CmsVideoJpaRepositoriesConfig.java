package com.xs.jt.cmsvideo.config;

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
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryCmsVideo", 
		transactionManagerRef = "transactionManagerCmsVideo", 
		basePackages = {"com.xs.jt.base.module.dao", "com.xs.jt.cmsvideo.dao" })
@ConfigurationProperties(prefix = "spring.jpa.cmsvideo")
public class CmsVideoJpaRepositoriesConfig {
	
	private Map<String, String> properties = new HashMap<String, String>() ;
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	@Resource
	@Qualifier("cmsVideoDataSource")
	private DataSource vehDataSource;

	@Primary
	@Bean(name = "entityManagerCmsVideo")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
	}

	@Resource
	private JpaProperties jpaProperties;

	private Map<String, Object> getVendorProperties() {
		this.jpaProperties.setProperties(properties);
		 return jpaProperties.getHibernateProperties(new HibernateSettings());
	}

	/** * 设置实体类所在位置 */
	@Primary
	@Bean(name = "entityManagerFactoryCmsVideo")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(vehDataSource)
				.packages(new String[] { "com.xs.jt.base.module.entity", "com.xs.jt.cmsvideo.entity" })
				.persistenceUnit("primaryPersistenceUnit").properties(getVendorProperties()).build();
	}

	@Primary
	@Bean(name = "transactionManagerCmsVideo")
	public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
	}
}
