package com.keepgreen.mapper;

import com.keepgreen.pojo.dto.UserMessageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;

@Mapper
public interface UserMessageMapper {

    /**
     * 查询旧密码
     *
     * @param username 用户名
     * @return 返回旧密码
     */
    @Select("select psw from user_account where username = #{username};")
    String select(String username);

    /**
     * 更新密码
     *
     * @param username 用户名
     * @param psw      密码
     */
    @Update("update user_account set psw = #{psw} where username = #{username}")
    void update(String username, String psw);

    /**
     * 查询用户信息
     *
     * @param username 用户名(邮箱)
     * @return 返回信息
     */
    @Select("select name,email,birth_date from user_message where email = #{username}")
    UserMessageDto getMassage(String username);

    /**
     * 更改用户昵称
     *
     * @param name     昵称
     * @param username 用户名
     */
    @Update("update user_message set name=#{name} where email=#{username}")
    void updateName(String name, String username);

    /**
     * 更新用户生日
     *
     * @param username 用户名
     * @param date     日期
     */
    @Update("update user_message set birth_date=#{date} where email=#{username}")
    void updateBirthDate(String username, Date date);
}
