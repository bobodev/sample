package com.example.gconf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by issac.hu on 2018/3/6.
 */
@Component
public class TestBean {

    @Value("#{setting[projectName]}")
    public String name;
    @Value("${contactlist.appid}")
    public String appid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
