package com.keepgreen.service;

import com.keepgreen.pojo.Result;

import com.keepgreen.pojo.dto.UserAccountDto;

public interface RegisterService {

    /**
     * 用户注册
     *
     * @param userAccount 用户信息
     */
    public Result register(UserAccountDto userAccount);
}
