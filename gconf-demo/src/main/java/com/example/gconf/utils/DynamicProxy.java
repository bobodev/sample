package com.example.gconf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by issac.hu on 2018/3/19.
 */
public class DynamicProxy implements InvocationHandler {

    private Logger logger= LoggerFactory.getLogger(DynamicProxy.class);

    private Object proxyObj;

    public DynamicProxy(Object proxyObj) {
        this.proxyObj = proxyObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        logger.info("before...");
        Object invoke = method.invoke(proxyObj, args);
        logger.info("after...");
        return null;
    }

}
