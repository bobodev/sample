package com.sample.easypoi.core;

import java.util.HashMap;
import java.util.Map;


public class ExcelImportParam {
    private int startRowNum;//开始行 从0开始
    private int headerRowNum;//表头行
    private Map<String, Object> replaceMap = new HashMap<>();

    public int getHeaderRowNum() {
        return headerRowNum;
    }

    public void setHeaderRowNum(int headerRowNum) {
        this.headerRowNum = headerRowNum;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public Map<String, Object> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, Object> replaceMap) {
        this.replaceMap = replaceMap;
    }
}
