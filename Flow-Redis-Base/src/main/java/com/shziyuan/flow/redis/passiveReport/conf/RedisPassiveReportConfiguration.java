package com.shziyuan.flow.redis.passiveReport.conf;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import com.shziyuan.flow.redis.base.conf.RedisConnectionConfigurationBuilder;

@Configuration
public class RedisPassiveReportConfiguration {
	public static final String PREFIX = "preport_";
	
	public static final String TEMPLATENAME = PREFIX + "RedisTemplate";
	
	/**
	 * RedisTemplate定义
	 * 使用 键 - String序列化  值 - json序列化
	 * @param redisConnectionFactory
	 * @return
	 * @throws UnknownHostException 
	 */
	@Bean(name = TEMPLATENAME)
    public RedisTemplate<String, Object> redisTemplate() throws UnknownHostException
    {
		RedisConnectionConfigurationBuilder builder = new RedisConnectionConfigurationBuilder(redisProperties());
		
		return builder.buildRedisTemplate();
    }
			
	@Bean(name = PREFIX + "redisProperties")
	@ConfigurationProperties(prefix = "system.redis.preport")
	public RedisProperties redisProperties() {
		return new RedisProperties();
	}

}
