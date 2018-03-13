package com.sample.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiValidationUtil;
import com.sample.easypoi.core.*;
import com.sample.easypoi.model.Student;
import com.sample.easypoi.service.DataService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

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
        ProgressBar progressBar = ProgressBar.createProgressBarByCode("progressBar");

        dataService.judgeFinish(progressBar);

        long start=System.currentTimeMillis();

        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(2);
        List<Student> students = ExcelImportHelper.transferToList(file, Student.class, excelImportParam);

        ExcelImportResult<Student> excelImportResult = new ExcelImportResult();
        List<Student> failList = excelImportResult.getFailList();
        //基本校验
        students.stream().forEach(o->{
            String errorMsg = PoiValidationUtil.validation(o, null);
            if(!StringUtils.isEmpty(errorMsg)){
                o.setErrorMsg(errorMsg);
                failList.add(o);
            }
        });

        if(excelImportResult.isVerifyFail()){//如果失败直接返回
            if (excelImportResult.isVerifyFail()) {
                this.exportErrorWorkBook(excelImportResult.getFailList());
            }
//            return;
        }

        progressBar.setTotal(students.size());
        List<List<Student>> sublist = ExcelCommonUtil.sublist(students, 2);
        List<Future<ExcelImportResult<Student>>> futures = new ArrayList<>();
        for (List<Student> studentList : sublist) {
            futures.add(dataService.processImport(studentList,progressBar));
            Thread.sleep(20);
        }

        excelImportResult = ExcelCommonUtil.dealFutureResult(futures);

        long end=System.currentTimeMillis();
        long l = end - start;
        System.out.println("导入数据共使用时间 " + l+" 秒");

        if (excelImportResult.isVerifyFail()) {
            this.exportErrorWorkBook(excelImportResult.getFailList());
        }

        Thread.sleep(2000);
    }

    private void exportErrorWorkBook(List<Student> students) throws Exception {
        //step1 准备数据
        List<String> headers = new ArrayList<>(Arrays.asList("学生姓名", "学生性别", "出生日期", "错误信息"));
        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("headers", headers);
        map.put("students", students);
        students.stream().filter(o->o.getSex()!=null).forEach(o->o.setSexStr(o.getSex()==1?"男":"女"));
        map.put("title", "模版导出-学生数据-错误数据");
        String templateUrl = RESOURCE_PATH + "/template/export_01_error.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ExcelExportHelper.exportCommon(workbook, RESOURCE_PATH + "/export/模版导出-错误数据(test01_ImportProgressBar).xlsx");
    }

}
