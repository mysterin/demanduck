package com.nib.demanduck.service;

import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface RoleService extends IService<Role> {

    List<Role> listUserRole(Long companyId, Long userId);

    Boolean hasPermission(Long companyId, Long userId, RoleEnum[] roleArray);

    void saveRole(Role role);
    void saveRole(Long companyId, List<String> emailList, String role);
    void deleteRole(Long userRoleId);

    List<Role> listCompanyRoleByUserId(Long userId);
    List<Role> listSystemRole();
    List<Role> listCompanyRole(Long companyId);
}
