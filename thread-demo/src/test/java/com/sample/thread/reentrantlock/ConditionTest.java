package com.sample.thread.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest<T> {
    private final T[] items;
    private final Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int head, tail, count;

    public ConditionTest(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public ConditionTest() {
        this(10);
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                //数组满时，线程进入等待队列挂起。线程被唤醒时，从这里返回。
                notFull.await();
            }
            System.out.println("put t = " + t);
            items[tail] = t;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("can not take  wait ..." );
                notEmpty.await();
            }
            T o = items[head];
            System.out.println("can take o = "+o );
            items[head] = null;//GC
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return o;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionTest<String> conditionTest = new ConditionTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                        conditionTest.put("a");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        conditionTest.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}