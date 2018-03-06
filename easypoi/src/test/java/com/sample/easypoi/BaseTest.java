package com.sample.easypoi;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class BaseTest {
    public static final String RESOURCE_PATH = "/Users/sunguangzhu/develop/github/sample/easypoi/src/main/resources";

    public void exportCommon(Workbook workbook, String filename) throws Exception {
        //输出流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
        workbook.write(bufferedOutputStream);
        bufferedOutputStream.flush();
        //关闭流
        bufferedOutputStream.close();
    }

}
