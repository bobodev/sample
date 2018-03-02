package com.sample.easypoi.service;

import com.sample.easypoi.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataService {
    public List<Student> loadData(int size){
        List<Student> students = new ArrayList<>();
        for(int i = 0;i<size;i++){
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
