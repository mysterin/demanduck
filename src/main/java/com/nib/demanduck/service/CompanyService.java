package com.nib.demanduck.service;

import com.nib.demanduck.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公司表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface CompanyService extends IService<Company> {

    void saveCompany(Company company);
    void deleteCompany(Long companyId);

    List<Company> listUserCompany(Long userId);
}
