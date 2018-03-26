package com.example.gconf.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by issac.hu on 2018/3/14.
 */
public class PropsToBeanUtils {

    /**
     *
     * @param path  在resources类路径下的properties文件
     * @param clazz 转换的普通bean
     * @param setDefaultValueWhenParseException 当发生转换异常时是否需要设置默认值，默认为false不设置
     * @param <T>  泛型返回
     * @return
     */

    public static<T> T toBean(String path,Class<T> clazz,boolean... setDefaultValueWhenParseException) {
         boolean flag=false;
         if (setDefaultValueWhenParseException.length>0){
             flag=setDefaultValueWhenParseException[0];
         }
         Properties properties=new Properties();
        try {
            properties.load(PropsToBeanUtils.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.list(System.out);

        T t=null;
        try {
            t=  clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field:declaredFields){
            String key =field.getName();
            PropsAnnotation annotation = field.getAnnotation(PropsAnnotation.class);
            if (annotation!=null){
                //如果有注解取注解的值，否则取字段名称
                key=annotation.value();
            }
            Object value = properties.get(key);
            String methodName="set"+capitalizeLetter(field.getName());
            if (value!=null){
                try {
                    Method setMethod = clazz.getMethod(methodName,field.getType());
                    setMethod.invoke(t,transferType(field.getType(),value,flag));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


        }

        return t;


    }

    private static  Object transferType(Class<?> clazz,Object value,boolean flag){
        try {
            if (clazz.isAssignableFrom(Integer.class)||clazz.isAssignableFrom(int.class)){
                return Integer.valueOf(String.valueOf(value));
            }else if (clazz.isAssignableFrom(Double.class)||clazz.isAssignableFrom(double.class)){
                return Double.valueOf(String.valueOf(value));
            }else if (clazz.isAssignableFrom(Float.class)||clazz.isAssignableFrom(float.class)){
                return Float.valueOf(String.valueOf(value));
            }else if (clazz.isAssignableFrom(Boolean.class)||clazz.isAssignableFrom(boolean.class)){
                return Boolean.valueOf(String.valueOf(value));
            }

        }catch (Exception e){
            if (flag){
                if (clazz.isAssignableFrom(Integer.class)||clazz.isAssignableFrom(int.class)){
                    return 0;
                }else if (clazz.isAssignableFrom(Double.class)||clazz.isAssignableFrom(double.class)){
                    return 0.0;
                }else if (clazz.isAssignableFrom(Float.class)||clazz.isAssignableFrom(float.class)){
                    return 0.0f;
                }else if (clazz.isAssignableFrom(Boolean.class)||clazz.isAssignableFrom(boolean.class)){
                    return false;
                }
            }

        }
        return value;

    }

    private static String capitalizeLetter(String letter){
     if (letter==null||letter.length()<1){
         return null;
     }
     letter=letter.substring(0,1).toUpperCase()+letter.substring(1);
     return letter;
    }


}
