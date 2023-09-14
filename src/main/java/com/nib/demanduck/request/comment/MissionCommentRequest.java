package com.nib.demanduck.request.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/13 17:45
 */
@Data
public class MissionCommentRequest extends CreateCommentRequest {
    @NotNull(message = "任务id不能为空")
    private Long missionId;
}
