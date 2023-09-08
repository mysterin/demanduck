package com.nib.demanduck.service;

import com.nib.demanduck.api.response.user.LoginUserData;
import com.nib.demanduck.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nib.demanduck.exception.ServiceException;

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

    LoginUserData login(String email, String password) throws ServiceException;

    User getByToken(String token);

    void logout(String token);
}