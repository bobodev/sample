package com.sample.feignexample.controller;

import com.sample.feignexample.base.ApiResponse;
import com.sample.feignexample.bean.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by issac.hu on 2018/3/28.
 */
@RestController
@RequestMapping("api")
public class ProviderController {

    @RequestMapping(value = "hello",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object hello(String name){
        String result = "hello:" + name;
        System.out.println(result);
        return ApiResponse.success(result);
    }

    @RequestMapping(value = "hello2")
    public Object hello2(String name){
        String result = "hello:" + name;
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "hello3",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object hello3(@RequestBody User user){
        String result = "hello:" + user;
        System.out.println(result);
        return ApiResponse.success(result);
    }

}
