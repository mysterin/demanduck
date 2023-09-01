package com.nib.demanduck.service.impl;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.DemanduckApplicationTests;
import com.nib.demanduck.constant.RoleConstant;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Role;
import com.nib.demanduck.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 10:13
 */
class RoleServiceImplTest extends DemanduckApplicationTests {

    @Autowired
    private RoleService roleService;

    @Test
    void saveUserRole() {
        // 配置系统管理员
        Role role = new Role();
        role.setUserId(86791569637378L);
        role.setRole(RoleEnum.SYSTEM_ADMIN.name());
        roleService.saveRole(role);
    }
    
    @Test
    public void test() {
//        String val = "[{\"createTime\":\"2023-08-31 11:58:01\",\"deleted\":0,\"id\":87757765214209,\"role\":\"SYSTEM_ADMIN\",\"updateTime\":\"2023-08-31 11:58:01\",\"userId\":86791569637378}]";
//        List list = JSON.parseObject(val, List.class);
//        System.out.println(list);
        Role role = new Role();
        role.setId(1L);
        role.setRole(RoleEnum.SYSTEM_ADMIN.name());
        List<Role> list = new ArrayList<>();
        list.add(role);
        String val = JSON.toJSONString(list);
        System.out.println(val);
    }
}