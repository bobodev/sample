package com.sample.interceptor;

import com.sample.interceptor.core.ExcelImportHelper;
import com.sample.interceptor.core.ExcelImportParam;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelHeaderTest extends BaseTest {


    /**
     * 获取表头List
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ExcelImportParam params = new ExcelImportParam();
        params.setHeaderRowNum(1);
        List<String> headerRow = ExcelImportHelper.getHeaderRow(file, params);
        System.out.println("headerRow = " + headerRow);
    }
}
