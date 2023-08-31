package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseCompanyRequest;
import com.nib.demanduck.api.request.SaveCompanyRequest;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Company;
import com.nib.demanduck.service.CompanyService;
import com.nib.demanduck.util.ThreadLocalUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 公司表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 保存公司接口
     */
    @PostMapping("/save")
    @UserPermission(RoleEnum.COMPANY_ADMIN)
    public Response save(@RequestBody @Validated SaveCompanyRequest request) {
        Company company = new Company();
        BeanUtils.copyProperties(request, company);
        companyService.saveCompany(company);
        return Response.success();
    }

    /**
     * 查询用户的公司列表
     */
    @PostMapping("/listUserCompany")
    public Response<Company> listUserCompany() {
        Long userId = ThreadLocalUtils.getUserId();
        return Response.success(companyService.listUserCompany(userId));
    }

    /**
     * 删除公司接口
     */
    @PostMapping("/delete")
    @UserPermission(RoleEnum.COMPANY_ADMIN)
    public Response delete(@RequestBody @Validated BaseCompanyRequest request) {
        companyService.deleteCompany(request.getCompanyId());
        return Response.success();
    }

}
