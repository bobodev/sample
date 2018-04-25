package com.example.gconf.test;

import com.example.gconf.business.ISay;
import com.example.gconf.business.SayImp;
import com.example.gconf.utils.DynamicProxy;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by issac.hu on 2018/3/19.
 */
public class ProxyTest {

    @Test
    public void test(){
        DynamicProxy dynamicProxy = new DynamicProxy(new SayImp());
        ISay iSay = (ISay) Proxy.newProxyInstance(SayImp.class.getClassLoader(), SayImp.class.getInterfaces(), dynamicProxy);
        iSay.say();
        System.out.println(iSay.say2());

    }
}
