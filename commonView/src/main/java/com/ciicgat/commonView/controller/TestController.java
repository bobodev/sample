package com.ciicgat.commonView.controller;

import com.ciicgat.api.hrscenter.model.Employee;
import com.ciicgat.commonView.model.Student;
import com.ciicgat.sdk.lang.convert.ApiResponse;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/commonView/test")
public class TestController extends CommonAction{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    @ResponseBody
    @RequestMapping(value = "/testJson")
    public Object testJson(HttpServletRequest request, String name){
        Student student=new Student();
        student.setId(1);
        student.setName(name);
        return student;
    }

    @RequestMapping("/testJsonp")
    @ResponseBody
    public JSONPObject testJsonp(String callback, String name) {
        LOGGER.info("callback:{}",callback);
        Student student=new Student();
        student.setId(1);
        student.setName(name);
        return new JSONPObject(callback, student);
    }
}
