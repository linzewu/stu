package com.xs.jt.hwvideo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xs.jt.hwvideo.manager.SimpleRead;
import com.xs.jt.hwvideo.manager.WeightDecodeAbstract;
import com.xs.jt.hwvideo.util.WeightContainer;

@Configuration
public class Config {
	
	protected static Log logger = LogFactory.getLog(Config.class);
	
	private WeightContainer weightContainer;
	
	@Value("${com.xs.hw.com}")
	private String com;
	
	@Value("${com.xs.hw.rate}")
	private Integer rate;
	
	@Value("${com.xs.hw.databits}")
	private Integer databits;
	
	@Value("${com.xs.hw.stopbits}")
	private Integer stopbits;
	
	@Value("${com.xs.hw.parity}")
	private Integer parity;
	
	@Value("${com.xs.hw.wdn}")
	private String weightDecodeName;
	
	@Bean("currentInDatas")
	public JSONArray currentInDatas(){
		return new JSONArray();
	}
	
	@Bean("currentOutDatas")
	public JSONArray currentOutDatas(){
		return new JSONArray();
	}
	
	@Bean("inVehMap")
	public Map<String,JSONObject> inVehMap(){
		return new HashMap();
	}



	@Bean(name = "hwvideoDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hwvideo")
	public DataSource hwvideoDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "hwvideoJdbcTemplate")
	public JdbcTemplate hwvideoJdbcTemplate(@Qualifier("hwvideoDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(15000);
		factory.setReadTimeout(5000);
		return factory;
	}
	
	@Bean("simpleRead")
	public SimpleRead simpleRead() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		SimpleRead sm = new SimpleRead();
		sm.setCom(com);
		sm.setDatabits(databits);
		sm.setParity(parity);
		sm.setRate(rate);
		sm.setStopbits(stopbits);
		
		WeightDecodeAbstract weightDecode = (WeightDecodeAbstract) Class.forName("com.xs.jt.hwvideo.manager.decode."+weightDecodeName).newInstance();
		
		sm.setWeightDecode(weightDecode);
		
		try {
			sm.open();
		}catch (Exception e) {
			logger.error("打开串口失败");
		}
		return sm;
	}
	
	@Bean("weight")
	public WeightContainer weight() {
		weightContainer =new WeightContainer();
		weightContainer.setWeight(0f);
		return weightContainer;
	}

}
