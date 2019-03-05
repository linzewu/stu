package com.xs.jt.cmsvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@ServletComponentScan({"com.xs.jt.base.module","com.xs.jt.cmsvideo"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@ComponentScan({"com.xs.jt.base.module","com.xs.jt.cmsvideo"})
@EnableSwagger2
@EnableAsync
@EnableScheduling
public class CmsVideoApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(CmsVideoApplication.class, args);
    }
}
