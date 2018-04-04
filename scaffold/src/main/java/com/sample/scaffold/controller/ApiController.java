package com.sample.scaffold.controller;

import com.alibaba.fastjson.JSON;
import com.sample.scaffold.contract.dto.SingleDto;
import com.sample.scaffold.service.biz.IEchoService;
import com.sample.scaffold.service.biz.ISingleService;
import com.sample.scaffold.service.biz.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scaffold")
public class ApiController {

    @Autowired
    private IEchoService echoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISingleService singleService;

    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public ResponseEntity<Object> echo() throws Exception {
        return ResponseEntity.ok(echoService.echo());
    }

    @RequestMapping(value = "/findOneUser", method = RequestMethod.GET)
    public ResponseEntity<Object> findOneUser(Long id) throws Exception {
        return ResponseEntity.ok(userService.findOneUser(id));
    }
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(Long id) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @RequestMapping(value = "/saveSingle", method = RequestMethod.POST)
    public ResponseEntity<Object> saveSingle(@RequestBody SingleDto singleDto) throws Exception {
        System.out.println("JSON.toJSONString(singleDto) = " + JSON.toJSONString(singleDto));
        singleService.saveSingle(singleDto);
        return ResponseEntity.ok(true);
    }
}
