package com.nib.demanduck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.entity.Mission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface MissionService extends IService<Mission> {

    void saveMission(Mission mission);

    IPage<Mission> listMissionByDemandId(Long demandId, long pageNo, long pageSize);
}
