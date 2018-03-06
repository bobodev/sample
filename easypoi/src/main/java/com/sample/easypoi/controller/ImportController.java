package com.sample.easypoi.controller;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.sample.easypoi.model.Student;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/easypoi/import")
public class ImportController extends BaseController{
    /**
     * 模版导入
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import01")
    public ResponseEntity<Object> import01(@RequestParam("file") MultipartFile file) throws Exception{
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Student> list = ExcelImportUtil.importExcel(
                file.getInputStream(),Student.class, params);
        return ResponseEntity.ok(list.size());
    }
    /**
     * 模版导入--校验
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import02")
    public ResponseEntity<Object> import02(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws Exception{
        POICacheManager.setLoadingCache(null);
//        Workbook workbook = new XSSFWorkbook(file.getInputStream());
//        Sheet sheetAt = workbook.getSheetAt(0);
//        Row row = sheetAt.getRow(2);

        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedVerfiy(true);
        ExcelImportResult<Student> excelImportResult = ExcelImportUtil.importExcelMore(
                file.getInputStream(),Student.class, params);

        if(excelImportResult.isVerfiyFail()){
            Workbook failWorkbook = excelImportResult.getFailWorkbook();
            FileOutputStream fos = new FileOutputStream("错误数据01.xlsx");
            failWorkbook.write(fos);
            fos.flush();
            fos.close();
            return ResponseEntity.ok(excelImportResult.getFailList());
        }
        return ResponseEntity.ok(excelImportResult.getList());
    }
}
