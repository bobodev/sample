package com.sample.scaffold.test;

import com.alibaba.fastjson.JSONObject;
import com.sample.scaffold.Application;
import com.sample.scaffold.contract.dto.UserDto;
import com.sample.scaffold.service.biz.MockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MockServiceTest {
    @Autowired
    private MockService mockService;

    @Test
    public void mockString() throws Exception {
        String s = mockService.mockString();
        System.out.println("s = " + s);
    }

    @Test
    public void mockInteger() throws Exception {
        Integer integerValue = mockService.mockInteger();
        System.out.println("integerValue = " + integerValue);
    }

    @Test
    public void mockInt() throws Exception {
        int intValue = mockService.mockInt();
        System.out.println("intValue = " + intValue);
    }

    @Test
    public void mockBigDecimal() throws Exception{
        BigDecimal bigDecimal = mockService.mockBigDecimal();
        System.out.println("bigDecimal = " + bigDecimal);
    }

    @Test
    public void mockListString() throws Exception{
        List<String> strings = mockService.mockListString();
        System.out.println("strings = " + strings);
    }

    @Test
    public void mockListJson() throws Exception{
        List<JSONObject> jsonObjects = mockService.mockListJson();
        System.out.println("jsonObjects = " + jsonObjects);
    }


    @Test
    public void mockListUserDto() throws Exception{
        List<UserDto> userDtos = mockService.mockListUserDto();
        System.out.println("userDtos = " + userDtos);
    }


    @Test
    public void mockUserDto() throws Exception{
        UserDto userDto = mockService.mockUserDto();
        System.out.println("userDtos = " + userDto);
    }

}