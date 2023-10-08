package com.nib.demanduck.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/10/8 15:58
 */
@Component
@Slf4j
public class MailUtils {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendValidCode(String to, String code) {
        String subject = "验证码";
        String text = "您的验证码为：" + code + "，请勿泄露给他人，5 分钟内有效。";
        sendMail(to, subject, text);
    }
}
