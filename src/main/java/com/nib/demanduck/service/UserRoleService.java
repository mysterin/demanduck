package com.nib.demanduck.service;

import com.nib.demanduck.constant.UserRoleEnum;
import com.nib.demanduck.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nib.demanduck.exception.ServiceException;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> listUserRole(Long companyId, Long projectId, Long userId);

    Boolean hasPermission(Long companyId, Long projectId, Long userId, UserRoleEnum userRoleEnum);

    void saveUserRole(UserRole userRole);
}
