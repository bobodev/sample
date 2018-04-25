package com.sample.scaffold.service.biz;

import com.sample.scaffold.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EchoServiceTest {
    @Autowired
    private IEchoService echoService;

    @Test
    public void echo() throws Exception {
        echoService.echo();
    }

}