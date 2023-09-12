package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.mapper.MissionMapper;
import com.nib.demanduck.service.MissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

    @Override
    public void saveMission(Mission mission) {
        // id 为空，新增
        if (mission.getId() == null) {
            this.save(mission);
        } else {
            this.updateById(mission);
        }
    }

    @Override
    public IPage<Mission> listMissionByDemandId(Long demandId, long pageNo, long pageSize) {
        // 分页查询
        Page<Mission> page = new Page<>(pageNo, pageSize);
        return this.lambdaQuery().eq(Mission::getDemandId, demandId).orderByDesc(Mission::getId).page(page);
    }
}
