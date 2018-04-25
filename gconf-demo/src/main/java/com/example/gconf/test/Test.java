package com.example.gconf.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by issac.hu on 2018/3/20.
 */
public class Test {

    private static Map map=new HashMap();
    private static ConcurrentHashMap map2=new ConcurrentHashMap();

    public static  class sth{

    }

    public Object test(){

        Object xx = map.get("xx");
        if (xx ==null){
            sth sth = new sth();
            map.put("xx",sth);
            /*Object newXX = map2.putIfAbsent("xx", sth);
            if (newXX==null){
                xx=newXX;
            }*/
        }
        return xx;
    }

    public static void main(String[] args) {

    }
}
