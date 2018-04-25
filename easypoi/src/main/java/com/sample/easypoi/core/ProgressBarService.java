package com.sample.easypoi.core;

import com.alibaba.fastjson.JSON;
import com.sample.easypoi.config.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressBarService {

    @Autowired
    private RedisService redisService;

    public void setTotal(String code,long total) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        progressBar.setTotal(total);
        this.setProgressBarToRedis(code,progressBar);
    }

    public synchronized void addCount(String code) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        progressBar.setCount(progressBar.getCount()+1);
        this.setProgressBarToRedis(code,progressBar);
    }

    public synchronized void addCount(String code,long size) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        progressBar.setCount(progressBar.getCount()+size);
        this.setProgressBarToRedis(code,progressBar);

    }

    public synchronized long getCount(String code) throws Exception{
        ProgressBar progressBar = this.getProgressBarByCode(code);
        return progressBar.getCount();
    }

    public synchronized long getPercent(String code) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        if (progressBar.getTotal() == 0) {
            return 0;
        }
        return (long) (((float) progressBar.getCount() / progressBar.getTotal()) * 100);
    }

    public synchronized boolean isFinish(String code) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        if (progressBar.getTotal() == 0) {
            return false;
        }
        return progressBar.getCount()/ progressBar.getTotal() == 1 ? true : false;
    }

    public long getTotalTime(String code) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        long totalTime = progressBar.getTotalTime();
        long startTime = progressBar.getStartTime();
        long count = progressBar.getCount();
        long startCount = progressBar.getStartCount();
        long endTime = progressBar.getEndTime();
        long endCount = progressBar.getEndCount();
        long total = progressBar.getTotal();


        if (totalTime > 0) {
            return totalTime;
        } else {
            if (startTime == 0 && count > 0) {
                startTime = System.currentTimeMillis();
                startCount = count;
            }
            if (startTime > 0 && count > 0 && endTime == 0) {
                endTime = System.currentTimeMillis();
                endCount = count;
                if (endCount - startCount == 0 || endTime - startTime == 0) {
                    endTime = 0;
                    endCount = 0;
                } else {
                    //计算总时间
                    long minusTime = endTime - startTime;
                    long minusCount = endCount - startCount;
                    long minusRate = minusTime / minusCount;
                    if (minusRate == 0) {
                        endTime = 0;
                        endCount = 0;
                    } else {
                        totalTime = minusRate * total;
                    }
                }
            }
        }

        progressBar.setTotalTime(totalTime);
        progressBar.setStartTime(startTime);
        progressBar.setCount(count);
        progressBar.setStartCount(startCount);
        progressBar.setEndTime(endTime);
        progressBar.setEndCount(endCount);
        progressBar.setTotal(total);
        this.setProgressBarToRedis(code,progressBar);
        return totalTime;
    }

    public long getRemainTime(String code) throws Exception {
        ProgressBar progressBar = this.getProgressBarByCode(code);
        long totalTime = progressBar.getTotalTime();
        long remainTime = progressBar.getRemainTime();
        if (totalTime == 0) {
            totalTime=getTotalTime(code);
        }
        if (totalTime > 0) {
            remainTime = totalTime - totalTime / progressBar.getTotal() * progressBar.getCount();
        }
        progressBar.setTotalTime(totalTime);
        progressBar.setRemainTime(remainTime);
        this.setProgressBarToRedis(code,progressBar);
        return remainTime;
    }

    public ProgressBar getProgressBarByCode(String code) throws Exception{
        String s = redisService.get(code);
        if(StringUtils.isEmpty(s)){
            throw new Exception("尚未创建进度条");
        }
        ProgressBar progressBar = JSON.parseObject(s, ProgressBar.class);
        return progressBar;
    }
    public ProgressBar createProgressBarByCode(String code){
        String s = redisService.get(code);
        if(StringUtils.isNotEmpty(s)){
           redisService.del(code);
        }
        ProgressBar progressBar = new ProgressBar();
        this.setProgressBarToRedis(code, progressBar);
        return progressBar;
    }

    private void setProgressBarToRedis(String code, ProgressBar progressBar){
        redisService.set(code,JSON.toJSONString(progressBar));
        redisService.expire(code,60*60*6);
    }
}
