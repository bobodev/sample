package com.sample.scaffold.contract.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class AddrDto implements java.io.Serializable{
    @NotEmpty(message = "homeAddr不允许为空")
    @Size(min = 0, max = 50, message = "homeAddr允许输入长度范围为【0-50】")
    private String homeAddr;
    private String companyAddr;

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }
}
