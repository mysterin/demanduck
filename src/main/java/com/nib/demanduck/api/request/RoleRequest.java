package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 14:10
 */
@Data
public class RoleRequest {

    /**
     * 角色 id
     */
    @NotNull(message = "角色不能为空")
    private Long roleId;
}
