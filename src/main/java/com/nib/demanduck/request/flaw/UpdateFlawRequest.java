package com.nib.demanduck.request.flaw;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/12 15:37
 */
@Data
public class UpdateFlawRequest extends CreateFlawRequest {
    @NotNull(message = "缺陷 id 不能为空")
    private Long flawId;
}
