package org.my.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    // 访问服务器根目录显示 hello world
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/user")
    public String helloUser() {
        return "Hello User!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }
}
