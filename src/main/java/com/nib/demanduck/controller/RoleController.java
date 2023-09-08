package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.request.company.BaseCompanyRequest;
import com.nib.demanduck.request.project.BaseProjectRequest;
import com.nib.demanduck.request.role.RoleRequest;
import com.nib.demanduck.request.role.SaveRoleRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.constant.EntityType;
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
    @UserPermission(value = RoleEnum.SYSTEM_ADMIN, entityType = EntityType.NO_ENTITY)
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
    @UserPermission(value = RoleEnum.SYSTEM_ADMIN, entityType = EntityType.NO_ENTITY)
    public Response deleteSystemRole(@RequestBody @Validated RoleRequest request) {
        roleService.deleteRole(request.getRoleId());
        return Response.success();
    }

    /**
     * 查询系统角色列表接口
     */
    @PostMapping("/listSystemRole")
    @UserPermission(value = RoleEnum.SYSTEM_ADMIN, entityType = EntityType.NO_ENTITY)
    public Response listSystemRole() {
        return Response.success(roleService.listSystemRole());
    }

    /**
     * 保存用户公司角色接口
     * @param request
     * @return
     */
    @PostMapping("/saveCompanyRole")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN, entityType = EntityType.COMPANY)
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
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN, entityType = EntityType.COMPANY)
    public Response deleteCompanyRole(@RequestBody @Validated RoleRequest request) {
        roleService.deleteRole(request.getRoleId());
        return Response.success();
    }

    /**
     * 查询公司角色列表接口
     */
    @PostMapping("/listCompanyRole")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN, entityType = EntityType.COMPANY)
    public Response listCompanyRole(@RequestBody @Validated BaseCompanyRequest request) {
        return Response.success(roleService.listCompanyRole(request.getCompanyId()));
    }

    /**
     * 保存项目角色接口
     */
    @PostMapping("/saveProjectRole")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_ADMIN, entityType = EntityType.PROJECT)
    public Response saveProjectRole(@RequestBody @Validated SaveRoleRequest request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        roleService.saveRole(role);
        return Response.success();
    }

    /**
     * 删除项目角色接口
     */
    @PostMapping("/deleteProjectRole")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_ADMIN, entityType = EntityType.PROJECT)
    public Response deleteProjectRole(@RequestBody @Validated RoleRequest request) {
        roleService.deleteRole(request.getRoleId());
        return Response.success();
    }

    /**
     * 查询项目角色列表接口
     */
    @PostMapping("/listProjectRole")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_ADMIN, entityType = EntityType.PROJECT)
    public Response listProjectRole(@RequestBody @Validated BaseProjectRequest request) {
        return Response.success(roleService.listProjectRole(request.getCompanyId(), request.getProjectId()));
    }
}
