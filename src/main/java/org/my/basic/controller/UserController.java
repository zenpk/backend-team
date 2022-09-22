package org.my.basic.controller;

import org.my.basic.model.User;
import org.my.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 用户注册
    @PostMapping(path = "/register", // URL
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,// 接收的数据类型为 form-data
            produces = MediaType.APPLICATION_JSON_VALUE) // 返回的数据类型为 JSON
    public String register(User newUser) {
        User check = userService.findByUsername(newUser.getUsername());
        System.out.println(newUser.getPassword());
        if (check == null) {
            userService.saveUser(newUser);
            return "success";
        } else {
            return "exist";
        }
    }
}
