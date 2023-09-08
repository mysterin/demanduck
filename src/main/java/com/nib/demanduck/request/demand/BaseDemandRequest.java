package com.nib.demanduck.request.demand;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/7 15:38
 */
@Data
public class BaseDemandRequest {
    @NotNull(message = "需求id不能为空")
    private Long demandId;
}
