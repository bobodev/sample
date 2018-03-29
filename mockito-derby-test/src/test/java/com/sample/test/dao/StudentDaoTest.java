package com.sample.test.dao;

import com.sample.test.ApplicationTest;
import com.sample.test.domain.StudentDO;
import com.sample.test.utils.EmbeddedDbManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wanchongyang
 * @date 2018/3/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentDaoTest {
    @Resource
    private StudentDao studentDao;

    private static StudentDO studentDO;

    @BeforeClass
    public static void before() {
        EmbeddedDbManager.initTable("student");

        studentDO = new StudentDO();
        studentDO.setStudentNo("code1");
        studentDO.setStudentName("name1");
    }

    @Test
    public void a_insert() {
        int rowCount = studentDao.insertSelective(studentDO);
        Assert.assertEquals(1, rowCount);
    }

    @Test
    public void b_getById() {
        Integer id = 1;
        StudentDO user = studentDao.getById(id);
        Assert.assertEquals("1", user.getId() + "");
    }
}
