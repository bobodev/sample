package com.sample.easypoi.config.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by sunguangzhu on 2017/9/5.
 */
@Configuration
@EnableConfigurationProperties({DefaultRedisProperties.class})
public class RedisConfig{


    @Autowired
    private DefaultRedisProperties defaultRedisProperties;

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(defaultRedisProperties.getRedisHost());
        jedisConnectionFactory.setPort(defaultRedisProperties.getRedisPort());
        jedisConnectionFactory.setPassword(defaultRedisProperties.getRedisPassword());
        jedisConnectionFactory.setDatabase(defaultRedisProperties.getDatabase());
        jedisConnectionFactory.setClientName(defaultRedisProperties.getClientName());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }



}
