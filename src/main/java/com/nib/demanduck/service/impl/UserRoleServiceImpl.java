package com.nib.demanduck.service.impl;

import com.nib.demanduck.annotation.RedisCache;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.constant.UserRoleConstant;
import com.nib.demanduck.constant.UserRoleEnum;
import com.nib.demanduck.entity.Project;
import com.nib.demanduck.entity.UserRole;
import com.nib.demanduck.mapper.UserRoleMapper;
import com.nib.demanduck.service.ProjectService;
import com.nib.demanduck.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.util.ApplicationUtils;
import com.nib.demanduck.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MapBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ApplicationUtils applicationUtils;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserRole getByUserIdAndBusinessId(Long userId, Long businessId) {
        return lambdaQuery().eq(UserRole::getUserId, userId).eq(UserRole::getBusinessId, businessId).one();
    }

    @Override
    @RedisCache(key = RedisKeyConstant.USER_ROLE_KEY, ttl = RedisConstant.HALF_HOUR)
    public List<UserRole> listUserRole(Long companyId, Long projectId, Long userId) {
        Objects.requireNonNull(userId);
        List<UserRole> list = new ArrayList<>();
        // 系统下角色
        List<UserRole> systemRoleList = lambdaQuery()
                .eq(UserRole::getLevel, UserRoleConstant.SYSTEM)
                .eq(UserRole::getUserId, userId).list();
        list.addAll(systemRoleList);

        // 公司下角色
        if (Objects.nonNull(companyId)) {
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
        List<UserRoleEnum> list = applicationUtils.getBean(getClass())
                .listUserRole(companyId, projectId, userId)
                .stream()
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

        // 清除缓存
        deleteCache(userRole);

    }

    @Override
    public void deleteUserRole(Long userRoleId) {
        UserRole userRole = getById(userRoleId);
        if (Objects.isNull(userRole)) {
            return;
        }
        if (isProjectRole(userRole)) {
            removeById(userRoleId);
        } else if (isCompanyRole(userRole)) {
            List<Project> projects = projectService.listProjectByCompanyId(userRole.getBusinessId());
            projects.stream().forEach(project -> {
                UserRole projectUserRole = getByUserIdAndBusinessId(userRole.getUserId(), project.getId());
                if (Objects.nonNull(projectUserRole)) {
                    deleteUserRole(projectUserRole.getId());
                }
            });
        } else if (isSystemRole(userRole)) {
            // 删除用户所有角色
            List<UserRole> list = lambdaQuery().eq(UserRole::getUserId, userRole.getUserId()).list();
            list.stream().forEach(o -> {
                deleteUserRole(o.getId());
            });
        }

        deleteCache(userRole);
    }

    private void deleteCache(UserRole userRole) {
        Map<String, Object> map = new MapBuilder<MapBuilder, String, Object>()
                .put("companyId", getCompanyId(userRole))
                .put("projectId", getProjectId(userRole))
                .put("userId", userRole.getUserId())
                .get();
        redisUtils.del(RedisKeyConstant.USER_ROLE_KEY, map);
    }

    public boolean isSystemRole(UserRole userRole) {
        return Objects.equals(userRole.getLevel(), UserRoleConstant.SYSTEM);
    }

    public boolean isCompanyRole(UserRole userRole) {
        return Objects.equals(userRole.getLevel(), UserRoleConstant.COMPANY);
    }

    public boolean isProjectRole(UserRole userRole) {
        return Objects.equals(userRole.getLevel(), UserRoleConstant.PROJECT);
    }

    public Long getCompanyId(UserRole userRole) {
        if (isCompanyRole(userRole)) {
            return userRole.getBusinessId();
        }
        if (isProjectRole(userRole)) {
            return projectService.getCompanyIdById(userRole.getBusinessId());
        }
        return null;
    }

    public Long getProjectId(UserRole userRole) {
        if (isProjectRole(userRole)) {
            return userRole.getBusinessId();
        }
        return null;
    }
}
