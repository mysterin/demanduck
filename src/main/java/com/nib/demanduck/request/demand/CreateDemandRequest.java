package com.nib.demanduck.request.demand;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 13:53
 */
@Data
public class CreateDemandRequest {
    @NotBlank(message = "项目 id 不能为空")
    private Long projectId;
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
