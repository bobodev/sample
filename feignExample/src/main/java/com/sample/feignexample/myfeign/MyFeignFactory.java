package com.sample.feignexample.myfeign;

import com.alibaba.fastjson.JSON;
import com.sample.feignexample.Proxy.MethodProxy;

import java.lang.reflect.Proxy;

/**
 * Created by issac.hu on 2018/3/29.
 */
public class MyFeignFactory {


    public static MyFeign build(){
        return new MyFeign();
    }

    public static class MyFeign{

        private MyDecoder decoder;
        private String url;

        public MyFeign(){
           this(defaultDecoder());
        }
        public MyFeign(MyDecoder decoder){
          this.decoder=decoder;
        }

        public MyFeign decoder(MyDecoder decoder){
            this.decoder=decoder;
           return this;
        }

        public<T> T target(Class<T> clazz, String url){
            this.url=url;
            MethodProxy methodProxy = new MethodProxy(this);
            Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, methodProxy);
            return (T) o;
        }

        public MyDecoder getDecoder() {
            return decoder;
        }

        public void setDecoder(MyDecoder decoder) {
            this.decoder = decoder;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static MyDecoder defaultDecoder(){
        return JSON::parseObject;
    }

    public static <T> T newInstance(Class<T> clazz){
        MyEndPoint annotation = clazz.getAnnotation(MyEndPoint.class);
        String url="";
        if (annotation!=null){
            url=annotation.value();
        }
        return  build().target(clazz,url);
    }

}
