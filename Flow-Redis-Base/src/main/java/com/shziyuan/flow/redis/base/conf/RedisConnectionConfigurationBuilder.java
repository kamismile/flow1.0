package com.shziyuan.flow.redis.base.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Cluster;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionConfigurationBuilder {

	private final RedisProperties properties;

	private final RedisSentinelConfiguration sentinelConfiguration;

	private final RedisClusterConfiguration clusterConfiguration;

	public RedisConnectionConfigurationBuilder(RedisProperties properties,
			ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
			ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
		this.properties = properties;
		this.sentinelConfiguration = sentinelConfiguration.getIfAvailable();
		this.clusterConfiguration = clusterConfiguration.getIfAvailable();
	}
	
	public RedisConnectionConfigurationBuilder(RedisProperties properties) {
		this.properties = properties;
		this.sentinelConfiguration = null;
		this.clusterConfiguration = null;
	}
	
	public RedisTemplate<String, Object> buildRedisTemplate() throws UnknownHostException {
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(Include.NON_NULL);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        
        JedisConnectionFactory fac = redisConnectionFactory();
        fac.afterPropertiesSet();
        template.setConnectionFactory(fac);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        
        return template;
	}

	public JedisConnectionFactory redisConnectionFactory()
			throws UnknownHostException {
		return applyProperties(createJedisConnectionFactory());
	}

	protected final JedisConnectionFactory applyProperties(
			JedisConnectionFactory factory) {
		configureConnection(factory);
		if (this.properties.isSsl()) {
			factory.setUseSsl(true);
		}
		factory.setDatabase(this.properties.getDatabase());
		if (this.properties.getTimeout() > 0) {
			factory.setTimeout(this.properties.getTimeout());
		}
		return factory;
	}

	private void configureConnection(JedisConnectionFactory factory) {
		if (StringUtils.hasText(this.properties.getUrl())) {
			configureConnectionFromUrl(factory);
		}
		else {
			factory.setHostName(this.properties.getHost());
			factory.setPort(this.properties.getPort());
			if (this.properties.getPassword() != null) {
				factory.setPassword(this.properties.getPassword());
			}
		}
	}

	private void configureConnectionFromUrl(JedisConnectionFactory factory) {
		String url = this.properties.getUrl();
		if (url.startsWith("rediss://")) {
			factory.setUseSsl(true);
		}
		try {
			URI uri = new URI(url);
			factory.setHostName(uri.getHost());
			factory.setPort(uri.getPort());
			if (uri.getUserInfo() != null) {
				String password = uri.getUserInfo();
				int index = password.lastIndexOf(":");
				if (index >= 0) {
					password = password.substring(index + 1);
				}
				factory.setPassword(password);
			}
		}
		catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Malformed 'spring.redis.url' " + url,
					ex);
		}
	}

	protected final RedisSentinelConfiguration getSentinelConfig() {
		if (this.sentinelConfiguration != null) {
			return this.sentinelConfiguration;
		}
		Sentinel sentinelProperties = this.properties.getSentinel();
		if (sentinelProperties != null) {
			RedisSentinelConfiguration config = new RedisSentinelConfiguration();
			config.master(sentinelProperties.getMaster());
			config.setSentinels(createSentinels(sentinelProperties));
			return config;
		}
		return null;
	}

	/**
	 * Create a {@link RedisClusterConfiguration} if necessary.
	 * @return {@literal null} if no cluster settings are set.
	 */
	protected final RedisClusterConfiguration getClusterConfiguration() {
		if (this.clusterConfiguration != null) {
			return this.clusterConfiguration;
		}
		if (this.properties.getCluster() == null) {
			return null;
		}
		Cluster clusterProperties = this.properties.getCluster();
		RedisClusterConfiguration config = new RedisClusterConfiguration(
				clusterProperties.getNodes());

		if (clusterProperties.getMaxRedirects() != null) {
			config.setMaxRedirects(clusterProperties.getMaxRedirects());
		}
		return config;
	}

	private List<RedisNode> createSentinels(Sentinel sentinel) {
		List<RedisNode> nodes = new ArrayList<RedisNode>();
		for (String node : StringUtils
				.commaDelimitedListToStringArray(sentinel.getNodes())) {
			try {
				String[] parts = StringUtils.split(node, ":");
				Assert.state(parts.length == 2, "Must be defined as 'host:port'");
				nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
			}
			catch (RuntimeException ex) {
				throw new IllegalStateException(
						"Invalid redis sentinel " + "property '" + node + "'", ex);
			}
		}
		return nodes;
	}

	private JedisConnectionFactory createJedisConnectionFactory() {
		JedisPoolConfig poolConfig = this.properties.getPool() != null
				? jedisPoolConfig() : new JedisPoolConfig();

		if (getSentinelConfig() != null) {
			return new JedisConnectionFactory(getSentinelConfig(), poolConfig);
		}
		if (getClusterConfiguration() != null) {
			return new JedisConnectionFactory(getClusterConfiguration(), poolConfig);
		}
		return new JedisConnectionFactory(poolConfig);
	}

	private JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		RedisProperties.Pool props = this.properties.getPool();
		config.setMaxTotal(props.getMaxActive());
		config.setMaxIdle(props.getMaxIdle());
		config.setMinIdle(props.getMinIdle());
		config.setMaxWaitMillis(props.getMaxWait());
		return config;
	}

}
