package com.nib.demanduck.controller;

import com.nib.demanduck.entity.User;
import com.nib.demanduck.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public User get(Long id) {
        User user = userService.getById(id);
        return user;
    }

    @RequestMapping("/delete")
    public Boolean delete(Long id) {
        return userService.removeById(id);
    }

    @RequestMapping("/create")
    public User create() {
        User user = new User();
        user.setUserName(RandomStringUtils.randomAlphabetic(5));
        user.setMobile(RandomStringUtils.randomNumeric(11));
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@nib.com");
        user.setPassword(DigestUtils.md5Hex(DigestUtils.sha1Hex(RandomStringUtils.randomAlphabetic(8))));
        userService.save(user);
        return user;
    }

}
