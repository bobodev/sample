package com.sample.scaffold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/scaffold")
public class ViewController {

    @RequestMapping(value = "/testRedirect", method = RequestMethod.GET)
    public String testRedirect(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/a.html");
        return "redirect:/a.html";
    }

}
