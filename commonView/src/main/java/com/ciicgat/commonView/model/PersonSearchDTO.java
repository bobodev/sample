package com.ciicgat.commonView.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PersonSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ids;

    // 企业id
    private Integer enterpriseId;

    // memberId
    private Integer memberId;

    // 部门id
    private Integer departmentId;

    // 工号
    private String code;

    // 姓名
    private String name;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
