package com.nib.demanduck.request.term;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author linxiaobin
 * @Description
 * @date 2024/5/8 17:30
 */
@Data
public class FindTermsRequest {
    @NotBlank(message = "关键词不能为空")
    private String key;
}
