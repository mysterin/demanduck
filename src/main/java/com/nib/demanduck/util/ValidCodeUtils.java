package com.nib.demanduck.util;

import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.constant.SceneConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author linxiaobin
 * @Description 验证码工具
 * @date 2023/10/8 17:24
 */
@Component
@Slf4j
public class ValidCodeUtils {
    @Autowired
    private MailUtils mailUtils;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送验证码
     * @param email
     * @param scene
     */
    public void send(String email, String scene) {
        String code = RandomStringUtils.randomNumeric(6);
        String subject = emailSubject(scene);
        String text = "您的验证码为：" + code + "，请勿泄露给他人，5 分钟内有效。";
        String key = MessageFormat.format(RedisKeyConstant.VALID_CODE_KEY, email, scene);
        redisUtils.set(key, code, RedisConstant.FIVE_MINUTE);
        log.debug("email: {}, scene: {}, 验证码: {}", email, scene, code);
        mailUtils.sendMail(email, subject, text);
    }

    /**
     * 校验验证码
     * @param email
     * @param scene
     * @param code
     * @return
     */
    public boolean check(String email, String scene, String code) {
        String key = MessageFormat.format(RedisKeyConstant.VALID_CODE_KEY, email, scene);
        String redisCode = redisUtils.get(key);
        return code.equals(redisCode);
    }

    public String emailSubject(String scene) {
        String subject = "";
        switch (scene) {
            case SceneConstant.RESET_PASSWORD:
                subject = "重置密码";
                break;
            case SceneConstant.REGISTER:
                subject = "注册";
                break;
            default:
                subject = "";
                break;
        }
        return subject;
    }
}
