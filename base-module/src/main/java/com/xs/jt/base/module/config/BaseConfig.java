package com.xs.jt.base.module.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.manager.impl.SecurityAuditPolicySettingManagerImpl;
import com.xs.jt.base.module.util.SecurityInterceptor;
import com.xs.jt.base.module.util.UserInterceptor;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class BaseConfig extends WebMvcConfigurationSupport  {
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private BaseConfigExtend baseConfigExtend;
	@Autowired
	protected UserInterceptor userInterceptor;
	@Autowired
	protected SecurityInterceptor securityInterceptor;
	
	@Autowired
	private SecurityAuditPolicySettingManagerImpl saps;
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.xs.jt")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("交通基础平台API").build();
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String[] caches = cacheDir.split(",");
		for(String cache:caches) {
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","file:"+cache);
			System.out.println(cache);
		}
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}
	

	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("/user/getZzjUser");
		excludeList.add("/user/login");
		excludeList.add("/static/**");
		excludeList.add("/error");
		excludeList.add("/");
		if(!StringUtils.isEmpty(baseConfigExtend.getExcludeList())) {
			String[] excludeLists = baseConfigExtend.getExcludeList().split(",");
			for(String s:excludeLists) {
				excludeList.add(s);
			}
		}
		registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
		registry.addInterceptor(securityInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
	}
	
	@Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
    	return new OpenEntityManagerInViewFilter();
    }
    
    
    @Bean  
    public CommonsMultipartResolver commonsMultipartResolver() {
    	CommonsMultipartResolver factory = new CommonsMultipartResolver();
    	factory.setDefaultEncoding("UTF-8");
    	factory.setMaxInMemorySize(1);
    	factory.setMaxUploadSize(1024*1024*10);
        return factory;  
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		 registry.addViewController("/").setViewName("forward:/static/html/forward.html");
		 
	     registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	 
	     super.addViewControllers(registry);
	}
	
	@Bean("minHF")
	public Map<String,Integer> minHF() {
		HashMap<String,Integer> minHF = new HashMap<String,Integer>();
		return minHF;
	}
	
	@Bean("hourHF")
	public Map<String,Integer> hourHF() {
		 Map<String,Integer> hourHF =new HashMap<String,Integer>();
		return hourHF;
	}
	
	@Bean("dayHF")
	public Map<String,Integer> dayHF() {
		Map<String,Integer> dayHF=new HashMap<String,Integer>();
		return dayHF;
	}
	
	@Bean("hfMap")
	public Map<String,Integer> hfMap() {
		SecurityAuditPolicySetting spas = saps.getPolicyByCode(SecurityAuditPolicySetting.VISIT_NUMBER_ONEDAY);
		Map<String,Integer> hfMap=new HashMap<String,Integer>();
		if(spas != null) {
			hfMap.put("dayHF", Integer.valueOf(spas.getClz()));
		}
		spas = saps.getPolicyByCode(SecurityAuditPolicySetting.VISIT_NUMBER_ONEHOUR);
		if(spas != null) {
			hfMap.put("hourHF", Integer.valueOf(spas.getClz()));
		}
		spas = saps.getPolicyByCode(SecurityAuditPolicySetting.VISIT_NUMBER_ONEMINUTE);
		if(spas != null) {
			hfMap.put("minHF", Integer.valueOf(spas.getClz()));
		}
		return hfMap;
	}


}
