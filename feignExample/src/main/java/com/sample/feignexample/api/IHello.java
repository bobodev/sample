package com.sample.feignexample.api;

import com.sample.feignexample.base.ApiResponse;
import com.sample.feignexample.myfeign.MyEndPoint;
import com.sample.feignexample.myfeign.MyParam;
import com.sample.feignexample.myfeign.MyRequestLine;

/**
 * Created by issac.hu on 2018/3/29.
 */
@MyEndPoint("http://localhost:8080")
public interface IHello {
    @MyRequestLine("GET /api/hello")
    ApiResponse hello(@MyParam("name") String name);

    @MyRequestLine("GET /api/hello2")
    String hello2(@MyParam("name") String name);

}
