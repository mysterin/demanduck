package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.entity.AssignUser;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.mapper.AssignUserMapper;
import com.nib.demanduck.service.AssignUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.service.DemandService;
import com.nib.demanduck.service.FlawService;
import com.nib.demanduck.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 分配用户表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class AssignUserServiceImpl extends ServiceImpl<AssignUserMapper, AssignUser> implements AssignUserService {

    @Autowired
    private DemandService demandService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private FlawService flawService;

    /**
     * 分配需求
     * @param demandId
     * @param userIds
     */
    @Override
    public void assignDemand(Long demandId, List<Long> userIds) {
        Demand demand = demandService.getById(demandId);
        Objects.requireNonNull(demand, "需求不存在");
        assignUser(demand.getCompanyId(), demand.getProjectId(), demand.getId(), EntityType.DEMAND, userIds);
    }

    /**
     * 分配任务
     * @param missionId
     * @param userIds
     */
    @Override
    public void assignMission(Long missionId, List<Long> userIds) {
        Mission mission = missionService.getById(missionId);
        Objects.requireNonNull(mission, "任务不存在");
        assignUser(mission.getCompanyId(), mission.getProjectId(), mission.getId(), EntityType.MISSION, userIds);
    }

    /**
     * 分配缺陷
     * @param flawId
     * @param userIds
     */
    @Override
    public void assignFlaw(Long flawId, List<Long> userIds) {
        Flaw flaw = flawService.getById(flawId);
        Objects.requireNonNull(flaw, "缺陷不存在");
        assignUser(flaw.getCompanyId(), flaw.getProjectId(), flaw.getId(), EntityType.FLAW, userIds);
    }

    public void assignUser(Long companyId, Long projectId, Long businessId, EntityType entityType, List<Long> userIds) {
        // 先删除非userIds的数据，再插入userIds的数据
        AbstractWrapper<AssignUser, SFunction<AssignUser, ?>, LambdaQueryWrapper<AssignUser>> wrapper = lambdaQuery().eq(AssignUser::getBusinessId, businessId)
                .eq(AssignUser::getType, entityType)
                .notIn(AssignUser::getUserId, userIds)
                .getWrapper();
        remove(wrapper);

        userIds.stream().forEach(userId -> {
            AssignUser assignUser = lambdaQuery().eq(AssignUser::getBusinessId, businessId)
                    .eq(AssignUser::getType, entityType)
                    .eq(AssignUser::getUserId, userId)
                    .one();
            if (assignUser == null) {
                assignUser = new AssignUser();
                assignUser.setCompanyId(companyId);
                assignUser.setProjectId(projectId);
                assignUser.setBusinessId(businessId);
                assignUser.setType(entityType.name());
                assignUser.setUserId(userId);
                save(assignUser);
            }
        });

    }
}
