package com.nib.demanduck.util;

import com.nib.demanduck.DemanduckApplicationTests;
import com.nib.demanduck.constant.SceneConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/10/10 14:04
 */
class ValidCodeUtilsTest extends DemanduckApplicationTests {

    @Autowired
    private ValidCodeUtils validCodeUtils;

    @Test
    void send() {
        String email = "mysterin@163.com";
        String scene = SceneConstant.REGISTER;
        validCodeUtils.send(email, scene);
    }
}