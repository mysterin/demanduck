package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description 需要传 companyId 的请求
 * @date 2023/8/30 14:10
 */
@Data
public class PageRequest extends BaseRequest {

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Integer pageNo;
    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private Integer pageSize;
}
