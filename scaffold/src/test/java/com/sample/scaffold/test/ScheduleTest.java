package com.sample.scaffold.test;

import com.sample.scaffold.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ScheduleTest {

    @Test
    public void testSchedule() throws Exception{
        //ScheduleFactory 会自动执行定时任务，观察控制台
        Thread.sleep(30000);
    }
}
