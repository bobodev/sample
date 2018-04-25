package com.sample.scaffold.config.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class DefaultCacheManager extends CachingConfigurerSupport {
    public final static ConcurrentMap<Object, Object> store = new ConcurrentHashMap<>();
    public static Map<String, Long> expires = new ConcurrentHashMap<>();

    static {
        expires.put("scaffold",60l);
        expires.put("user", 5 * 60l);
    }

    @Bean("simpleCacheManager")
    public CacheManager simpleCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<String> cacheNames = new ArrayList<>(expires.keySet());
        List<Cache> caches = new ArrayList<>();
        for (String cacheName : cacheNames) {
            caches.add(new ConcurrentMapCache(cacheName, store, true));
        }
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }

    @Bean("guavaCacheManager")
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> guavaCaches = new ArrayList<>();
        Set<Map.Entry<String, Long>> entries = expires.entrySet();
        for (Map.Entry<String, Long> entry : entries) {
            GuavaCache guavaCache = new GuavaCache(entry.getKey(), CacheBuilder.newBuilder()
                    .maximumSize(100).expireAfterAccess(entry.getValue(), TimeUnit.SECONDS).build());
            guavaCaches.add(guavaCache);
        }
        cacheManager.setCaches(guavaCaches);
        return cacheManager;
    }

    @Bean("redisCacheManager")
    @Primary
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
        List<String> cacheNames = new ArrayList<>(expires.keySet());
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate, cacheNames, true);
        redisCacheManager.setDefaultExpiration(60);
        redisCacheManager.setExpires(expires);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(false);
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
}
