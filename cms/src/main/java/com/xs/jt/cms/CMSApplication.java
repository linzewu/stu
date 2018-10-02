package com.xs.jt.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@ServletComponentScan({"com.xs.jt.base.module","com.xs.jt.cms"})
@EnableAutoConfiguration
@ComponentScan({"com.xs.jt.base.module","com.xs.jt.cms"})
@EnableSwagger2
public class CMSApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(CMSApplication.class, args);
    }
}
