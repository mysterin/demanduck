package com.nib.demanduck.controller;

import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.request.user.CreateUserRequest;
import com.nib.demanduck.request.user.EmailValidCodeRequest;
import com.nib.demanduck.request.user.LoginUserRequest;
import com.nib.demanduck.request.user.ResetEmailPasswordRequest;
import com.nib.demanduck.response.user.LoginUserDTO;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.UserService;
import com.nib.demanduck.util.ValidCodeUtils;
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
    @Autowired
    private ValidCodeUtils validCodeUtils;

    /**
     * 注册
     * @param request
     * @return
     * @throws ServiceException
     */
    @PostMapping("/register")
    public Response register(@RequestBody @Validated CreateUserRequest request) throws ServiceException {
        boolean check = validCodeUtils.check(request.getEmail(), request.getScene(), request.getCode());
        if (!check) {
            throw new ServiceException(ErrorCode.VALID_CODE_ERROR);
        }
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
    public Response<LoginUserDTO> login(@RequestBody @Validated LoginUserRequest request) throws ServiceException {
        LoginUserDTO loginUserDTO = userService.login(request.getEmail(), request.getPassword());
        return Response.success(loginUserDTO);
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

    /**
     * 发送验证码
     */
    @PostMapping("/sendValidCode")
    public Response sendValidCode(@RequestBody @Validated EmailValidCodeRequest request) {
        validCodeUtils.send(request.getEmail(), request.getScene());
        return Response.success();
    }

    /**
     * 重置邮箱密码
     */
    @PostMapping("/resetPassword")
    public Response resetPassword(@RequestBody @Validated ResetEmailPasswordRequest request) {
        boolean check = validCodeUtils.check(request.getEmail(), request.getScene(), request.getCode());
        if (!check) {
            throw new ServiceException(ErrorCode.VALID_CODE_ERROR);
        }
        userService.resetPassword(request.getEmail(), request.getPassword());
        return Response.success();
    }

}
