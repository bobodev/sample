package com.sample.scaffold.service.biz;

import com.alibaba.fastjson.JSON;
import com.sample.scaffold.Application;
import com.sample.scaffold.mapper.SingleMapper;
import com.sample.scaffold.model.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MybatisTest {
    @Autowired
    private SingleMapper singleMapper;

    @Test
    public void test01() throws Exception{
        List<Single> singles = this.singleMapper.findSingle();
        System.out.println("JSON.toJSONString(singles) = " + JSON.toJSONString(singles));
    }
}
