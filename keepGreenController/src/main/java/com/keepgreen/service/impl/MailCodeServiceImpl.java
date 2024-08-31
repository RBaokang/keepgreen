package com.keepgreen.service.impl;

import com.keepgreen.pojo.Result;
import com.keepgreen.service.MailCodeService;
import com.keepgreen.utils.StringUtils;
import com.keepgreen.utils.uuid.UUID;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.keepgreen.constants.RedisConstants.CODE_TTL;


@Service
public class MailCodeServiceImpl implements MailCodeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 邮箱发送
     */
    @Resource
    private JavaMailSenderImpl mailSender;

    @Override
    public Result getCode(String email, String key) throws MessagingException {
        if (StringUtils.isEmpty(email) || !StringUtils.checkEmail(email)) {
            return Result.error("邮箱格式不正确");
        }
        // 生成验证码
        String code = UUID.generateCode(6);
        // 发送邮件
        mail(code, email);
        // redis 存储验证码 (有效时间为2分钟)
        stringRedisTemplate.opsForValue().set(key + email, code, CODE_TTL, TimeUnit.MINUTES);
        // 返回逻辑
        if (!StringUtils.isEmpty(code)) {
            return Result.success();
        }
        return Result.error("发送失败！");
    }

    /**
     * 检验验证码是否正确
     *
     * @param mail 邮箱（用户名）
     * @param key  标志
     * @param code 验证码
     * @return 返是否正确
     */
    @Override
    public boolean checkCode(String mail, String key, String code) {
        // 从redis 取出验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(key + mail);
        return code.equals(cacheCode);
    }

    /**
     * 发送邮件方法
     *
     * @param code  验证码
     * @param email 地址
     * @throws MessagingException 信息异常
     */
    public void mail(String code, String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置一个html邮件信息
        helper.setText("<p style='color: blue'>你的验证码为：" + code + "(有效期为两分钟，请即时操作)</p>", true);
        //设置邮件主题名
        helper.setSubject("FlowerPotNet验证码----验证码");
        //发给谁-》邮箱地址
        helper.setTo(email);
        //谁发的-》发送人邮箱
        helper.setFrom("2818479877@qq.com");
        mailSender.send(mimeMessage);
    }
}
