package com.sample.easypoi;

import com.sample.easypoi.core.ExcelImportHelper;
import com.sample.easypoi.core.ExcelImportParam;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class LastRowNumTest extends BaseTest {


    /**
     * 获取最大行
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
