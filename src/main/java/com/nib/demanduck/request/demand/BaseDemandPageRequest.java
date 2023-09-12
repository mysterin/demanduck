package com.nib.demanduck.request.demand;

import com.nib.demanduck.request.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/7 15:38
 */
@Data
public class BaseDemandPageRequest extends PageRequest {
    @NotNull(message = "需求id不能为空")
    private Long demandId;
}
