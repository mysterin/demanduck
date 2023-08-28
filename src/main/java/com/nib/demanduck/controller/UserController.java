package com.nib.demanduck.controller;

import com.nib.demanduck.api.request.CreateUserRequest;
import com.nib.demanduck.api.request.LoginUserRequest;
import com.nib.demanduck.api.response.LoginUserData;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
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
}
