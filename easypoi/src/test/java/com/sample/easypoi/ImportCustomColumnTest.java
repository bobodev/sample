package com.sample.easypoi;

import com.sample.easypoi.core.*;
import com.sample.easypoi.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ImportCustomColumnTest extends BaseTest {

    @Autowired
    private DataService dataService;

    @Autowired
    private ProgressBarService progressBarService;

    /**
     * 自定义列导入，excel数据解析类型为 List<Map<String,Object>> key为header，value为对应的值
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        System.out.println("test01 begin ");

//        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        File file = new File("/Users/sunguangzhu/Desktop/10w.xlsx");
        boolean b = ProcessPower.canProcess(file);
        System.out.println("b = " + b);
        if (!b) {
            throw new Exception("系统繁忙，请稍后再试");
        }

        String progressBarCode = "progressBar";

        progressBarService.createProgressBarByCode(progressBarCode);

        dataService.judgeFinish(progressBarCode);

        long start = System.currentTimeMillis();

        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(1);

        //获取headerRow
        ExcelImportParam paramForHeaderRow = new ExcelImportParam();
        paramForHeaderRow.setHeaderRowNum(0);
        List<String> headerRows = ExcelImportHelper.getHeaderRow(file, paramForHeaderRow);

        excelImportParam.setHeaderRows(headerRows);
        List<Map> mapList = ExcelImportHelper.transferToList(file, Map.class, excelImportParam);
//      System.out.println("JSON.toJSONString(mapList) = " + JSON.toJSONString(mapList));
        System.out.println("mapList = " + mapList.size());//
        System.out.println("test01 end ");

        //---------- gc
//        file=null;
//        headerRows=null;
//        mapList=null;
//        Runtime.getRuntime().gc();
        //------------gc

        progressBarService.setTotal(progressBarCode, mapList.size());
        List<List<Map>> sublist = ExcelCommonUtil.sublist(mapList, 20);
        List<Future<ExcelImportResult<Map>>> futures = new ArrayList<>();
        for (List<Map> tempList : sublist) {
            futures.add(dataService.processImport2(tempList, progressBarCode));
            Thread.sleep(20);
        }

        ExcelImportResult<Map> excelImportResult = ExcelCommonUtil.dealFutureResult(futures);

        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println("导入数据共使用时间 " + l + " 秒");

        if (excelImportResult.isVerifyFail()) {
            //导出错误的数据，参考ImportProgressBarTest.exportErrorWorkBook
        }

//        //导出自定义列示例 基于 jxls
//        Map<String, Object> map = new HashMap<>();
//        mapList=mapList.subList(0,2);
//        headerRows.add("错误信息");
//        map.put("headers", headerRows);
//        map.put("list", mapList);
//        String templateUrl = RESOURCE_PATH + "/template/export_06(1).xlsx";
//        XLSTransformer transformer = new XLSTransformer();
//        long start1 = System.currentTimeMillis();
//        transformer.registerRowProcessor(new StyleRowProcessor("o"));
//        Workbook workbook = transformer.transformXLS(new FileInputStream(templateUrl), map);
//        long end1 = System.currentTimeMillis();
//        long l1 = end1 - start1;
//        System.out.println("(end1-start) = " + (end1 - start1));
//
//        ExcelExportHelper.exportCommon(workbook, RESOURCE_PATH + "/export/自定义列导出_ImportCustomColumnTest(test01_export).xlsx");
//        Thread.sleep(2000);

        ProcessPower.reduce(new FileInputStream(file).available());
    }

    @Test
    public void test02() throws Exception {

//        for(int i=0;i<10;i++){
//            test01();
//        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        test01();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            test01();
        }

        long end = System.currentTimeMillis();
        long minus = end - start;
        System.out.println("minus = " + minus);
//        System.in.read();
//        while(true){
//            int availableProcessors = Runtime.getRuntime().availableProcessors();
//            long freeMemory = Runtime.getRuntime().freeMemory();
//            long totalMemory = Runtime.getRuntime().totalMemory();
//            System.out.println("availableProcessors = " + availableProcessors);
//            System.out.println("freeMemory = " + freeMemory);
//            System.out.println("totalMemory = " + totalMemory);
//            Runtime.getRuntime().gc();
//            Thread.sleep(2000);
//        }
//        System.in.read();
    }

    @Test
    public void test03() throws Exception {
        File file = new File("/Users/sunguangzhu/Desktop/10w.xlsx");
        System.out.println(12 * 1024 * 1024);
        System.out.println(new FileInputStream(file).available());
//        FileInputStream fileInputStream = new FileInputStream(file);
//        fileInputStream.close();
    }
}
