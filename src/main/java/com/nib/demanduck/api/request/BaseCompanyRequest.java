package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 14:10
 */
@Data
public class BaseCompanyRequest {

    /**
     * 公司id
     */
    @NotNull(message = "公司id不能为空")
    private Long companyId;
}
