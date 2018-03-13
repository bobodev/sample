package com.sample.easypoi.core;

import cn.afterturn.easypoi.cache.ExcelCache;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.excel.export.template.ExcelExportOfTemplateUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Excel 导出工具类
 */
public class ExcelExportHelper {

    /**
     * excel 多sheet导出，解决源码中的bug
     *
     * @param params 导出参数
     * @param map 参数值
     * @return 导出后的workbook
     * @throws Exception 异常类
     */
    public static Workbook exportExcel(TemplateExportParams params, Map<Integer, Map<String, Object>> map) throws Exception {
        Workbook wb = ExcelCache.getWorkbook(params.getTemplateUrl(), params.getSheetNum(),
                params.isScanAllsheet());
        ExcelExportOfTemplateUtil excelExportOfTemplateUtil = new ExcelExportOfTemplateUtil();
        excelExportOfTemplateUtil.setExcelExportStyler((IExcelExportStyler) params.getStyle()
                .getConstructor(Workbook.class).newInstance(wb));
        Workbook workbook = excelExportOfTemplateUtil.createExcleByTemplate(params, map);
        return workbook;
    }


    /**
     * 导出公用工具类
     *
     * @param workbook 要导出的workbook
     * @param filename 文件名
     * @throws Exception 异常信息
     */
    public static void exportCommon(Workbook workbook, String filename) throws Exception {
        //输出流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
        workbook.write(bufferedOutputStream);
        bufferedOutputStream.flush();
        //关闭流
        bufferedOutputStream.close();
    }
}
