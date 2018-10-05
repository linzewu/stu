package com.xs.jt.base.module.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.xs.jt")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("交通基础平台API").build();
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","file:"+cacheDir);
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}
	
	@Autowired
	private UserInterceptor userInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("/user/login");
		excludeList.add("/static/**");
		registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
	}
	
	@Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
    	return new OpenEntityManagerInViewFilter();
    }


}
