package com.sample.interceptor.inspector;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sample.interceptor.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jonathan on 2018/1/18.
 */
@Component
public class InspectorPersonCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(InspectorPersonCache.class);

    private Cache<Integer, String> cache = CacheBuilder.newBuilder().maximumSize(1024 * 8).expireAfterWrite(Constants.CACHE_TIME, Constants.UNIT).build();


    public String getPersonByCache(Integer personId) throws ExecutionException {
        String result = cache.get(personId, new Callable<String>() {
            @Override
            public String call() throws Exception {
                LOGGER.info("call me when InspectorPersonCache miss");
                /**
                 * 此处编写业务代码，一般是从数据库获取的数据在此处做缓存
                 */
                String value = "测试人员" + personId;
                return value;
            }
        });
        return result;
    }

    public void delCache(Integer personId) {
        cache.invalidate(personId);
    }

}
