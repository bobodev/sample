package com.sample.easypoi;

import com.sample.easypoi.model.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestData {

    public static List<Student> loadData01(){
        List<Student> students = new ArrayList<>();
        for(int i = 0;i<100;i++){
            Student studentEntity = new Student();
            studentEntity.setId(i+"");
            studentEntity.setName("名称"+i);
            studentEntity.setBirthday(new Date());
            studentEntity.setSex(1);
            students.add(studentEntity);
        }
        return students;
    }
}
