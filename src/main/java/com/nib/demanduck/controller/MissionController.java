package com.nib.demanduck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.request.demand.BaseDemandPageRequest;
import com.nib.demanduck.request.demand.BaseDemandRequest;
import com.nib.demanduck.request.mission.BaseMissionRequest;
import com.nib.demanduck.request.mission.SaveMissionRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.service.ContentService;
import com.nib.demanduck.service.MissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;
    @Autowired
    private ContentService contentService;

    /**
     * 保存任务接口
     */
    @PostMapping("/save")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response save(@RequestBody @Validated SaveMissionRequest request) {
        Mission mission = new Mission();
        BeanUtils.copyProperties(request, mission);
        missionService.saveMission(mission);
        if (Objects.nonNull(request.getContent())) {
            contentService.saveContent(mission.getId(), EntityType.MISSION, request.getContent());
        }
        return Response.success();
    }

    /**
     * 分页查询需求下的任务列表
     */
    @PostMapping("/listDemandMission")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.DEMAND)
    public Response<Mission> listDemandMission(@RequestBody @Validated BaseDemandPageRequest request) {
        IPage<Mission> page = missionService.listMissionByDemandId(request.getDemandId(), request.getPageNo(), request.getPageSize());
        return Response.success(page);
    }

    /**
     * 查询任务信息
     * @param request
     * @return
     */
    @PostMapping("/get")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.MISSION)
    public Response<Mission> get(@RequestBody @Validated BaseMissionRequest request) {
        Mission mission = missionService.getById(request.getMissionId());
        return Response.success(mission);
    }

}
