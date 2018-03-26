package com.example.gconf.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by issac.hu on 2018/3/20.
 */
public class TestCounter {

    public static class Num{
        private int i=0;
        private AtomicInteger ai=new AtomicInteger();
        public void inc(){
            i++;
           // ai.incrementAndGet();
        }
        public int get(){
            return i;
          // return ai.get();
        }
    }


    public static void go(int count,Runnable runnable){
        ExecutorService pool=Executors.newCachedThreadPool();

        for (int i=0;i<count;i++){
            pool.submit(runnable);
        }

        pool.shutdown();
        while(true){
            if(pool.isTerminated()){
                System.out.println("所有线程运行完毕，线程池关闭！");
                break;
            }

        }

    }


    public static void main(String[] args) {
        Num num = new Num();
        go(10000, () -> num.inc());
        System.out.println(num.get());
    }
}
