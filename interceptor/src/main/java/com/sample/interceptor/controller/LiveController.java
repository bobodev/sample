package com.sample.interceptor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stanley Shen
 * @version 1.0.0
 * @date 2017/12/12 13:51
 */
@RestController
public class LiveController {

    @RequestMapping(value = "/isLive")
    public String isLive() {
        return "服务正常";
    }
}
