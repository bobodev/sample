package com.sample.easypoi.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ProcessPower {
    private static final long maxSize = (long) (3.5 * 1024 * 1024);//默认10M
    private volatile static long size = 0;

    /**
     * 判断系统是否可以处理，如果可以，返回true，同时扩大容量，如果不可以返回false
     * @param increaseSize
     * @return
     */
    public static synchronized boolean canProcess(long increaseSize) {
        long sizeTemp = size + increaseSize;
        if (sizeTemp > maxSize) {
            return false;
        }
        size = sizeTemp;
        return true;
    }

    /**
     * 判断系统是否可以处理，如果可以，返回true，同时扩大容量，如果不可以返回false
     * @param file
     * @return
     */
    public static synchronized boolean canProcess(File file) throws Exception {
        return canProcess(new FileInputStream(file));
    }

    /**
     * 判断系统是否可以处理，如果可以，返回true，同时扩大容量，如果不可以返回false
     * @param inputStream
     * @return
     */
    public static synchronized boolean canProcess(InputStream inputStream) throws Exception {
        long increaseSize = inputStream.available();
        return canProcess(increaseSize);
    }
    /**
     * 减少容量，处理完成后调用
     * @param inputStream
     */
    public static synchronized void reduce(InputStream inputStream) throws Exception {
        reduce(inputStream.available());
    }
    /**
     * 减少容量，处理完成后调用
     * @param file
     */
    public static synchronized void reduce(File file) throws Exception {
        reduce(new FileInputStream(file));
    }
    /**
     * 减少容量，处理完成后调用
     * @param reduceSize
     */
    public static synchronized void reduce(long reduceSize) {
        long sizeTemp = size - reduceSize;
        if (sizeTemp < 0) {
            sizeTemp = 0;
        }
        size = sizeTemp;
    }
}
