package com.xs.jt.vehvideo;

import java.io.IOException;

import org.apache.axis2.transport.http.AxisServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@ServletComponentScan({"com.xs.jt.base.module","com.xs.jt.vehvideo"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@ComponentScan({"com.xs.jt.base.module","com.xs.jt.vehvideo"})
@EnableSwagger2
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class VehVideoApplication 
{
	protected static Log log = LogFactory.getLog(VehVideoApplication.class);
    public static void main( String[] args )
    {
    	SpringApplication.run(VehVideoApplication.class, args);
    }
  
}
