package com.sample.easypoi;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
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

    @Test
    public void test02() throws Exception{
//        System.out.println("科学计数法数字");
        double num1 = 50123.12E25;
        String s = new BigDecimal(num1).setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString();
        System.out.println("s = " + s);
//        System.out.println("普通数字");
//        double num2 = 50123.12;
//        System.out.println(num2);
//        BigDecimal bd2 = new BigDecimal(num2);
//        System.out.println(bd2.toPlainString());
//        System.out.println(bd2.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString());
    }

    @Test
    public void test03() throws Exception{
        String a = "0001";
    }

}
