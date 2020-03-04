package com.example.demo.config.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

//@EnableRedisHttpSession
public class HttpSessionConfig {

	@Bean
	public RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Autowired
	private FindByIndexNameSessionRepository sessionRepository;
	
	
	@Bean
	public SpringSessionBackedSessionRegistry sessionRegistry(){
		return new SpringSessionBackedSessionRegistry(sessionRepository);
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher(){
		return new HttpSessionEventPublisher();
	}
	
}
