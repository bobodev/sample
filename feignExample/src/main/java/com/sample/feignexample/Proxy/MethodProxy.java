package com.sample.feignexample.Proxy;

import com.sample.feignexample.base.ApiResponse;
import com.sample.feignexample.myfeign.MyFeignFactory;
import com.sample.feignexample.myfeign.MyParam;
import com.sample.feignexample.myfeign.MyRequestLine;
import com.sample.feignexample.utils.HttpClientHelperWithoutSSL;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by issac.hu on 2018/3/29.
 */
public class MethodProxy implements InvocationHandler {
    private MyFeignFactory.MyFeign myFeign;


    public MethodProxy(MyFeignFactory.MyFeign myFeign){
        this.myFeign=myFeign;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //如果接口有默认方法，直接执行
       if (method.isDefault()){
           return handleDefaultMethod(proxy, method, args);
       }else {
         String result= handleMethod(method, args);
        if (method.getReturnType().isAssignableFrom(ApiResponse.class)){
            return myFeign.getDecoder().decode(result, ApiResponse.class);
        }
        return result;
       }

    }

    private Object handleDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);

        Class<?> declaringClass = method.getDeclaringClass();
        int allModes = MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE;
        return constructor.newInstance(declaringClass, allModes)
                .unreflectSpecial(method, declaringClass)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }

    private String handleMethod(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Map<String,Object> params=new HashMap<>();
        for (int i=0;i<args.length;i++){
            MyParam myParamAnnotation = parameters[i].getAnnotation(MyParam.class);
            if (myParamAnnotation!=null){
                String key = myParamAnnotation.value();
                params.put(key,args[i]);
            }
        }
        MyRequestLine myRequestLineAnnotation = method.getAnnotation(MyRequestLine.class);
        String result="";
        if (myRequestLineAnnotation!=null){
            String request = myRequestLineAnnotation.value();
            String reqMethod="GET";
            String reqAddress="/";

            try {
                String[] split = request.split("\\s");
                 reqMethod=split[0].trim();
                 reqAddress=split[1].trim();

            }catch (Exception e){
                 throw  new RuntimeException("请求参数行格式不正确:"+request);
            }
            String realUrl=myFeign.getUrl()+reqAddress;
            System.out.println(reqMethod+","+"url:"+realUrl+",params:"+params);
            if ("GET".equals(reqMethod)){
                result = HttpClientHelperWithoutSSL.getUriContentUsingGet(realUrl, params);
            }else if ("POST".equals(reqMethod)){
                result= HttpClientHelperWithoutSSL.getUriContentUsingPost(realUrl,params,"utf-8");
            }

        }
        return result;
    }


}
