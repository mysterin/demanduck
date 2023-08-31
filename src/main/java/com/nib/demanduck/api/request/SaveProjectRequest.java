package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 13:53
 */
@Data
public class SaveProjectRequest {
    /**
     * 项目id
     */
    private Long id;
    /**
     * 公司id
     */
    @NotNull(message = "公司id不能为空")
    private Long companyId;
    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    private String name;
    /**
     * 项目logo
     */
    @NotBlank(message = "项目logo不能为空")
    private String logo;
}
