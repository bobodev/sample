package com.sample.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.sample.easypoi.core.ExcelImportHelper;
import com.sample.easypoi.core.ExcelImportParam;
import com.sample.easypoi.core.ExcelImportResult;
import com.sample.easypoi.model.Student;
import com.sample.easypoi.service.DataService;
import com.sample.easypoi.service.ProgressBarService;
import com.sample.easypoi.util.CommonUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;
import java.util.concurrent.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ImportProgressBarTest extends BaseTest {

    @Autowired
    private DataService dataService;

    @Test
    public void test01() throws Exception {
        dataService.judgeFinish();

        long start=System.currentTimeMillis();

        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(2);
        List<Student> students = ExcelImportHelper.transferToList(file, Student.class, excelImportParam);
        ProgressBarService.setTotal(students.size());
        List<List<Student>> sublist = CommonUtil.sublist(students, 20);
        List<Future<ExcelImportResult<Student>>> futures = new ArrayList<>();
        for (List<Student> studentList : sublist) {
            futures.add(dataService.processImport(studentList));
            Thread.sleep(10);
        }
        ExcelImportResult excelImportResult = this.dealFutureResult(futures);

        long end=System.currentTimeMillis();
        long l = end - start;
        System.out.println("导入数据共使用时间 " + l+" 秒");

        if (excelImportResult.isVerifyFail()) {
            this.exportErrorWorkBook(excelImportResult.getFailList());
        }


    }


    private <T> ExcelImportResult<T> dealFutureResult(List<Future<ExcelImportResult<T>>> futures) throws Exception {
        ExcelImportResult<T> excelImportResult = new ExcelImportResult();
        List<T> successList = excelImportResult.getSuccessList();
        List<T> failList = excelImportResult.getFailList();
        List<ExcelImportResult> excelImportResults = new ArrayList<>();
        while (true) {
            List<Future<ExcelImportResult<T>>> futuresTemp = new ArrayList<>();
            for (Future<ExcelImportResult<T>> future : futures) {
                if (future.isDone()) {
                    futuresTemp.add(future);
                    excelImportResults.add(future.get());
                } else {
                    break;
                }
            }
            if (futuresTemp.size() > 0) {
                futures.removeAll(futuresTemp);
            }

            if (futures.size() == 0) {
                break;
            }
            Thread.sleep(100);
        }

        for (ExcelImportResult importResult : excelImportResults) {
            successList.addAll(importResult.getSuccessList());
            failList.addAll(importResult.getFailList());
        }
        return excelImportResult;
    }


    private void exportErrorWorkBook(List<Student> students) throws Exception {
        //step1 准备数据
        List<String> headers = new ArrayList<>(Arrays.asList("学生姓名", "学生性别", "出生日期", "错误信息"));
        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("headers", headers);
        map.put("students", students);
        map.put("title", "模版导出-学生数据-错误数据");
        String templateUrl = RESOURCE_PATH + "/template/export_01_error.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, RESOURCE_PATH + "/export/模版导出-错误数据(test01_ImportProgressBar).xlsx");
    }

}
