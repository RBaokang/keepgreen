package com.keepgreen.constants;

/**
 * redis 存储key
 */
public class RedisConstants {
    public static final String REGISTER_CODE_KEY = "register:code:";//注册标志
    public static final String LOGIN_TOKEN = "login:token:";
    public static final long TOKEN_TTL = 30L;//找回密码标志
    public static final String PSW_CODE_KEY = "psw:code:";//找回密码标志
    public static final long CODE_TTL = 2L;//找回密码标志
}
