package com.sample.interceptor;

import com.sample.interceptor.core.ExcelImportHelper;
import com.sample.interceptor.core.ExcelImportParam;
import org.junit.Test;

import java.io.File;

public class ExcelHeaderTest extends BaseTest {


    /**
     * 获取最大行
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ExcelImportParam params = new ExcelImportParam();
        int lastRowNum = ExcelImportHelper.getLastRowNum(file, params);
        System.out.println("lastRowNum = " + lastRowNum);
    }
}
