package com.sample.easypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.sample.easypoi.model.ExcelVerifyEntity;
import com.sample.easypoi.model.Student;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ImportTest extends BaseTest{

    /**
     * 模版导入
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Student> list = ExcelImportUtil.importExcel(
                file, Student.class, params);
        System.out.println(list.size());

    }

    /**
     * 模版导入-验证
     *
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入-验证(test02_import).xlsx");
        ImportParams params = new ImportParams();
        params.setNeedVerfiy(true);
        ExcelImportResult<ExcelVerifyEntity> excelImportResult = ExcelImportUtil.importExcelMore(file, ExcelVerifyEntity.class, params);
        if (excelImportResult.isVerfiyFail()) {
            Workbook failWorkbook = excelImportResult.getFailWorkbook();
            FileOutputStream fos = new FileOutputStream(new File(RESOURCE_PATH + "/import/模版导入-验证-失败数据(test02_import).xlsx"));
            failWorkbook.write(fos);
            fos.flush();
            fos.close();
        }
    }
}
