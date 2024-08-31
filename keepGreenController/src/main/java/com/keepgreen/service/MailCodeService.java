package com.keepgreen.service;

import com.keepgreen.pojo.Result;
import jakarta.mail.MessagingException;

public interface MailCodeService {
    /**
     * 获取验证码
     *
     * @param key   标记
     * @param email 邮箱地址
     * @return 返回结果
     */
    Result getCode(String email, String key) throws MessagingException;

    /**
     * 检验验证码是否正确
     * @param mail
     * @param key
     * @param code
     * @return
     */
    boolean checkCode(String mail,String key,String code);
}
