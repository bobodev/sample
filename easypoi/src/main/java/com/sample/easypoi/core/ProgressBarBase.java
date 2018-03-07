package com.sample.easypoi.core;

public class ProgressBarBase {
    private static long totalTime = 0;
    private static long remainTime = -1;
    private static long startTime = 0;
    private static long endTime = 0;
    private static long startCount = 0;
    private static long endCount = 0;

    private static volatile long count = 0;
    private static long total = 0;

    public static void setTotal(long total) {
        ProgressBarBase.total = total;
    }

    public static synchronized void addCount() {
        count++;
    }

    public static synchronized void addCount(long size) {
        count += size;
    }

    public static synchronized long getCount() {
        return count;
    }

    public static synchronized long getPercent() {
        if (total == 0) {
            return 0;
        }
        return (long) (((float) count / total) * 100);
    }

    public static synchronized boolean isFinish() {
        if (total == 0) {
            return false;
        }
        return count / total == 1 ? true : false;
    }

    public static long getTotalTime() {
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

    public static long getRemainTime() {
        if (totalTime == 0) {
            getTotalTime();
        }
        if (totalTime > 0) {
            remainTime = totalTime - totalTime / total * count;
        }
        return remainTime;
    }
}
