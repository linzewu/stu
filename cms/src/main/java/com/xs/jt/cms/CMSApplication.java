package com.xs.jt.cms;

import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.aspose.words.License;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@ServletComponentScan({"com.xs.jt.base.module","com.xs.jt.cms"})
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@ComponentScan({"com.xs.jt.base.module","com.xs.jt.cms"})
@EnableSwagger2
@EnableAsync
@EnableScheduling
public class CMSApplication 
{
	
	
    public static void main( String[] args ) throws Exception
    {
    	InputStream license = CMSApplication.class.getClassLoader().getResourceAsStream("license.xml");
    	License aposeLic = new License();
        aposeLic.setLicense(license);
        SpringApplication.run(CMSApplication.class, args);
    }
}
