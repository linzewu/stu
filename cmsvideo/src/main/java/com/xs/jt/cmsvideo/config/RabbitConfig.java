package com.xs.jt.cmsvideo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	public static final String QUEUE_VIDEO_DOWNLOAD="videoDownLoad";
	
	public static final String QUEUE_VIDEO_UPLOAD="videoUpload";

	@Bean
    public Queue videoDownLoadQueue(){
        return new Queue(QUEUE_VIDEO_DOWNLOAD);
    }

	@Bean
    public Queue videoUpLoadQueue(){
        return new Queue(QUEUE_VIDEO_UPLOAD);
    }
	
	@Bean("videoContainerFactory")
	public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		//每个消费者一次抓取数量
		factory.setPrefetchCount(1);
		//并发数量 最多50个消费者
		factory.setConcurrentConsumers(50);
		configurer.configure(factory, connectionFactory);
		return factory;
	}
}
