package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.request.company.BaseCompanyRequest;
import com.nib.demanduck.request.project.BaseProjectRequest;
import com.nib.demanduck.request.project.CreateProjectRequest;
import com.nib.demanduck.request.project.UpdateProjectRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Project;
import com.nib.demanduck.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 创建项目接口
     */
    @PostMapping("/create")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN, entityType = EntityType.COMPANY)
    public Response create(@RequestBody @Validated CreateProjectRequest request) {
        Project project = new Project();
        BeanUtils.copyProperties(request, project);
        projectService.saveProject(project);
        return Response.success();
    }

    /**
     * 更新项目接口
     */
    @PostMapping("/update")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_ADMIN, entityType = EntityType.PROJECT)
    public Response update(@RequestBody @Validated UpdateProjectRequest request) {
        Project project = new Project();
        BeanUtils.copyProperties(request, project);
        project.setId(request.getProjectId());
        projectService.saveProject(project);
        return Response.success();
    }

    /**
     * 查询公司的项目列表
     */
    @PostMapping("/list")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.COMPANY)
    public Response<List<Project>> list(@RequestBody @Validated BaseCompanyRequest request) {
        return Response.success(projectService.listProjectByCompanyId(request.getCompanyId()));
    }

    /**
     * 删除项目接口
     */
    @PostMapping("/delete")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_ADMIN, entityType = EntityType.PROJECT)
    public Response delete(@RequestBody @Validated BaseProjectRequest request) {
        projectService.removeById(request.getProjectId());
        return Response.success();
    }

}
