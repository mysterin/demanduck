package com.nib.demanduck.request.user;

import com.nib.demanduck.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description 需要传 userId 的请求
 * @date 2023/8/30 14:10
 */
@Data
public class BaseUserRequest extends BaseRequest {

    /**
     * 用户 id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
