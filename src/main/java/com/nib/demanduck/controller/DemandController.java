package com.nib.demanduck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.request.demand.BaseDemandRequest;
import com.nib.demanduck.request.demand.CreateDemandRequest;
import com.nib.demanduck.request.demand.UpdateDemandRequest;
import com.nib.demanduck.request.project.BaseProjectPageRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.service.ContentService;
import com.nib.demanduck.service.DemandService;
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
 * 需求表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/demand")
public class DemandController {

    @Autowired
    private DemandService demandService;
    @Autowired
    private ContentService contentService;

    /**
     * 创建需求接口
     */
    @PostMapping("/create")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response create(@RequestBody @Validated CreateDemandRequest request) {
        Demand demand = new Demand();
        BeanUtils.copyProperties(request, demand);
        demandService.saveDemand(demand);
        if (Objects.nonNull(request.getContent())) {
            contentService.saveContent(demand.getId(), EntityType.DEMAND, request.getContent());
        }
        return Response.success();
    }

    /**
     * 更新需求接口
     */
    @PostMapping("/update")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.DEMAND)
    public Response update(@RequestBody @Validated UpdateDemandRequest request) {
        Demand demand = new Demand();
        BeanUtils.copyProperties(request, demand);
        demand.setId(request.getDemandId());
        demandService.saveDemand(demand);
        if (Objects.nonNull(request.getContent())) {
            contentService.saveContent(demand.getId(), EntityType.DEMAND, request.getContent());
        }
        return Response.success();
    }

    /**
     * 查询项目下的需求列表
     */
    @PostMapping("/list")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response<Demand> list(@RequestBody @Validated BaseProjectPageRequest request) {
        IPage<Demand> page = demandService.listDemandByProjectId(request.getProjectId(), request.getPageNo(), request.getPageSize());
        return Response.success(page);
    }

    /**
     * 查询需求详情
     */
    @PostMapping("/get")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.DEMAND)
    public Response<Demand> get(@RequestBody @Validated BaseDemandRequest request) {
        Demand demand = demandService.getById(request.getDemandId());
        return Response.success(demand);
    }
}
