package com.nib.demanduck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseProjectPageRequest;
import com.nib.demanduck.api.request.BaseProjectRequest;
import com.nib.demanduck.api.request.SaveDemandRequest;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Demand;
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
     * 保存需求接口
     */
    @PostMapping("/save")
    @UserPermission(RoleEnum.SYS_COM_PRO_ADMIN)
    public Response save(@RequestBody @Validated SaveDemandRequest request) {
        Demand demand = new Demand();
        BeanUtils.copyProperties(request, demand);
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
    @UserPermission(RoleEnum.SYS_COM_PRO_MEMBER)
    public Response<Demand> list(@RequestBody @Validated BaseProjectPageRequest request) {
        IPage<Demand> page = demandService.listDemandByProjectId(request.getProjectId(), request.getPageNo(), request.getPageSize());
        return Response.success(page);
    }
}
