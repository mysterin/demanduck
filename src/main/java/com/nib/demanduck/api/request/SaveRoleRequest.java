package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/29 18:07
 */
@Data
public class SaveRoleRequest extends BaseRequest {
    @NotNull(message = "用户id不能为空")
    private Long userId;
    @NotBlank(message = "角色不能为空")
    private String role;
    private Long companyId;
    private Long projectId;
}
