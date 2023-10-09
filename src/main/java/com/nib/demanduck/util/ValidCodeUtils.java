package com.nib.demanduck.util;

import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.constant.SceneConstant;
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
        mailUtils.sendMail(email, subject, text);
        String key = MessageFormat.format(RedisKeyConstant.VALID_CODE_KEY, email, scene);
        redisUtils.set(key, code, RedisConstant.FIVE_MINUTE);
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
                subject = "【Demanduck】重置密码";
                break;
            case SceneConstant.REGISTER:
                subject = "【Demanduck】注册";
                break;
            default:
                subject = "【Demanduck】";
                break;
        }
        return subject;
    }
}
