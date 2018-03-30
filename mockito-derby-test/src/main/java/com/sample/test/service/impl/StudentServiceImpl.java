package com.sample.test.service.impl;

import com.sample.test.dao.StudentDao;
import com.sample.test.domain.StudentDO;
import com.sample.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanchongyang
 * @date 2018/3/27
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public StudentDO getById(Integer id) {
        return studentDao.getById(id);
    }
}
