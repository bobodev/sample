package com.sample.feignexample.myfeign;

/**
 * Created by issac.hu on 2018/3/29.
 */
public interface MyDecoder<T> {
     T decode(String result,Class<T> clazz);
}
