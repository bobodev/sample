package com.sample.easypoi.service;

import com.sample.easypoi.core.ExcelImportResult;
import com.sample.easypoi.core.ProgressBar;
import com.sample.easypoi.model.Student;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class DataService {
    public static final String RESOURCE_PATH = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources";

    public List<Student> loadData(int size) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Student studentEntity = new Student();
            studentEntity.setId(i + "");
            studentEntity.setName("名称" + i);
            studentEntity.setBirthday(new Date());
            studentEntity.setSex(1);
            students.add(studentEntity);
        }
        return students;
    }

    @Async
    public Future<ExcelImportResult<Student>> processImport(List<Student> students,ProgressBar progressBar) throws Exception {
        ExcelImportResult<Student> excelImportResult = new ExcelImportResult<>();
        List<Student> successList = new ArrayList<>();
        List<Student> failList = new ArrayList<>();
        for (Student student : students) {
            //业务验证
            successList.add(student);//如果失败，放入failList
            progressBar.addCount();
        }
        excelImportResult.setFailList(failList);
        excelImportResult.setSuccessList(successList);
        return new AsyncResult<>(excelImportResult);
    }


    @Async
    public void judgeFinish(ProgressBar progressBar) throws Exception{
        while (!progressBar.isFinish()) {
            long remainTime = progressBar.getRemainTime();
            System.out.println("进度 " + progressBar.getPercent() + "%"+" 剩余时间 "+remainTime+"秒");
            Thread.sleep(50);
        }
        System.out.println("进度 " + progressBar.getPercent() + "%");
    }
}
