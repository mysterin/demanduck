package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.request.assign.AssignDemandRequest;
import com.nib.demanduck.request.assign.AssignFlawRequest;
import com.nib.demanduck.request.assign.AssignMissionRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.service.AssignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分配用户表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/assignUser")
public class AssignUserController {

    @Autowired
    private AssignUserService assignUserService;


    /**
     * 分配需求给用户
     */
    @PostMapping("/assignDemand")
    @UserPermission(value = RoleEnum.PRO_ADMIN_MEMBER, entityType = EntityType.DEMAND)
    public Response assignDemand(@RequestBody @Validated AssignDemandRequest request) {
        assignUserService.assignDemand(request.getDemandId(), request.getUserIds());
        return Response.success();
    }

    /**
     * 分配任务给用户
     */
    @PostMapping("/assignMission")
    @UserPermission(value = RoleEnum.PRO_ADMIN_MEMBER, entityType = EntityType.MISSION)
    public Response assignMission(@RequestBody @Validated AssignMissionRequest request) {
        assignUserService.assignMission(request.getMissionId(), request.getUserIds());
        return Response.success();
    }

    /**
     * 分配缺陷给用户
     */
    @PostMapping("/assignFlaw")
    @UserPermission(value = RoleEnum.PRO_ADMIN_MEMBER, entityType = EntityType.FLAW)
    public Response assignFlaw(@RequestBody @Validated AssignFlawRequest request) {
        assignUserService.assignFlaw(request.getFlawId(), request.getUserIds());
        return Response.success();
    }

}
