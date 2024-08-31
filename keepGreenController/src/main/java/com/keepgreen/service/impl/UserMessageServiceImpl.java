package com.keepgreen.service.impl;

import com.keepgreen.exception.NullException;
import com.keepgreen.mapper.UserMessageMapper;
import com.keepgreen.service.UserMessageService;
import com.keepgreen.utils.Md5Utils;
import com.keepgreen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public void changeName(String name, String username) {
        if (StringUtils.isEmpty(name)) {
            throw new NullException();
        }
        userMessageMapper.updateName(name, username);
    }

    @Override
    public void changeBirthDate(String username, Date date) {
        if (date == null || StringUtils.isEmpty(username)) {
            throw new NullException();
        }
        userMessageMapper.updateBirthDate(username, date);
    }

}
