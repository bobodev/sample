package com.sample.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.sample.easypoi.model.Student;
import com.sample.easypoi.service.DataService;
import com.sample.easypoi.util.CommonUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportProcessorTest {
    private List<Student> students = TestData.loadData01();

    /**
     * 正常导出
     * @throws Exception
     */
    @Test
    public void test01() throws Exception{
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
                Student.class, students);
        FileOutputStream fos = new FileOutputStream("export01.xls");
        workbook.write(fos);
        fos.close();
    }


    @Test
    public void test02() throws Exception{
        List<ExcelExportEntity> beanList = new ArrayList<>();
        beanList .add(new ExcelExportEntity("学生姓名", "name"));
        beanList .add(new ExcelExportEntity("学生性别", "sex"));
//        beanList .add(new ExcelExportEntity("进校日期", "registrationDate"));
//        beanList .add(new ExcelExportEntity("出生日期", "birthday"));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试", "测试"), beanList ,
                students);
        FileOutputStream fos = new FileOutputStream("export02.xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void test03() throws Exception{
        //step1 准备数据
        List<Student> students = new DataService().loadData(100000);
        long start = System.currentTimeMillis();
        //构建动态列
        boolean needName = false;
        boolean needSex = true;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("students",students);
        map.put("needName",needName);
        map.put("needSex",needSex);
        map.put("needBirthday",needBirthday);
        map.put("title","模版导出-自定义列-学生数据");
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_02.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params,map);
        FileOutputStream fos = new FileOutputStream("模版导出-自定义列-大数据.xlsx");
        workbook.write(fos);
        fos.close();
        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println("l = " + l);
    }

    @Test
    public void test04() throws Exception{
        //step1 准备数据
        long start = System.currentTimeMillis();
        //构建动态列
        boolean needName = false;
        boolean needSex = true;
        boolean needBirthday = false;
        List<List<Student>> sublist = CommonUtil.sublist(new DataService().loadData(100000), 10000);
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_02.xlsx";
        ExportParams params = new ExportParams(
                "大数据测试", "测试");
        Workbook workbook =null;
        for (List<Student> students : sublist) {
            workbook = ExcelExportUtil.exportBigExcel(params,Student.class,students);
        }
        ExcelExportUtil.closeExportBigExcel();
        FileOutputStream fos = new FileOutputStream("模版导出-自定义列-大数据.xlsx");
        workbook.write(fos);
        fos.close();
        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println("l = " + l);
    }
}
