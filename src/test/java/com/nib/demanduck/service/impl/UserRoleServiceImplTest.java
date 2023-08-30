package com.nib.demanduck.service.impl;

import com.nib.demanduck.DemanduckApplicationTests;
import com.nib.demanduck.constant.UserRoleConstant;
import com.nib.demanduck.entity.UserRole;
import com.nib.demanduck.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 10:13
 */
class UserRoleServiceImplTest extends DemanduckApplicationTests {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    void saveUserRole() {
        // 配置系统管理员
        UserRole userRole = new UserRole();
        userRole.setUserId(86791569637378L);
        userRole.setLevel(UserRoleConstant.SYSTEM);
        userRole.setRole(UserRoleConstant.ADMIN);
        userRoleService.saveUserRole(userRole);
    }
}