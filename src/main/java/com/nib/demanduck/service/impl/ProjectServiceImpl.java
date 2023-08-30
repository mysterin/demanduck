package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Project;
import com.nib.demanduck.mapper.ProjectMapper;
import com.nib.demanduck.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Long getCompanyIdById(Long projectId) {
        return lambdaQuery().eq(Project::getId, projectId).oneOpt().map(Project::getCompanyId).orElse(null);
    }

    @Override
    public List<Project> listProjectByCompanyId(Long companyId) {
        // 查询公司下的项目
        return lambdaQuery().eq(Project::getCompanyId, companyId).list();
    }
}