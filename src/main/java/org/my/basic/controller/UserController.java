package org.my.basic.controller;

import org.my.basic.model.User;
import org.my.basic.repository.UserRepository;
import org.my.basic.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 用户注册
    @PostMapping(path = "/register", // URL
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)// 接收的数据类型为 form-data
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

    @GetMapping("/all_user") // 显示所有用户信息，作为登录成功的返回结果
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
