package com.nib.demanduck.service.impl;

import com.nib.demanduck.response.user.LoginUserDTO;
import com.nib.demanduck.constant.AccessSource;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
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
    public LoginUserDTO login(String email, String password) {
        User user = getByMobileOrEmail(null, email);
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
        }
        String salt = user.getSalt();
        String sha1Hex = DigestUtils.sha1Hex(password + salt);
        if (!StringUtils.equals(sha1Hex, user.getPassword())) {
            throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
        }
        // 先删除已有 token
        deleteToken(user.getId());
        // 生成 token
        String token = generateToken(user.getId());
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtils.copyProperties(user, loginUserDTO);
        loginUserDTO.setToken(token);
        return loginUserDTO;
    }

    /**
     * 根据 token 获取用户
     *
     * @param token
     * @return
     */
    @Override
    public User getByToken(String token) {
        String key = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_TOKEN_ID_KEY, token);
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
        String key = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_TOKEN_ID_KEY, token);
        redisUtils.del(key);
    }

    @Override
    public void resetPassword(String email, String password) {
        User user = getByMobileOrEmail(null, email);
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
        }
        // 生成新的盐
        String salt = RandomStringUtils.randomAlphanumeric(20);
        password = DigestUtils.sha1Hex(password + salt);
        user.setPassword(password);
        user.setSalt(salt);
        baseMapper.updateById(user);
    }

    @Override
    public User getByEmail(String email) {
        User user = getByMobileOrEmail(null, email);
        return user;
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

    /**
     * 删除 token
     *
     * @param userId
     */
    public void deleteToken(Long userId) {
        String key = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_ID_TOKEN_KEY, userId);
        String token = redisUtils.hget(key, AccessSource.PC.name());
        String tokenKey = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_TOKEN_ID_KEY, token);
        redisUtils.del(tokenKey);
    }

    /**
     * 生成 token
     * @param userId
     * @return
     */
    public String generateToken(Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String tokenIdKey = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_TOKEN_ID_KEY, token);
        redisUtils.set(tokenIdKey, userId, RedisConstant.ONE_WEEK);
        String idTokenKey = RedisKeyConstant.getKey(RedisKeyConstant.SESSION_ID_TOKEN_KEY, userId);
        redisUtils.hset(idTokenKey, AccessSource.PC.name(), token, RedisConstant.ONE_WEEK);
        return token;
    }
}
