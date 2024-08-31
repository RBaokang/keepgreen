package com.keepgreen.service;

import com.keepgreen.pojo.Result;

public interface SecureService {
    /**
     * 更新密码
     *
     * @param username 用户名
     * @param oldPsw   旧密码
     * @param psw      新密码
     * @return 结果
     */
    Result changePsw(String username, String oldPsw, String psw);

    /**
     * 找回密码
     *
     * @param username 用户账号
     * @param code     验证码
     * @param psw      新密码
     * @return 返回结果
     */
    Result findPsw(String username, String code, String psw);
}
