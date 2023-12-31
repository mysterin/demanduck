package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.annotation.RedisCache;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Role;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.mapper.RoleMapper;
import com.nib.demanduck.service.RoleService;
import com.nib.demanduck.service.UserService;
import com.nib.demanduck.util.ApplicationUtils;
import com.nib.demanduck.util.AssertUtils;
import com.nib.demanduck.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private ApplicationUtils applicationUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserService userService;

    /**
     * 查询用户角色, 包括系统角色和公司角色和项目角色
     * @param companyId
     * @param userId
     * @return
     */
    @Override
    @RedisCache(key = RedisKeyConstant.USER_ROLE_KEY, ttl = RedisConstant.HALF_HOUR,
            indexNames = {"companyId", "userId"}, returnListType = Role.class)
    public List<Role> listUserRole(Long companyId, Long userId) {
        Objects.requireNonNull(userId);
        List<Role> list = new ArrayList<>();
        // 系统下角色
        List<Role> systemRoleList = lambdaQuery()
                .eq(Role::getRole, RoleEnum.SYSTEM_ADMIN)
                .eq(Role::getUserId, userId).list();
        list.addAll(systemRoleList);

        // 公司下角色
        if (Objects.nonNull(companyId)) {
            List<Role> companyRoleList = lambdaQuery()
                    .eq(Role::getCompanyId, companyId)
                    .eq(Role::getUserId, userId)
                    .in(Role::getRole, RoleEnum.COMPANY_ADMIN, RoleEnum.COMPANY_MEMBER)
                    .list();
            list.addAll(companyRoleList);
        }

        return list;
    }

    /**
     * 用户是否有权限
     * @param companyId
     * @param userId
     * @param roleArray
     * @return
     */
    @Override
    public Boolean hasPermission(Long companyId, Long userId, RoleEnum[] roleArray) {
        List<RoleEnum> list = applicationUtils.getBean(getClass())
                .listUserRole(companyId, userId)
                .stream()
                .map(userRole -> RoleEnum.getByRole(userRole.getRole()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return list.stream().anyMatch(userRole -> userRole.isIn(roleArray));
    }

    @Override
    public void saveRole(Role role) {
        Assert.notNull(role, "userRole can not be null");
        Assert.notNull(role.getUserId(), "userId can not be null");
        Assert.notNull(role.getRole(), "role can not be null");
        if (isCompanyRole(role)) {
            Assert.notNull(role.getCompanyId(), "companyId can not be null");
        }
        Role dbRole = lambdaQuery()
                .eq(Role::getUserId, role.getUserId())
                .eq(isSystemRole(role), Role::getRole, role.getRole())
                .eq(isCompanyRole(role), Role::getCompanyId, role.getCompanyId())
                .one();
        // 如果存在, 则更新, 否则插入
        if (Objects.nonNull(dbRole)) {
            role.setId(dbRole.getId());
            updateById(role);
        } else {
            save(role);
        }

        // 清除缓存
        deleteCache(role);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Long companyId, List<String> emailList, String role) {
        for (String email : emailList) {
            User user = userService.getByEmail(email);
            AssertUtils.notNull(user, ErrorCode.INVALID_USER.msg("无效邮箱: " + email));
            Role userRole = new Role();
            userRole.setUserId(user.getId());
            userRole.setCompanyId(companyId);
            userRole.setRole(role);
            saveRole(userRole);
        }
    }

    @Override
    public void deleteRole(Long userRoleId) {
        Role role = getById(userRoleId);
        if (Objects.isNull(role)) {
            return;
        }
        removeById(userRoleId);
        deleteCache(role);
    }

    /**
     * 查询用户所有公司角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> listCompanyRoleByUserId(Long userId) {
        return lambdaQuery()
                .eq(Role::getUserId, userId)
                .in(Role::getRole, RoleEnum.COMPANY_ADMIN, RoleEnum.COMPANY_MEMBER)
                .list();
    }

    /**
     * 查询用户系统角色
     * @return
     */
    @Override
    public List<Role> listSystemRole() {
        return lambdaQuery()
                .eq(Role::getRole, RoleEnum.SYSTEM_ADMIN)
                .list();
    }

    /**
     * 查询公司下所有角色
     * @param companyId
     * @return
     */
    @Override
    public List<Role> listCompanyRole(Long companyId) {
        Assert.notNull(companyId, "companyId can not be null");
        return lambdaQuery()
                .eq(Role::getCompanyId, companyId)
                .in(Role::getRole, RoleEnum.COMPANY_ADMIN, RoleEnum.COMPANY_MEMBER)
                .list();
    }

    public void deleteCache(Role role) {
        redisUtils.del(RedisKeyConstant.USER_ROLE_KEY, role.getCompanyId(), role.getUserId());
    }

    public boolean isSystemRole(Role role) {
        RoleEnum roleEnum = RoleEnum.getByRole(role.getRole());
        return Objects.equals(roleEnum, RoleEnum.SYSTEM_ADMIN);
    }

    public boolean isCompanyRole(Role role) {
        RoleEnum roleEnum = RoleEnum.getByRole(role.getRole());
        return Objects.equals(roleEnum, RoleEnum.COMPANY_ADMIN)
                || Objects.equals(roleEnum, RoleEnum.COMPANY_MEMBER);
    }

}
