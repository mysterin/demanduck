package com.nib.demanduck.request.demand;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/13 17:11
 */
@Data
public class UpdateDemandRequest extends CreateDemandRequest {
    @NotNull(message = "需求 id 不能为空")
    private Long demandId;
}
