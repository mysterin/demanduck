package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseCompanyRequest;
import com.nib.demanduck.api.request.RoleRequest;
import com.nib.demanduck.api.request.SaveRoleRequest;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Role;
import com.nib.demanduck.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 保存用户系统角色接口
     *
     * @param request
     * @return
     */
    @PostMapping("/saveSystemRole")
    @UserPermission(RoleEnum.SYSTEM_ADMIN)
    public Response saveSystemRole(@RequestBody @Validated SaveRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        role.setCompanyId(null);
        roleService.saveRole(role);
        return Response.success();
    }

    /**
     * 删除用户系统角色接口
     * @param request
     * @return
     */
    @PostMapping("/deleteSystemRole")
    @UserPermission(RoleEnum.SYSTEM_ADMIN)
    public Response deleteSystemRole(@RequestBody @Validated RoleRequest request) {
        roleService.deleteUserRole(request.getRoleId());
        return Response.success();
    }

    /**
     * 查询系统角色列表接口
     */
    @PostMapping("/listSystemRole")
    @UserPermission(RoleEnum.SYSTEM_ADMIN)
    public Response listSystemRole() {
        return Response.success(roleService.listSystemRole());
    }

    /**
     * 保存用户公司角色接口
     * @param request
     * @return
     */
    @PostMapping("/saveCompanyRole")
    @UserPermission(RoleEnum.COMPANY_ADMIN)
    public Response saveCompanyRole(@RequestBody @Validated SaveRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        roleService.saveRole(role);
        return Response.success();
    }

    /**
     * 删除用户公司角色接口
     */
    @PostMapping("/deleteCompanyRole")
    @UserPermission(RoleEnum.COMPANY_ADMIN)
    public Response deleteCompanyRole(@RequestBody @Validated RoleRequest request) {
        roleService.deleteUserRole(request.getRoleId());
        return Response.success();
    }

    /**
     * 查询公司角色列表接口
     */
    @PostMapping("/listCompanyRole")
    @UserPermission(RoleEnum.COMPANY_ADMIN)
    public Response listCompanyRole(@RequestBody @Validated BaseCompanyRequest request) {
        return Response.success(roleService.listCompanyRole(request.getCompanyId()));
    }
}
