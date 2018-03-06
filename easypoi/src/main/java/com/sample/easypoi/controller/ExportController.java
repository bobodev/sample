package com.sample.easypoi.controller;

import cn.afterturn.easypoi.cache.ExcelCache;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil;
import com.sample.easypoi.config.CommonProperties;
import com.sample.easypoi.model.Student;
import com.sample.easypoi.service.DataService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/easypoi/export")
public class ExportController extends BaseController {
    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private DataService dataService;

//    @RequestMapping("/export01")
//    public void export01(HttpServletResponse response) throws Exception{
//        List<Student> students = dataService.loadData01();
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("简单导出-学生数据","简单导出",ExcelType.XSSF),
//                Student.class, students);
//        this.exportCommon(workbook,"简单导出",response);
//    }

    /**
     * 模版导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export01")
    public void export01(HttpServletResponse response) throws Exception {
        //step1 准备数据
        List<Student> students = dataService.loadData(100);
        List<String> headers = new ArrayList<>(Arrays.asList("学生姓名", "学生性别", "出生日期"));
        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("headers", headers);
        map.put("students", students);
        map.put("title", "模版导出-学生数据");
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_01.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, "模版导出", response);
    }

    /**
     * 模版导出-自定义列-65535
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export02")
    public void export02(HttpServletResponse response) throws Exception {
        //step1 准备数据
        List<Student> students = dataService.loadData(65535);
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
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_02.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, "模版导出-自定义列", response);
    }

    /**
     * 模版导出-大数据量导出-500000
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export03")
    public void export03(HttpServletResponse response) throws Exception {
        //step1 准备数据
        List<Student> students = dataService.loadData(500000);
        long start = System.currentTimeMillis();
        //构建动态列
        boolean needName = false;
        boolean needSex = true;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("students", students);
        map.put("needName", needName);
        map.put("needSex", needSex);
        map.put("needBirthday", needBirthday);
        map.put("title", "模版导出-自定义列-学生数据-大数据");
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_02.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        this.exportCommon(workbook, "模版导出-自定义列-大数据", response);
        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println("l = " + l);
    }

    /**
     * 模版导出-多sheet导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export04")
    public void export04(HttpServletResponse response) throws Exception {
        //step1 准备数据
        List<Student> students = dataService.loadData(1000);
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
        String templateUrl = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources/template/export_03.xlsx";
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

//        Workbook workbook = ExcelExportUtil.exportExcel(map,params);
        this.exportCommon(workbook, "模版导出-多sheet", response);
    }


}
