package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseCompanyRequest;
import com.nib.demanduck.api.request.BaseProjectRequest;
import com.nib.demanduck.api.request.SaveProjectRequest;
import com.nib.demanduck.api.response.Response;
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
     * 保存项目接口
     */
    @PostMapping("/save")
    @UserPermission(RoleEnum.SYS_COM_PRO_ADMIN)
    public Response save(@RequestBody @Validated SaveProjectRequest request) {
        Project project = new Project();
        BeanUtils.copyProperties(request, project);
        projectService.saveProject(project);
        return Response.success();
    }

    /**
     * 查询公司的项目列表
     */
    @PostMapping("/list")
    @UserPermission(RoleEnum.SYS_COM_PRO_MEMBER)
    public Response<List<Project>> list(@RequestBody @Validated BaseCompanyRequest request) {
        return Response.success(projectService.listProjectByCompanyId(request.getCompanyId()));
    }

    /**
     * 删除项目接口
     */
    @PostMapping("/delete")
    @UserPermission(RoleEnum.SYS_COM_PRO_ADMIN)
    public Response delete(@RequestBody @Validated BaseProjectRequest request) {
        projectService.removeById(request.getProjectId());
        return Response.success();
    }

}
