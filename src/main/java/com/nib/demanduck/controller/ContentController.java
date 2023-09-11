package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Content;
import com.nib.demanduck.request.demand.BaseDemandRequest;
import com.nib.demanduck.request.flaw.BaseFlawRequest;
import com.nib.demanduck.request.mission.BaseMissionRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 内容表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 查询需求内容
     */
    @PostMapping("/getDemandContent")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response<Content> getDemandContent(@RequestBody @Validated BaseDemandRequest request) {
        return Response.success(contentService.getByBusinessId(request.getDemandId(), EntityType.DEMAND));
    }

    /**
     * 查询任务内容
     */
    @PostMapping("/getMissionContent")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.MISSION)
    public Response<Content> getMissionContent(@RequestBody @Validated BaseMissionRequest request) {
        return Response.success(contentService.getByBusinessId(request.getMissionId(), EntityType.MISSION));
    }

    /**
     * 查询缺陷内容
     */
    @PostMapping("/getFlawContent")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.FLAW)
    public Response<Content> getFlawContent(@RequestBody @Validated BaseFlawRequest request) {
        return Response.success(contentService.getByBusinessId(request.getFlawId(), EntityType.FLAW));
    }
}
