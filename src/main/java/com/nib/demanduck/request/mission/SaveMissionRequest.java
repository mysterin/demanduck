package com.nib.demanduck.request.mission;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/12 15:37
 */
@Data
public class SaveMissionRequest {
    private Long id;
    private Long projectId;
    private String title;
    private String priority;
    private String state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String content;
}
