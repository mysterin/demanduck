package com.nib.demanduck.request.flaw;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/7 15:39
 */
@Data
public class BaseFlawRequest {
    @NotNull(message = "缺陷id不能为空")
    private Long flawId;
}
