package com.xs.jt.base.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ServletComponentScan
@EnableAutoConfiguration
@ComponentScan("com.xs.jt.base.module")
@EntityScan("com.xs.jt.base.module.entity")
@EnableSwagger2
public class JTBaseApplication 
{
    public static void main( String[] args )
    {
    	 SpringApplication.run(JTBaseApplication.class, args);
    }
    
}
