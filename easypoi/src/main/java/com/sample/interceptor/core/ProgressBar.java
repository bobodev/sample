package com.sample.interceptor.core;

import java.util.concurrent.ConcurrentHashMap;

public class ProgressBar {
    private static ConcurrentHashMap<String, ProgressBar> progressBarMap = new ConcurrentHashMap<>();
    private long totalTime = 0;
    private long remainTime = -1;
    private long startTime = 0;
    private long endTime = 0;
    private long startCount = 0;
    private long endCount = 0;

    private volatile long count = 0;
    private long total = 0;

    public void setTotal(long total) {
        this.total = total;
    }

    public synchronized void addCount() {
        count++;
    }

    public synchronized void addCount(long size) {
        count += size;
    }

    public synchronized long getCount() {
        return count;
    }

    public synchronized long getPercent() {
        if (total == 0) {
            return 0;
        }
        return (long) (((float) count / total) * 100);
    }

    public synchronized boolean isFinish() {
        if (total == 0) {
            return false;
        }
        return count / total == 1 ? true : false;
    }

    public long getTotalTime() {
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
        return totalTime;
    }

    public long getRemainTime() {
        if (totalTime == 0) {
            getTotalTime();
        }
        if (totalTime > 0) {
            remainTime = totalTime - totalTime / total * count;
        }
        return remainTime;
    }

    public static ProgressBar getProgressBarByCode(String code){
        return progressBarMap.get(code);
    }
    public static ProgressBar createProgressBarByCode(String code){
        if(progressBarMap.get(code)==null){
            progressBarMap.put(code,new ProgressBar());
        }
        return progressBarMap.get(code);
    }
}
