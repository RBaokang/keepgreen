package com.keepgreen.controller;

import com.keepgreen.pojo.Result;
import com.keepgreen.service.SecureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecureController {

    @Autowired
    private SecureService secureService;

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param oldPsw   旧密码
     * @param psw      新密码
     * @return 返回结果
     */
    @PutMapping("/psw/{username}")
    public Result changePsw(@PathVariable String username, String oldPsw, String psw) {
        return secureService.changePsw(username, oldPsw, psw);
    }


    /**
     * 找回密码
     *
     * @param username 用户名
     * @param code     验证码
     * @return 返回结果
     */
    @PutMapping("/findPsw/{username}")
    public Result findPsw(@PathVariable String username, String code, String psw) {
        return secureService.findPsw(username, code, psw);
    }

}
