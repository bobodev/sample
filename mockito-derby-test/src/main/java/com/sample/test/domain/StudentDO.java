package com.sample.test.domain;

import java.io.Serializable;

/**
 * @author wanchongyang
 * @date 2018/3/27
 */
public class StudentDO implements Serializable {
    private Integer id;
    private String studentNo;
    private String studentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
