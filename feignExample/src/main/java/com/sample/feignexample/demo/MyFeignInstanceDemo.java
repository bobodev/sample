package com.sample.feignexample.demo;

import com.sample.feignexample.api.IHello;
import com.sample.feignexample.base.ApiResponse;
import com.sample.feignexample.myfeign.MyFeignFactory;

/**
 * Created by issac.hu on 2018/3/29.
 */
public class MyFeignInstanceDemo {

    public static void main(String[] args) {
     //   testJsonReturn();
       // testStringReturn();
        testDefaultBuild();
    }

    public  static  void testJsonReturn(){
        IHello instance = MyFeignFactory.build()
                .decoder(MyFeignFactory.defaultDecoder())
                .target(IHello.class,"http://localhost:8080");
        ApiResponse world = instance.hello("JsonReturn");
        System.out.println(world);
    }

    public  static  void testStringReturn(){
        IHello instance = MyFeignFactory.build().decoder((result, clazz) -> result).target(IHello.class,"http://localhost:8080");
        String world = instance.hello2("StringReturn");
        System.out.println(world);
    }

    public static void testDefaultBuild(){
        IHello iHello = MyFeignFactory.newInstance(IHello.class);
        ApiResponse world = iHello.hello("DefaultBuild");
        System.out.println(world);
    }


}
