package com.nib.demanduck.util;

import com.nib.demanduck.DemanduckApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/10/8 16:01
 */
class MailUtilsTest extends DemanduckApplicationTests {

    @Autowired
    private MailUtils mailUtils;

    @Test
    void sendMail() {
        String to = "mysterin@163.com";
        String subject = "测试邮件";
        String text = "测试邮件内容\n换行";
        mailUtils.sendMail(to, subject, text);
    }
}