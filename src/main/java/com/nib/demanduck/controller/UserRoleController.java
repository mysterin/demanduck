package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.SaveUserRoleRequest;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.constant.UserRoleConstant;
import com.nib.demanduck.constant.UserRoleEnum;
import com.nib.demanduck.entity.UserRole;
import com.nib.demanduck.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 保存用户系统角色接口
     * @param request
     * @return
     */
    @PostMapping("/saveSystemUserRole")
    @UserPermission(UserRoleEnum.SYSTEM_ADMIN)
    public Response saveSystemUserRole(@RequestBody @Validated SaveUserRoleRequest request) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(request, userRole);
        userRole.setLevel(UserRoleConstant.SYSTEM);
        userRoleService.saveUserRole(userRole);
        return Response.success();
    }

    /**
     * 保存用户公司角色接口
     */
    @PostMapping("/saveCompanyUserRole")
    @UserPermission(UserRoleEnum.COMPANY_ADMIN)
    public Response saveCompanyUserRole(@RequestBody @Validated SaveUserRoleRequest request) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(request, userRole);
        userRole.setLevel(UserRoleConstant.COMPANY);
        userRoleService.saveUserRole(userRole);
        return Response.success();
    }

    /**
     * 保存用户项目角色接口
     */
    @PostMapping("/saveProjectUserRole")
    @UserPermission(UserRoleEnum.PROJECT_ADMIN)
    public Response saveProjectUserRole(@RequestBody @Validated SaveUserRoleRequest request) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(request, userRole);
        userRole.setLevel(UserRoleConstant.PROJECT);
        userRoleService.saveUserRole(userRole);
        return Response.success();
    }
}
