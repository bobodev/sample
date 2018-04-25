package com.sample.scaffold.contract.dto;

import com.sample.scaffold.core.validate.annotation.ValidateAnno;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto implements java.io.Serializable{
    private Integer id;
    @NotEmpty(message = "personName不允许为空")
    @Size(min = 0, max = 50, message = "personName允许输入长度范围为【0-50】")
    private String personName;
    private String phoneNumber;
    @ValidateAnno
    @NotNull(message = "addrDto不允许为空")
    private AddrDto addrDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public AddrDto getAddrDto() {
        return addrDto;
    }

    public void setAddrDto(AddrDto addrDto) {
        this.addrDto = addrDto;
    }
}
