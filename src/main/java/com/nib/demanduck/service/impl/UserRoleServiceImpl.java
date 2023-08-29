package com.nib.demanduck.service.impl;

import com.nib.demanduck.constant.UserRoleConstant;
import com.nib.demanduck.constant.UserRoleEnum;
import com.nib.demanduck.entity.UserRole;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.mapper.UserRoleMapper;
import com.nib.demanduck.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRole> listUserRole(Long companyId, Long projectId, Long userId) {
        Objects.requireNonNull(userId);
        List<UserRole> list = new ArrayList<>();
        // 系统下角色
        List<UserRole> systemRoleList = lambdaQuery()
                .eq(UserRole::getLevel, UserRoleConstant.SYSTEM)
                .eq(UserRole::getUserId, userId).list();
        list.addAll(systemRoleList);

        // 公司下角色
        if (Objects.nonNull(companyId) && Objects.isNull(projectId)) {
            List<UserRole> companyRoleList = lambdaQuery()
                    .eq(UserRole::getLevel, UserRoleConstant.COMPANY)
                    .eq(UserRole::getBusinessId, companyId)
                    .eq(UserRole::getUserId, userId).list();
            list.addAll(companyRoleList);
        }
        // 项目下角色
        if (Objects.nonNull(projectId)) {
            List<UserRole> projectRoleList = lambdaQuery()
                    .eq(UserRole::getLevel, UserRoleConstant.PROJECT)
                    .eq(UserRole::getBusinessId, projectId)
                    .eq(UserRole::getUserId, userId).list();
            list.addAll(projectRoleList);
        }
        return list;
    }

    @Override
    public Boolean hasPermission(Long companyId, Long projectId, Long userId, UserRoleEnum userRoleEnum) {
        List<UserRoleEnum> list = listUserRole(companyId, projectId, userId).stream()
                .map(userRole -> UserRoleEnum.getByLevelAndRole(userRole.getLevel(), userRole.getRole()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return list.stream().anyMatch(userRole -> userRole.hasPermission(userRoleEnum));
    }

    @Override
    public void saveUserRole(UserRole userRole) {
        // 根据 userId level businessId 查询, null 值或空字符串需要过滤掉, 返回一条记录, 允许为空
        UserRole dbUserRole = lambdaQuery()
                .eq(Objects.nonNull(userRole.getUserId()), UserRole::getUserId, userRole.getUserId())
                .eq(Objects.nonNull(userRole.getLevel()), UserRole::getLevel, userRole.getLevel())
                .eq(Objects.nonNull(userRole.getBusinessId()), UserRole::getBusinessId, userRole.getBusinessId())
                .one();
        // 如果存在, 则更新, 否则插入
        if (Objects.nonNull(dbUserRole)) {
            userRole.setId(dbUserRole.getId());
            updateById(userRole);
        } else {
            save(userRole);
        }

    }
}
