package com.nib.demanduck.request.mission;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/7 15:39
 */
@Data
public class BaseMissionRequest {
    @NotNull(message = "任务id不能为空")
    private Long missionId;
}
