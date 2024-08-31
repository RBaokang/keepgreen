package com.keepgreen.service.impl;

import com.keepgreen.service.TokenService;
import com.keepgreen.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String createToken(String username, String psw, String phoneMsg) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("psw", psw);
        claims.put("phoneMsg", phoneMsg);
        String token = JwtUtils.generateJwt(claims);
        return token;
    }
}
