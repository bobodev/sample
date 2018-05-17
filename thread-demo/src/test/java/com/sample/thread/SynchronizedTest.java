package com.sample.thread;

import org.junit.Test;

public class SynchronizedTest {

    @Test
    public void test01() throws Exception{
        synchronized (this){
            System.out.println("true = " + true);
        }
    }
}
