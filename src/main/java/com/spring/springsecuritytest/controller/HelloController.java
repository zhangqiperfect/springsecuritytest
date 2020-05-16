package com.spring.springsecuritytest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZQ
 * @create 2020-05-14
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/user/hello")
    public String user() {
        return "hello user=========================================s";

    }

    @ResponseBody
    @GetMapping("/dba/hello")
    public String dba() {
        return "hello dba";
    }

    @ResponseBody
    @GetMapping("admin/hello")
    public String admin() {
        return "hello admin";
    }

    @RequestMapping("/login_page")
    public String hello() {
        return "login_page";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    @GetMapping("test")
    public String test() {
        return "admin-------test";
    }


}
