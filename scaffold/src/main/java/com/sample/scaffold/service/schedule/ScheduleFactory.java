package com.sample.scaffold.service.schedule;

import com.sample.scaffold.core.lock.annotation.RedisLockAnno;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleFactory {
    private static final String SYNC_TEST_REDIS_LOCK_KEY = "SYNC_TEST_REDIS_LOCK_KEY";
    private static final int SYNC_TEST_REDIS_LOCK_KEY_EXPIRED = 60 * 1000;//60s

    @Scheduled(fixedDelay = 10 * 1000)
    @RedisLockAnno(key = SYNC_TEST_REDIS_LOCK_KEY, expired = SYNC_TEST_REDIS_LOCK_KEY_EXPIRED, execKey = "syncTest")
    public void syncTest() throws Exception {
        Thread.sleep(5000);
    }

}
