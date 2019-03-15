package com.xs.jt.cmsvideo;

import javax.xml.ws.Endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.xs.jt.cmsvideo.webservice.VideoWebServiceImpl;

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
    	
    	//定义webService的发布地址，提供给外界使用接口的地址
    	String address = "http://127.0.0.1:8083/cmsvideo/vwService";
        //使用Endpoint类提供的publish方法发布WebService，发布时要保证使用的端口号没有被其他应用程序占用
        Endpoint.publish(address , new VideoWebServiceImpl());
        System.out.println("发布webservice成功!");
    }
}
