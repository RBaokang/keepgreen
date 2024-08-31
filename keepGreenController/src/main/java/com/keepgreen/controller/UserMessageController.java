package com.keepgreen.controller;

import com.keepgreen.pojo.Result;
import com.keepgreen.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@Slf4j
@RestController
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    /**
     * 更改昵称
     *
     * @param username 用户名
     * @param name     昵称
     * @return 返回信息
     */
    @PutMapping("/name/{username}")
    public Result changeName(@PathVariable String username, String name) {
        log.info("{}：修改昵称：{} ", username, name);
        userMessageService.changeName(name, username);

        return Result.success();
    }

    /**
     * 更新用户生日
     *
     * @param username 用户名
     * @param date     日期
     * @return 返回结果
     */
    @PutMapping("/birthDate/{username}")
    public Result changeBirthDate(@PathVariable String username, Date date) {
        userMessageService.changeBirthDate(username, date);
        return Result.success();
    }
}
