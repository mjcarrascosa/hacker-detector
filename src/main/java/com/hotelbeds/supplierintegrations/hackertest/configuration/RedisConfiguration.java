package com.hotelbeds.supplierintegrations.hackertest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

	@Bean
	JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	RedisTemplate<String, Integer> redisTemplate(JedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
}
