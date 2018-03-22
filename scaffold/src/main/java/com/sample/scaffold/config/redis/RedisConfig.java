package com.sample.scaffold.config.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by sunguangzhu on 2017/9/5.
 */
@Configuration
@EnableConfigurationProperties({DefaultRedisProperties.class})
public class RedisConfig extends CachingConfigurerSupport {

    public final static ConcurrentMap<Object, Object> store = new ConcurrentHashMap<>();

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

    @Bean("redisCacheManager")
    @Primary
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
        List<String> cacheNames = new ArrayList<>(Arrays.asList("scaffold", "user"));
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate, cacheNames, true);
        //配置缓存的过期时间
        Map<String, Long> expires = new ConcurrentHashMap<>();
        expires.put("scaffold", 5 * 60l);
        expires.put("user", 5 * 60l);

        redisCacheManager.setDefaultExpiration(60);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setExpires(expires);
        return redisCacheManager;
    }

    /**
     * key值为className+methodName+参数值列表
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, args) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append("#");
            sb.append(method.getName()).append("(");
            for (Object obj : args) {
                sb.append(obj.toString()).append(",");
            }
            sb.append(")");
            return sb.toString();
        };
    }

    @Bean("simpleCacheManager")
    public SimpleCacheManager simpleCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<String> cacheNames = new ArrayList<>(Arrays.asList("scaffold", "user"));
        List<Cache> caches = new ArrayList<>();
        for (String cacheName : cacheNames) {
            caches.add(new ConcurrentMapCache(cacheName, store,true));
        }
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }


}
