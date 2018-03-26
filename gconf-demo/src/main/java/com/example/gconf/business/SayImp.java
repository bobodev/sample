package com.example.gconf.business;


/**
 * Created by issac.hu on 2018/3/19.
 */
public class SayImp implements ISay {
    @Override
    public void say() {
        System.out.println("hello world!");
    }

    @Override
    public String say2() {
        System.out.println("hello world2!");
        return "say2";
    }
}
