package com.sample.scaffold.service.schedule;

import com.alibaba.fastjson.JSON;
import com.sample.scaffold.core.lock.annotation.RedisLockAnno;
import com.sample.scaffold.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ScheduleFactory {
    private static final String SYNC_TEST_REDIS_LOCK_KEY = "SYNC_TEST_REDIS_LOCK_KEY";
    private static final int SYNC_TEST_REDIS_LOCK_KEY_EXPIRED = 60 * 1000;//60s

    private static final String SYNC_TEST02_REDIS_LOCK_KEY = "SYNC_TEST_REDIS_LOCK_KEY";
    private static final int SYNC_TEST02_REDIS_LOCK_KEY_EXPIRED = 60 * 1000;//60s

    @Autowired
    @Qualifier("redisCacheManager")
    private CacheManager redisCacheManager;

    @Scheduled(fixedDelay = 10 * 1000)
    @RedisLockAnno(key = SYNC_TEST_REDIS_LOCK_KEY, expired = SYNC_TEST_REDIS_LOCK_KEY_EXPIRED, execKey = "syncTest")
    public void syncTest() throws Exception {
        Thread.sleep(5000);
    }


    @Scheduled(fixedDelay = 5 * 1000)
    @RedisLockAnno(key = SYNC_TEST02_REDIS_LOCK_KEY, expired = SYNC_TEST02_REDIS_LOCK_KEY_EXPIRED, execKey = "syncTest02")
    public void syncTest02() throws Exception {
        Collection<String> cacheNames = redisCacheManager.getCacheNames();
        System.out.println("JSON.toJSONString(cacheNames) = " + JSON.toJSONString(cacheNames));
        Cache cache = redisCacheManager.getCache("scaffold");

        User user = cache.get("6", User.class);
        System.out.println("user = " + user);
        if(user!=null){
           cache.evict("6");
            user = cache.get("6", User.class);
            System.out.println("after user = " + user);
        }
    }

}
