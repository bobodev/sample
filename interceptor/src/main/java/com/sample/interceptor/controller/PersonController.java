package com.sample.interceptor.controller;

import com.sample.interceptor.core.Constants;
import com.sample.interceptor.model.RequestInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Stanley Shen
 * @version 1.0.0
 * @date 2017/12/12 13:51
 */
@RestController
public class PersonController {

    @RequestMapping(value = "/person")
    public RequestInfo person(HttpServletRequest request, HttpServletResponse response) {
        RequestInfo requestInfo = (RequestInfo) request.getAttribute(Constants.REQUEST_INFO_TAG);
        return requestInfo;
    }
}
