package com.keepgreen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    private int id;//id
    private String username;//用户名
    private String psw;//密码
    private Timestamp createdAt;//用户信息创建时间
    private Timestamp updatedAt;// 用户信息更新时间；
}
