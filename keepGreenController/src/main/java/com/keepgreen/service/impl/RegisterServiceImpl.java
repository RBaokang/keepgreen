package com.keepgreen.service.impl;

import com.keepgreen.mapper.RegisterMapper;
import com.keepgreen.pojo.Result;
import com.keepgreen.pojo.UserAccount;
import com.keepgreen.pojo.UserMessage;
import com.keepgreen.pojo.dto.UserAccountDto;
import com.keepgreen.service.MailCodeService;
import com.keepgreen.service.RegisterService;
import com.keepgreen.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

import static com.keepgreen.constants.RedisConstants.REGISTER_CODE_KEY;
import static com.keepgreen.constants.UserConstants.REGISTER_NAME;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MailCodeService mailCodeService;
    @Autowired
    private RegisterMapper registerMapper;

    @Override
    public Result register(UserAccountDto userAccount) {
        if (!mailCodeService.checkCode(userAccount.getUsername(), REGISTER_CODE_KEY, userAccount.getCode())) {
            return Result.error("验证码错误!");
        }
        UserAccount user = new UserAccount();
        user.setUsername(userAccount.getUsername());
        user.setPsw(userAccount.getPsw());
        // 存储账号
        Timestamp time = new Timestamp(new Date().getTime());
        user.setCreatedAt(time);
        user.setUpdatedAt(time);
        registerMapper.insertNew(user);

        // 生成并存储用户信息
        UserMessage userMessage = new UserMessage();
        userMessage.setEmail(userAccount.getUsername());
        userMessage.setName(REGISTER_NAME + UUID.generateCode(8));
        userMessage.setUpdatedAt(time);
        userMessage.setCreatedAt(time);
        registerMapper.insertUserMessage(userMessage);

        return Result.success();
    }
}
