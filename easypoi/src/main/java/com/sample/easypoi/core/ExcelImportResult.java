package com.sample.easypoi.core;

import java.util.ArrayList;
import java.util.List;

public class ExcelImportResult<T> {
    private List<T> successList=new ArrayList<>();
    private List<T>  failList=new ArrayList<>();
    private boolean verifyFail;

    public List<T> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<T> successList) {
        this.successList = successList;
    }

    public List<T> getFailList() {
        return failList;
    }

    public void setFailList(List<T> failList) {
        this.failList = failList;
    }

    public boolean isVerifyFail() {
        return failList.size()>0?true:false;
    }

    public void setVerifyFail(boolean verifyFail) {
        this.verifyFail = verifyFail;
    }
}
