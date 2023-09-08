package com.nib.demanduck.request.assign;

import com.nib.demanduck.request.mission.BaseMissionRequest;
import lombok.Data;

import java.util.List;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/7 15:40
 */
@Data
public class AssignMissionRequest extends BaseMissionRequest {
    private List<Long> userIds;
}
