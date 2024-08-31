package com.keepgreen.mapper;

import com.keepgreen.pojo.UserAccount;
import com.keepgreen.pojo.UserMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    /**
     * 添加用户账号
     *
     * @param userAccount 用户账号信息
     */
    @Insert("insert into  user_account values (null,#{username},#{psw},#{createdAt},#{updatedAt})")
    public void insertNew(UserAccount userAccount);

    /**
     * 插入用户信息
     *
     * @param userMessage 用户账号
     */
    @Insert("insert into user_message(name, email, created_at, updated_at) values (#{name},#{email},#{createdAt},#{updatedAt}) ")
    void insertUserMessage(UserMessage userMessage);
}
