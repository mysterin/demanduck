package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 13:53
 */
@Data
public class SaveCompanyRequest extends BaseRequest {
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    private String name;
    /**
     * 公司logo
     */
    @NotBlank(message = "公司logo不能为空")
    private String logo;
}
