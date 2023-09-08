package com.nib.demanduck.request.role;

import com.nib.demanduck.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 14:10
 */
@Data
public class RoleRequest extends BaseRequest {

    /**
     * 角色 id
     */
    @NotNull(message = "角色不能为空")
    private Long roleId;
}
