package com.xs.jt.hwvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@ServletComponentScan({"com.xs.jt.base.module","com.xs.jt.hwvideo"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@ComponentScan({"com.xs.jt.base.module","com.xs.jt.hwvideo"})
@EnableSwagger2
@EnableAsync
@EnableScheduling
public class HWVideoApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(HWVideoApplication.class, args);
    }
}
