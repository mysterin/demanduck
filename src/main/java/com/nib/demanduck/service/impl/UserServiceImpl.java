package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.User;
import com.nib.demanduck.mapper.UserMapper;
import com.nib.demanduck.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
