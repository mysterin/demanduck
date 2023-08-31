package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nib.demanduck.api.response.LoginUserData;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.mapper.UserMapper;
import com.nib.demanduck.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.util.RedisUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String USER_SESSION_KEY = "USER:SESSION:{0}";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void register(User user) throws ServiceException {
        User dbUser = getByMobileOrEmail(user.getMobile(), user.getEmail());
        if (dbUser != null) {
            throw new ServiceException(ErrorCode.USER_EXIST);
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = DigestUtils.sha1Hex(user.getPassword() + salt);
        user.setPassword(password);
        user.setSalt(salt);
        baseMapper.insert(user);
    }

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public LoginUserData login(String email, String password) throws ServiceException {
        User user = getByMobileOrEmail(null, email);
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_NOT_EXIST);
        }
        String salt = user.getSalt();
        String sha1Hex = DigestUtils.sha1Hex(password + salt);
        if (!StringUtils.equals(sha1Hex, user.getPassword())) {
            throw new ServiceException(ErrorCode.PASSWORD_ERROR);
        }
        // 生成 UUID 表示 token
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = MessageFormat.format(USER_SESSION_KEY, token);
        redisUtils.set(key, user.getId(), RedisConstant.ONE_WEEK);
        LoginUserData loginUserData = new LoginUserData();
        BeanUtils.copyProperties(user, loginUserData);
        loginUserData.setToken(token);
        return loginUserData;
    }

    /**
     * 根据 token 获取用户
     *
     * @param token
     * @return
     */
    @Override
    public User getByToken(String token) {
        String key = MessageFormat.format(USER_SESSION_KEY, token);
        Long userId = redisUtils.get(key, Long.class);
        if (userId == null) {
            return null;
        }
        User user = baseMapper.selectById(userId);
        return user;
    }

    /**
     * 退出登录
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        String key = MessageFormat.format(USER_SESSION_KEY, token);
        redisUtils.del(key);
    }

    /**
     * 根据手机号或邮箱获取用户
     *
     * @param mobile
     * @param email
     * @return
     */
    public User getByMobileOrEmail(String mobile, String email) {
        if (StringUtils.isAllBlank(mobile, email)) {
            return null;
        }
        return lambdaQuery()
                .eq(StringUtils.isNotBlank(mobile), User::getMobile, mobile)
                .or()
                .eq(StringUtils.isNotBlank(email), User::getEmail, email)
                .one();
    }
}
