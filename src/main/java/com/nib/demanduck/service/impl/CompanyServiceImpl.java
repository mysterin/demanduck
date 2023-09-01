package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Company;
import com.nib.demanduck.entity.Role;
import com.nib.demanduck.mapper.CompanyMapper;
import com.nib.demanduck.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private RoleService roleService;

    @Override
    public void saveCompany(Company company) {
        // 根据公司名称查询公司信息
        Company dbCompany = lambdaQuery().eq(Company::getName, company.getName()).one();
        if (dbCompany != null) {
            // 如果公司存在，更新公司信息
            company.setId(dbCompany.getId());
            updateById(company);
        } else {
            // 如果公司不存在，新增公司信息
            save(company);
        }
    }

    @Override
    public void deleteCompany(Long companyId) {
        // 删除公司信息
        Objects.requireNonNull(companyId);
        removeById(companyId);
    }

    @Override
    public List<Company> listUserCompany(Long userId) {
        Objects.requireNonNull(userId);
        List<Role> roles = roleService.listCompanyRoleByUserId(userId);
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> companyIds = roles.stream().map(Role::getCompanyId).collect(Collectors.toList());
        return lambdaQuery().in(Company::getId, companyIds).list();
    }
}
