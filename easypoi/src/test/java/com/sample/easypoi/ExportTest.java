package com.sample.easypoi;

import cn.afterturn.easypoi.cache.ExcelCache;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil;
import com.sample.easypoi.model.Student;
import com.sample.easypoi.service.DataService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.util.*;

public class ExportTest extends BaseTest{
    /**
     * 模版导出
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(100);
        List<String> headers = new ArrayList<>(Arrays.asList("学生姓名", "学生性别", "出生日期"));
        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("headers", headers);
        map.put("students", students);
        map.put("title", "模版导出-学生数据");
        String templateUrl = RESOURCE_PATH+"/template/export_01.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, RESOURCE_PATH+"/export/模版导出(test01_export).xlsx");
    }

    /**
     * 模版导出-自定义列-65535
     *
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(65535);
        //构建动态列
        boolean needName = false;
        boolean needSex = false;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("students", students);
        map.put("needName", needName);
        map.put("needSex", needSex);
        map.put("needBirthday", needBirthday);
        map.put("title", "模版导出-自定义列-学生数据");
        String templateUrl = RESOURCE_PATH+"/template/export_02.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, RESOURCE_PATH+"/export/模版导出-自定义列-65535(test02_export).xlsx");
    }

//    /**
//     * 模版导出-大数据量导出-500000
//     *
//     * @throws Exception
//     */
//    @Test
//    public void test03() throws Exception {
//        //step1 准备数据
//        List<Student> students = new DataService().loadData(500000);
//        //构建动态列
//        boolean needName = false;
//        boolean needSex = false;
//        boolean needBirthday = false;
//
//        //step2 构造数据和模版
//        Map<String, Object> map = new HashMap<>();
//        map.put("students", students);
//        map.put("needName", needName);
//        map.put("needSex", needSex);
//        map.put("needBirthday", needBirthday);
//        map.put("title", "模版导出-自定义列-学生数据");
//        String templateUrl = RESOURCE_PATH+"/template/export_02.xlsx";
//        //step3 导出
//        TemplateExportParams params = new TemplateExportParams(
//                templateUrl);
//        params.setColForEach(true);
//        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
//        this.exportCommon(workbook, RESOURCE_PATH+"/export/模版导出-大数据量导出-500000(test03_export).xlsx");
//    }


    /**
     * 模版导出-多sheet导出
     *
     * @throws Exception
     */
    @Test
    public void test04() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(1000);
        //构建动态列
        boolean needName = false;
        boolean needSex = true;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> mapTemp1 = new HashMap<>();
        mapTemp1.put("students", students);
        mapTemp1.put("needName", needName);
        mapTemp1.put("needSex", needSex);
        mapTemp1.put("needBirthday", needBirthday);
        mapTemp1.put("title", "模版导出-多sheet");
        mapTemp1.put("title2", "模版导出-多sheet2");
        map.put(0, mapTemp1);
        map.put(1, mapTemp1);
        String templateUrl = RESOURCE_PATH+"/template/export_03.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        Integer[] sheetNum = new Integer[]{0, 1};
        params.setSheetNum(sheetNum);
        params.setColForEach(true);

        //解决源码中bug
        Workbook wb = ExcelCache.getWorkbook(params.getTemplateUrl(), params.getSheetNum(),
                params.isScanAllsheet());
        ExcelExportOfTemplateUtil excelExportOfTemplateUtil = new ExcelExportOfTemplateUtil();
        excelExportOfTemplateUtil.setExcelExportStyler((IExcelExportStyler) params.getStyle()
                .getConstructor(Workbook.class).newInstance(wb));
        Workbook workbook = excelExportOfTemplateUtil.createExcleByTemplate(params, map);

//        Workbook workbook = ExcelExportUtil.exportExcel(map,params);//一个sheet用这个
        this.exportCommon(workbook,  RESOURCE_PATH+"/export/模版导出-多sheet(test04_export).xlsx");
    }

}
