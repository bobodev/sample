package com.sample.test.dao;

import com.sample.test.domain.StudentDO;

/**
 * @author wanchongyang
 * @date 2018/3/27
 */
public interface StudentDao {
    int insertSelective(StudentDO studentDO);

    StudentDO getById(Integer id);
}
