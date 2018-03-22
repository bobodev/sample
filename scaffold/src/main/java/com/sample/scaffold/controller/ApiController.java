package com.sample.scaffold.controller;

import com.sample.scaffold.model.User;
import com.sample.scaffold.service.biz.IEchoService;
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
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
