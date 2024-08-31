package com.keepgreen.service.impl;

import com.keepgreen.exception.NullException;
import com.keepgreen.mapper.UserMessageMapper;
import com.keepgreen.pojo.Result;
import com.keepgreen.service.SecureService;
import com.keepgreen.utils.Md5Utils;
import com.keepgreen.utils.StringUtils;
import io.lettuce.core.StringMatchResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.reactive.ApplicationContextServerWebExchangeMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.keepgreen.constants.RedisConstants.PSW_CODE_KEY;

@Service
public class SecureServiceImpl implements SecureService {
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result changePsw(String username, String oldPsw, String psw) {
        String oldD = Md5Utils.hash(oldPsw);

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(oldPsw)) {
            throw new NullException();
        }

        String old = userMessageMapper.select(username);

        if (old.equals(oldD)) {
            userMessageMapper.update(username, psw);
            return Result.success();
        }
        return Result.error("账号或密码错误");
    }

    @Override
    public Result findPsw(String username, String code, String psw) {
        // 1. 验证码是否正确
        String s = stringRedisTemplate.opsForValue().get(PSW_CODE_KEY + username);

        if (code.equals(s)) {
            userMessageMapper.update(username, psw);
            return Result.success();
        }
        return Result.error("验证码错误");
    }
}
