package com.keepgreen.controller;

import com.keepgreen.pojo.Result;
import com.keepgreen.pojo.UserAccount;
import com.keepgreen.pojo.UserMessage;
import com.keepgreen.pojo.dto.UserAccountDto;
import com.keepgreen.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册管理类
 */
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册信息
     *
     * @param userAccount 用户信息
     * @return 返回结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserAccountDto userAccount) {

        return registerService.register(userAccount);
    }


}
