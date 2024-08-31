package com.keepgreen.controller;

import com.keepgreen.pojo.Result;
import com.keepgreen.service.LoginService;
import com.keepgreen.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginService loginService;

    /**
     * 登录  返回 登录失败||成功
     *
     * @param username 用户名
     * @param psw      密码
     * @return 返回信息唯一标识
     */
    @PostMapping("/login")
    public Result login(String username, String psw) {

        log.info("登录：{}--{}", username,psw);

        return loginService.login(username, psw);
    }
}
