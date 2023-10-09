package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.request.company.BaseCompanyRequest;
import com.nib.demanduck.request.project.BaseProjectRequest;
import com.nib.demanduck.request.role.RoleRequest;
import com.nib.demanduck.request.role.SaveRoleRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Role;
import com.nib.demanduck.response.role.RoleDTO;
import com.nib.demanduck.service.RoleService;
import com.nib.demanduck.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;

    /**
     * 保存系统角色接口
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
     * 删除系统角色接口
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
        List<Role> roles = roleService.listCompanyRole(request.getCompanyId());
        List<RoleDTO> list = roles.stream().map(role -> {
            User user = userService.getById(role.getUserId());
            RoleDTO roleDTO = new RoleDTO()
                    .setRoleId(role.getId())
                    .setCompanyId(role.getCompanyId())
                    .setUserId(user.getId())
                    .setRole(role.getRole())
                    .setEmail(user.getEmail())
                    .setUsername(user.getUsername());
            return roleDTO;
        }).collect(Collectors.toList());
        return Response.success(list);
    }

}
