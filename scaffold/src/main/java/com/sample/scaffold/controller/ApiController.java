package com.sample.scaffold.controller;

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

    @RequestMapping(value = "/echo",method = RequestMethod.GET)
    public ResponseEntity<Object> echo() throws Exception{
        return ResponseEntity.ok(echoService.echo());
    }

}
