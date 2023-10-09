package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.request.demand.BaseDemandPageRequest;
import com.nib.demanduck.request.flaw.CreateFlawRequest;
import com.nib.demanduck.request.flaw.UpdateFlawRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.service.ContentService;
import com.nib.demanduck.service.FlawService;
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
 * 缺陷表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/flaw")
public class FlawController {

    @Autowired
    private FlawService flawService;
    @Autowired
    private ContentService contentService;

    /**
     * 创建缺陷接口
     */
    @PostMapping("/create")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN_MEMBER, entityType = EntityType.PROJECT)
    public Response create(@RequestBody @Validated CreateFlawRequest request) {
        Flaw flaw = new Flaw();
        BeanUtils.copyProperties(request, flaw);
        flawService.saveFlaw(flaw);
        if (Objects.nonNull(request.getContent())) {
            contentService.saveContent(flaw.getId(), EntityType.FLAW, request.getContent());
        }
        return Response.success();
    }

    /**
     * 更新缺陷接口
     */
    @PostMapping("/update")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN_MEMBER, entityType = EntityType.FLAW)
    public Response update(@RequestBody @Validated UpdateFlawRequest request) {
        Flaw flaw = new Flaw();
        BeanUtils.copyProperties(request, flaw);
        flaw.setId(request.getFlawId());
        flawService.saveFlaw(flaw);
        if (Objects.nonNull(request.getContent())) {
            contentService.saveContent(flaw.getId(), EntityType.FLAW, request.getContent());
        }
        return Response.success();
    }

    /**
     * 分页查询需求下缺陷列表
     */
    @PostMapping("/listDemandFlaw")
    @UserPermission(value = RoleEnum.SYS_COM_ADMIN_MEMBER, entityType = EntityType.DEMAND)
    public Response listDemandFlaw(@RequestBody @Validated BaseDemandPageRequest request) {
        return Response.success(flawService.listFlawByDemandId(request.getDemandId(), request.getPageNo(), request.getPageSize()));
    }
}
