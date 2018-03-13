package com.sample.easypoi;

import org.junit.Test;

public class BaseTest {
    public static final String RESOURCE_PATH =  System.getProperty("user.dir")+"/src/main/resources";

    @Test
    public void test01() throws Exception{
        System.out.println("RESOURCE_PATH = " + RESOURCE_PATH);
        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);
    }
}
