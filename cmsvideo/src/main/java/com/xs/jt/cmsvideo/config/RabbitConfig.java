package com.xs.jt.cmsvideo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {
	
	public static final String QUEUE_VIDEO_DOWNLOAD="videoDownLoad";
	
	public static final String QUEUE_VIDEO_UPLOAD="videoUpload";
	
	@Value("${spring.rabbitmq.host}")
	String address;
	@Value("${spring.rabbitmq.username}")
	String username;
	@Value("${spring.rabbitmq.password}")
	String password;
	@Value("${spring.rabbitmq.port}")
	Integer port;

	//@Bean
    public Queue videoUpLoadQueue(){
        return new Queue(QUEUE_VIDEO_UPLOAD);
    }
	
	//@Bean("videoContainerFactory")
	public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		//每个消费者一次抓取数量
		factory.setPrefetchCount(1);
		//并发数量 最多50个消费者
		factory.setConcurrentConsumers(50);
		configurer.configure(factory, connectionFactory);
		return factory;
	}
	
	//@Bean("simpleMessageListenerContainerMap")
	public Map<String,SimpleMessageListenerContainer> simpleMessageListenerContainerMap(){
		return new HashMap<String,SimpleMessageListenerContainer>();
	}


	// 创建mq连接
	//@Bean(name = "connectionFactory")
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPublisherConfirms(true);
		connectionFactory.setAddresses(address);
		connectionFactory.setPort(port);
		return connectionFactory;

	}
	
	//@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
	    return new RabbitAdmin(connectionFactory);
	}
}
