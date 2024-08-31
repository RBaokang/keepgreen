package com.keepgreen.service;

public interface TokenService {
    /**
     * 根据信息生成token，采用jwt
     *
     * @param username 用户名
     * @param psw      密码
     * @param UUID     唯一标识
     * @return 返货token
     */
    public String createToken(String username, String psw, String UUID);
}
