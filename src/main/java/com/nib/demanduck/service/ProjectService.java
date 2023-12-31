package com.nib.demanduck.service;

import com.nib.demanduck.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface ProjectService extends IService<Project> {

    void saveProject(Project project);
    Long getCompanyIdById(Long projectId);
    List<Project> listProjectByCompanyId(Long companyId);
}
