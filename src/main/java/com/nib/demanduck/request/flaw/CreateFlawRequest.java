package com.nib.demanduck.request.flaw;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/12 15:37
 */
@Data
public class CreateFlawRequest {
    @NotNull(message = "项目 id 不能为空")
    private Long projectId;
    @NotBlank(message = "任务标题不能为空")
    private String title;
    private String priority;
    private String state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String content;
}
