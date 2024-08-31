package com.keepgreen.service.impl;

import com.keepgreen.constants.UserConstants;
import com.keepgreen.exception.user.UserNotExistsException;
import com.keepgreen.exception.user.UserPasswordNotMatchException;
import com.keepgreen.mapper.LoginMapper;
import com.keepgreen.mapper.UserMessageMapper;
import com.keepgreen.pojo.Result;
import com.keepgreen.pojo.UserAccount;
import com.keepgreen.pojo.dto.UserMessageDto;
import com.keepgreen.service.LoginService;
import com.keepgreen.service.TokenService;
import com.keepgreen.utils.Md5Utils;
import com.keepgreen.utils.StringUtils;
import com.keepgreen.utils.uuid.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.keepgreen.constants.RedisConstants.LOGIN_TOKEN;
import static com.keepgreen.constants.RedisConstants.TOKEN_TTL;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMessageMapper userMessageMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(String username, String psw) {
        loginPerCheck(username, psw);
        psw = Md5Utils.hash(psw);

        log.info("{}:登录：{}",username,psw);

        UserAccount userAccount = loginMapper.login(username, psw);
        UserMessageDto user = null;
        if (userAccount == null) {

            return Result.error("账号或密码错误！");
        }
        user = userMessageMapper.getMassage(username);
        if (user != null) {
            String token = tokenService.createToken(username, psw, UUID.randomUUID(true).toString());
            user.setToken(token);
            stringRedisTemplate.opsForValue().set(LOGIN_TOKEN+username,token,TOKEN_TTL, TimeUnit.DAYS);
            return Result.success(user);
        }
        return Result.error("系统错误");
    }

    /**
     * 登录前检验用户名密码格式
     *
     * @param username 账户名
     * @param psw      密码
     */
    @Override
    public void loginPerCheck(String username, String psw) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(psw)) {
            throw new UserNotExistsException();
        }

        if (username.length() > UserConstants.USERNAME_MAX_LENGTH
                || username.length() < UserConstants.USERNAME_MIN_LENGTH) {
            throw new UserPasswordNotMatchException();
        }

        if (psw.length() > UserConstants.PASSWORD_MAX_LENGTH || psw.length() < UserConstants.PASSWORD_MIN_LENGTH) {
            throw new UserPasswordNotMatchException();
        }
    }

    /**
     * 记录信息
     *
     * @param username 用户名
     * @param phoneMsg 手机型号信息
     */
    @Override
    public void recordMsg(String username, String phoneMsg) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(phoneMsg)) {
            throw new UserPasswordNotMatchException();
        }
        Timestamp loginTime = new Timestamp(new Date().getTime());
        int num = loginMapper.selectLoginNumber(username);

        if (num == 5) {
            loginMapper.delete(username);
        }
        loginMapper.insertMsg(username, phoneMsg, loginTime);
    }
}
