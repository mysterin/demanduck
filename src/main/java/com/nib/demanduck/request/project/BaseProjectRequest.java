package com.nib.demanduck.request.project;

import com.nib.demanduck.request.company.BaseCompanyRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 14:10
 */
@Data
public class BaseProjectRequest extends BaseCompanyRequest {
    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;
}
