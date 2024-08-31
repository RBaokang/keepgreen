package com.keepgreen.service;

import java.sql.Date;

public interface UserMessageService {


    /**
     * 更改用户昵称
     *
     * @param name     更改后信息
     * @param username 用户名
     */
    void changeName(String name, String username);

    /**
     * 修稿用户生日
     *
     * @param username 用户生日
     * @param date     生日
     */
    void changeBirthDate(String username, Date date);
}
