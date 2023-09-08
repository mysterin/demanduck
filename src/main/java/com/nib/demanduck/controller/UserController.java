package com.nib.demanduck.controller;

import com.nib.demanduck.request.user.CreateUserRequest;
import com.nib.demanduck.request.user.LoginUserRequest;
import com.nib.demanduck.response.user.LoginUserData;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param request
     * @return
     * @throws ServiceException
     */
    @PostMapping("/register")
    public Response register(@RequestBody @Validated CreateUserRequest request) throws ServiceException {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        userService.register(user);
        return Response.success();
    }

    /**
     * 登录
     * @param request
     * @return
     * @throws ServiceException
     */
    @PostMapping("/login")
    public Response<User> login(@RequestBody @Validated LoginUserRequest request) throws ServiceException {
        LoginUserData loginUserData = userService.login(request.getEmail(), request.getPassword());
        return Response.success(loginUserData);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public Response logout(@RequestHeader("Token") String token) {
        userService.logout(token);
        return Response.success();
    }
}
