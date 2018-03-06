package com.sample.easypoi;

import com.sample.easypoi.util.CommonUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class BatchImportTest {
    public static final String RESOURCE_PATH = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources";

    @Test
    public void test01() throws Exception{
        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        Workbook workbook = WorkbookFactory.create(file);

        List<Workbook> workbooks = CommonUtil.splitWorkbook(workbook,2,50);


//        ImportParams params = new ImportParams();
//        params.setNeedVerfiy(true);
//        ExcelImportResult<ExcelVerifyEntity> excelImportResult = ExcelImportUtil.importExcelMore(file, ExcelVerifyEntity.class, params);
//        if (excelImportResult.isVerfiyFail()) {
//            Workbook failWorkbook = excelImportResult.getFailWorkbook();
//            FileOutputStream fos = new FileOutputStream(new File(RESOURCE_PATH + "/import/模版导入-验证-失败数据(test02_import).xlsx"));
//            failWorkbook.write(fos);
//            fos.flush();
//            fos.close();
//        }
    }
}
