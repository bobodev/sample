package com.sample.easypoi.service;

import com.sample.easypoi.core.ExcelImportResult;
import com.sample.easypoi.core.ProgressBarService;
import com.sample.easypoi.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class DataService {
    public static final String RESOURCE_PATH = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources";

    @Autowired
    private ProgressBarService progressBarService;

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
    public Future<ExcelImportResult<Student>> processImport(List<Student> students,String code) throws Exception {
        ExcelImportResult<Student> excelImportResult = new ExcelImportResult<>();
        List<Student> successList = new ArrayList<>();
        List<Student> failList = new ArrayList<>();
        for (Student student : students) {
            //业务验证
            successList.add(student);//如果失败，放入failList
            progressBarService.addCount(code);
        }
        excelImportResult.setFailList(failList);
        excelImportResult.setSuccessList(successList);
        return new AsyncResult<>(excelImportResult);
    }

    @Async
    public Future<ExcelImportResult<Map>> processImport2(List<Map> mapList,String code) throws Exception {
        ExcelImportResult<Map> excelImportResult = new ExcelImportResult<>();
        List<Map> successList = new ArrayList<>();
        List<Map> failList = new ArrayList<>();
        for (Map map : mapList) {
            if(map.get("学生星美那个")==null){
                map.put("错误信息","fdsfs");
                failList.add(map);
            }
            //业务验证
            successList.add(map);//如果失败，放入failList
            progressBarService.addCount(code);
        }
        excelImportResult.setFailList(failList);
        excelImportResult.setSuccessList(successList);
        return new AsyncResult<>(excelImportResult);
    }

    @Async
    public void judgeFinish(String code) throws Exception{
        while (!progressBarService.isFinish(code)) {
            long remainTime = progressBarService.getRemainTime(code);
            System.out.println("进度 " + progressBarService.getPercent(code) + "%"+" 剩余时间 "+remainTime+"秒");
            Thread.sleep(50);
        }
        System.out.println("进度 " + progressBarService.getPercent(code) + "%");
    }


}
