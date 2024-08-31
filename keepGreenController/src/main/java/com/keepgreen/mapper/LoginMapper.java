package com.keepgreen.mapper;

import com.keepgreen.pojo.UserAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;

@Mapper
public interface LoginMapper {

    /**
     * 查询用户名密码是否存在，若存在则登录成功
     *
     * @param username 用户名
     * @param psw      密码
     * @return 返回结果
     */
    @Select("select * from user_account where username = #{username} and psw = #{psw}")
    UserAccount login(String username, String psw);

    /**
     * 插入登录信息
     *
     * @param username  用户名
     * @param phoneMsg  手机型号信息
     * @param loginTime 登陆时间
     */
    @Insert("insert into user_devices values (null, #{username},#{phoneMsg},#{loginTime})")
    void insertMsg(String username, String phoneMsg, Timestamp loginTime);

    /**
     * 查询用户登录数目
     *
     * @param username 用户名
     * @return 返回用户数目
     */
    @Select("select count(*) from user_devices where username = #{username}")
    int selectLoginNumber(String username);

    /**
     * 删除最早的登录信息记录
     *
     * @param username 用户名
     */
    @Delete("delete from user_devices where (username, login_time) in" +
            "      (select a.username, a.login_time" +
            "       from (select *" +
            "             from user_devices" +
            "             where login_time = (select min(login_time) from user_devices where username = 123)) as a)")
    void delete(String username);
}
