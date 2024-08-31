package com.keepgreen.service;

import com.keepgreen.pojo.Result;

public interface LoginService {

    /**
     * 登录结果，使用 MD5 加密
     *
     * @param username 用户名
     * @param psw      密码
     * @return 返回结果
     */
    public Result login(String username, String psw);

    /**
     * 登录前检测，检验账号密码是否符合格式要求
     *
     * @param username 账户名
     * @param psw      密码
     */
    public void loginPerCheck(String username, String psw);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param phoneMsg 手机型号信息
     */
    public void recordMsg(String username, String phoneMsg);
}
