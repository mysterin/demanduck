package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.mapper.DemandMapper;
import com.nib.demanduck.service.DemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 需求表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {

    @Autowired
    private ProjectService projectService;

    @Override
    public Demand saveDemand(Demand demand) {
        if (Objects.isNull(demand.getId())) {
            Long companyId = projectService.getCompanyIdById(demand.getProjectId());
            demand.setCompanyId(companyId);
            this.save(demand);
        } else {
            Demand dbDemand = getById(demand.getId());
            demand.setCompanyId(dbDemand.getCompanyId());
            demand.setProjectId(dbDemand.getProjectId());
            this.updateById(demand);
        }
        return demand;
    }

    @Override
    public IPage<Demand> listDemandByProjectId(Long projectId, long pageNo, long pageSize) {
        Page<Demand> page = new Page<>(pageNo, pageSize);
        // 按 id 倒序排序，分页查询，需要返回总数
        return this.lambdaQuery().eq(Demand::getProjectId, projectId).orderByDesc(Demand::getId).page(page);
    }
}
