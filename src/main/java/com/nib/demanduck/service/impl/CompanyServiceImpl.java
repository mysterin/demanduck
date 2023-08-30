package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Company;
import com.nib.demanduck.mapper.CompanyMapper;
import com.nib.demanduck.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
}
