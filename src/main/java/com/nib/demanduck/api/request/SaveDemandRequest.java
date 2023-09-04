package com.nib.demanduck.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 13:53
 */
@Data
public class SaveDemandRequest extends BaseProjectRequest {
    /**
     * 需求 id
     */
    private Long id;
    /**
     * 需求标题
     */
    @NotBlank(message = "需求标题不能为空")
    private String title;
    private String priority;
    private String state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String content;
}
