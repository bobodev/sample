package com.sample.easypoi.controller;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;

public class BaseController {

    public void exportCommon(Workbook workbook, String filename, HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");
        String downlaodFilename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + downlaodFilename + ".xlsx");
        //输出流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        workbook.write(bufferedOutputStream);
        bufferedOutputStream.flush();
        //关闭流
        bufferedOutputStream.close();
    }
}
