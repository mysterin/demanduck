package com.nib.demanduck.service;

import com.nib.demanduck.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.response.user.LoginUserDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-17
 */
public interface UserService extends IService<User> {
    void register(User user) throws ServiceException;

    LoginUserDTO login(String email, String password);

    User getByToken(String token);

    void logout(String token);

    void resetPassword(String email, String password);
}