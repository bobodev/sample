package com.sample.test.controller;

import com.sample.test.domain.StudentDO;
import com.sample.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanchongyang
 * @date 2018/3/27
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/getById")
    public ResponseEntity<StudentDO> getById(Integer id) {
        StudentDO studentDO = studentService.getById(id);
        return ResponseEntity.ok(studentDO);
    }
}
