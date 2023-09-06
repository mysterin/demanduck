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
public class UpdateProjectRequest extends CreateProjectRequest {
    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;
}
