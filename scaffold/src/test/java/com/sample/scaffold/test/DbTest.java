package com.sample.scaffold.test;

import com.sample.scaffold.Application;
import com.sample.scaffold.mapper.SingleMapper;
import com.sample.scaffold.model.Single;
import com.sample.scaffold.repository.SingleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DbTest {

    @Autowired
    private SingleRepository singleRepository;

    @Autowired
    private SingleMapper singleMapper;

    @Test
    public void testJpa() throws Exception{

        Single single = new Single();
        single.setPropInteger(111);
        single.setPropLong(222l);
        singleRepository.save(single);
    }

    @Test
    public void testMybatis() throws Exception{
        List<Single> singles = singleMapper.findSingle();
        System.out.println("singles = " + singles);
    }
}
