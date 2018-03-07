package com.sample.easypoi.core;

import java.util.HashMap;
import java.util.Map;


public class ExcelImportParam {
    private int startRowNum;//开始行 从0开始
    private int endRowNum;//结束行
    private Map<String, Object> replaceMap = new HashMap<>();

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    public Map<String, Object> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, Object> replaceMap) {
        this.replaceMap = replaceMap;
    }
}
