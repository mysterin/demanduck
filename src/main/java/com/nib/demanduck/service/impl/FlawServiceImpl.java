package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.mapper.FlawMapper;
import com.nib.demanduck.service.FlawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 缺陷表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class FlawServiceImpl extends ServiceImpl<FlawMapper, Flaw> implements FlawService {

    @Autowired
    private ProjectService projectService;

    @Override
    public void saveFlaw(Flaw flaw) {
        // id 是否空，是就插入
        if (Objects.isNull(flaw.getId())) {
            Long companyId = projectService.getCompanyIdById(flaw.getProjectId());
            flaw.setCompanyId(companyId);
            save(flaw);
        } else {
            Flaw dbFlaw = getById(flaw.getId());
            flaw.setCompanyId(dbFlaw.getCompanyId());
            flaw.setProjectId(dbFlaw.getProjectId());
            updateById(flaw);
        }
    }

    @Override
    public IPage<Flaw> listFlawByDemandId(Long demandId, long pageNo, long pageSize) {
        IPage<Flaw> page = new Page<>(pageNo, pageSize);
        return lambdaQuery().eq(Flaw::getDemandId, demandId).orderByDesc(Flaw::getId).page(page);
    }
}
