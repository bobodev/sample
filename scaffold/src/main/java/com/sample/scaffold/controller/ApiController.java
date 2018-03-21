package com.sample.scaffold.controller;

import com.sample.scaffold.contract.dto.AddrDto;
import com.sample.scaffold.contract.dto.UserDto;
import com.sample.scaffold.service.biz.IEchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scaffold")
public class ApiController {

    @Autowired
    private IEchoService echoService;

    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public ResponseEntity<Object> echo() throws Exception {
        return ResponseEntity.ok(echoService.echo());
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Object> user() throws Exception {
        UserDto user = new UserDto();
        user.setId(1);
        user.setPersonName("fsdfsdfsdf");
        user.setPhoneNumber("fsdfsdfsdf");
        AddrDto addr = new AddrDto();
        addr.setCompanyAddr("fdsf");
        addr.setHomeAddr("fsdf");
        user.setAddrDto(addr);
        return ResponseEntity.ok(user);
    }
}
