package com.keepgreen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    private String username;//用户名
    private String psw;//密码
    private String code;// 验证码
}
