package com.ciicgat.commonView.model;

import java.io.Serializable;

public class DialogDTO implements Serializable{
    //选择框类型，1单选2复选，默认1
    private Integer selectType=1;
    //已选id，用逗号分隔
    private String chooseIds;
    //回调事件
    private String callbackFunc;
    //关闭dialog事件
    private String closeFunc;

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public String getChooseIds() {
        return chooseIds;
    }

    public void setChooseIds(String chooseIds) {
        this.chooseIds = chooseIds;
    }

    public String getCloseFunc() {
        return closeFunc;
    }

    public void setCloseFunc(String closeFunc) {
        this.closeFunc = closeFunc;
    }

    public String getCallbackFunc() {
        return callbackFunc;
    }

    public void setCallbackFunc(String callbackFunc) {
        this.callbackFunc = callbackFunc;
    }
}
