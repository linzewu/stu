package com.xs.jt.cmsvideo;

import java.io.IOException;

import org.apache.axis2.transport.http.AxisServlet;
import org.apache.catalina.connector.Connector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.xs.jt.cmsvideo.util.FileCopyUtils;

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
@SpringBootApplication
public class CmsVideoApplication extends SpringBootServletInitializer
{
	protected static Log log = LogFactory.getLog(CmsVideoApplication.class);
    public static void main( String[] args )
    {
    	SpringApplication.run(CmsVideoApplication.class, args);
    }
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CmsVideoApplication.class);
	}
    
    @Bean
	public ServletRegistrationBean helloWorldServlet() {
	    ServletRegistrationBean helloWorldServlet = new ServletRegistrationBean();
	    helloWorldServlet.setServlet(new AxisServlet());//这里的AxisServlet就是web.xml中的org.apache.axis2.transport.http.AxisServlet
	    helloWorldServlet.addUrlMappings("/services/*");
	    //通过默认路径无法找到services.xml，这里需要指定一下路径，且必须是绝对路径
	    String path = this.getClass().getResource("/ServicesPath").getPath().toString();
	    log.info("The original path：" + path);
	    if(path.toLowerCase().startsWith("file:")){
	    	log.info("去掉前面的“file:”！");
	    	path = path.substring(5);
	    }
	    //如果获得到的地址里有感叹号，说明文件在压缩包（jar包）中，Axis2无法正常使用，需要拷贝到jar包外
	    if(path.indexOf("!") != -1){
	    	try {
	    		log.info("将ServicesPath/services/MyWebService/META-INF/services.xml文件拷贝到jar包同级目录下！");
				FileCopyUtils.copy("ServicesPath/services/MyWebService/META-INF/services.xml");
			} catch (IOException e) {
				log.error(e);
			}
	    	log.info("jar包运行！查找jar包同级目录下的“/ServicesPath”目录");
	    	path = path.substring(0, path.lastIndexOf("/", path.indexOf("!"))) + "/ServicesPath";
	    }
	    log.info("The final path：" + path);
	    helloWorldServlet.addInitParameter("axis2.repository.path", path);
	    helloWorldServlet.setLoadOnStartup(1);
	    return helloWorldServlet;
	}
    
	// 下面是2.0的配置，1.x请搜索对应的设置
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
		return tomcat;
	}

	private Connector createHTTPConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		// 同时启用http（8080）、https（8443）两个端口
		connector.setScheme("http");
		connector.setSecure(false);
		connector.setPort(8087);
		connector.setRedirectPort(8082);
		return connector;
	}
  
}
