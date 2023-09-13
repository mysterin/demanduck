package com.nib.demanduck.request.mission;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/12 15:37
 */
@Data
public class UpdateMissionRequest extends CreateMissionRequest {
    @NotNull(message = "任务 id 不能为空")
    private Long missionId;

}
