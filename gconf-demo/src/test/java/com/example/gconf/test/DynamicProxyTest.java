package com.example.gconf.test;

import com.example.gconf.business.ISay;
import com.example.gconf.business.SayImp;
import com.example.gconf.utils.DynamicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by issac.hu on 2018/3/14.
 */
public class DynamicProxyTest {

    @Test
    public void test(){
        ISay iSay = (ISay) Proxy.newProxyInstance(SayImp.class.getClassLoader(), SayImp.class.getInterfaces(), new DynamicProxy(new SayImp()));
        iSay.say();
        Assert.assertEquals(iSay.say2(),"say2");
    }
}
