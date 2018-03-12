package com.sample.interceptor;

import com.sample.interceptor.core.*;
import com.sample.interceptor.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ImportCustomColumnTest extends BaseTest {

    @Autowired
    private DataService dataService;

    /**
     * 自定义列导入，excel数据解析类型为 List<Map<String,Object>> key为header，value为对应的值
     * 
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        ProgressBar progressBar = ProgressBar.createProgressBarByCode("progressBar");

        dataService.judgeFinish(progressBar);

        long start=System.currentTimeMillis();

        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(2);

        //获取headerRow
        ExcelImportParam paramForHeaderRow = new ExcelImportParam();
        paramForHeaderRow.setHeaderRowNum(1);
        List<String> headerRows= ExcelImportHelper.getHeaderRow(file, paramForHeaderRow);

        excelImportParam.setHeaderRows(headerRows);
        List<Map> mapList = ExcelImportHelper.transferToList(file, Map.class, excelImportParam);

        progressBar.setTotal(mapList.size());
        List<List<Map>> sublist = ExcelCommonUtil.sublist(mapList, 2);
        List<Future<ExcelImportResult<Map>>> futures = new ArrayList<>();
        for (List<Map> tempList : sublist) {
            futures.add(dataService.processImport2(tempList,progressBar));
            Thread.sleep(20);
        }

        ExcelImportResult<Map> excelImportResult = ExcelCommonUtil.dealFutureResult(futures);

        long end=System.currentTimeMillis();
        long l = end - start;
        System.out.println("导入数据共使用时间 " + l+" 秒");

        if (excelImportResult.isVerifyFail()) {
            //导出错误的数据，参考ImportProgressBarTest.exportErrorWorkBook
        }

        Thread.sleep(2000);
    }
}
