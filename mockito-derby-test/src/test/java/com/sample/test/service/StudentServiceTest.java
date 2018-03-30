package com.sample.test.service;

import com.sample.test.ApplicationTest;
import com.sample.test.dao.StudentDao;
import com.sample.test.domain.StudentDO;
import com.sample.test.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wanchongyang
 * @date 2018/3/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class StudentServiceTest {
    @Mock
    private StudentDao studentDao;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void test_getById() {
        StudentDO studentDO = new StudentDO();
        studentDO.setStudentNo("C001");
        studentDO.setStudentName("张三");
        Mockito.when(studentDao.getById(Mockito.any(Integer.class))).thenReturn(studentDO);

        StudentDO result = studentService.getById(1);
        Assert.assertEquals(studentDO, result);
    }
}
