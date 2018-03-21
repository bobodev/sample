package com.sample.scaffold.contract.dto;

public class UserDto {
    private Integer id;
    private String personName;
    private String phoneNumber;
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
