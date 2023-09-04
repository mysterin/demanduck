package com.nib.demanduck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.entity.Demand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 需求表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface DemandService extends IService<Demand> {

    Demand saveDemand(Demand demand);
    IPage<Demand> listDemandByProjectId(Long projectId, long pageNo, long pageSize);
}
