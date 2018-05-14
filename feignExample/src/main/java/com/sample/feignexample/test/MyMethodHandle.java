package com.sample.feignexample.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by issac.hu on 2018/4/4.
 */
public class MyMethodHandle {

    public String say(int num){
        System.out.println(num);
        return "say:"+num;
    }

    public static void main(String[] args) {
        MyMethodHandle myMethodHandle=new MyMethodHandle();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle say = lookup.findVirtual(MyMethodHandle.class, "say", MethodType.methodType(String.class, int.class));
            String invoke = (String)say.invoke(myMethodHandle,1);
            System.out.println(invoke);

            MethodHandle methodHandle = say.bindTo(myMethodHandle);
            Object invoke1 = methodHandle.invoke(2);
            System.out.println(invoke1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
