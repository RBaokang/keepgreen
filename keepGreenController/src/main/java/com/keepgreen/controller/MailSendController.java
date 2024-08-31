package com.keepgreen.controller;

import com.keepgreen.pojo.Result;
import com.keepgreen.service.MailCodeService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.keepgreen.constants.RedisConstants.PSW_CODE_KEY;
import static com.keepgreen.constants.RedisConstants.REGISTER_CODE_KEY;

@Slf4j
@RestController
public class MailSendController {
    @Autowired
    private MailCodeService mailSendService;

    /**
     * 注册获取邮箱验证码
     *
     * @param email 邮件地址
     * @return 返回code
     */
    @GetMapping("/registerCode/{email}")
    public Result getRegisterCode(@PathVariable String email) throws MessagingException {
        log.info("{}：获取验证码", email);

        return mailSendService.getCode(email, REGISTER_CODE_KEY);
    }

    /**
     * 获取重置密码验证码
     *
     * @param email 邮件地址
     * @return 返回结果
     * @throws MessagingException 邮件异常
     */
    @GetMapping("/pswCode/{email}")
    public Result getRenewPswCode(@PathVariable String email) throws MessagingException {
        log.info("{}：获取验证码", email);

        return mailSendService.getCode(email, PSW_CODE_KEY);
    }


}
