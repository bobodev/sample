package com.sample.test.controller;

import com.sample.test.ApplicationTest;
import com.sample.test.constant.GlobalConstants;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wanchongyang
 * @date 2018/3/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public abstract class BaseControllerTest {
    static {
        GlobalConstants.setIsInTest(true);
    }
}
