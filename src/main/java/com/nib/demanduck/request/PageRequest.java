package com.nib.demanduck.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Long pageNo;
    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    @Size(max = 100, message = "数量不能超过100")
    private Long pageSize;
}
