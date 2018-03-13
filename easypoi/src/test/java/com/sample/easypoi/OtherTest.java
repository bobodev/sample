package com.sample.easypoi;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OtherTest extends BaseTest{
    @Test
    public void test01() throws Exception{
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<1000;i++){
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"begin");
                    FileInputStream fileInputStream = new FileInputStream(new File(RESOURCE_PATH + "/import/模版导入-验证(test02_import).xlsx"));
                    System.out.println(Thread.currentThread().getName()+"end");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.run();
        }


        Thread.sleep(15000);

    }


}
