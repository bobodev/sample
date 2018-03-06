package com.sample.easypoi.service;

import cn.afterturn.easypoi.util.PoiValidationUtil;
import com.sample.easypoi.core.ExcelImportResult;
import com.sample.easypoi.model.Student;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Future<ExcelImportResult<Student>> processImport(List<Student> students) throws Exception {
        ExcelImportResult<Student> excelImportResult = new ExcelImportResult<>();
        List<Student> successList = new ArrayList<>();
        List<Student> failList = new ArrayList<>();
        for (Student student : students) {
            String errorMsg = PoiValidationUtil.validation(student, null);
            if(!StringUtils.isEmpty(errorMsg)){
                student.setErrorMsg(errorMsg);
                failList.add(student);
            }else {
                //业务验证
                successList.add(student);
            }
            ProgressBarService.addCount();
            Thread.sleep(200);
        }
        excelImportResult.setFailList(failList);
        excelImportResult.setSuccessList(successList);
        return new AsyncResult<>(excelImportResult);
    }


    @Async
    public void judgeFinish() throws Exception{
        while (!ProgressBarService.isFinish()) {
            long remainTime = ProgressBarService.getRemainTime();
            System.out.println("进度 " + ProgressBarService.getPercent() + "%"+" 剩余时间 "+remainTime+"秒");
            Thread.sleep(100);
        }
        System.out.println("进度 " + ProgressBarService.getPercent() + "%");
    }
}
