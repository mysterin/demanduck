package com.nib.demanduck.response.comment;

import com.nib.demanduck.constant.CommentType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/19 16:13
 */
@Data
@Accessors(chain = true)
public class CommentDTO {
    private Long commentId;
    private Long companyId;
    private Long projectId;
    private String type;
    private Long businessId;
    private Long rootCommentId;
    private Long repliedCommentId;
    private Boolean childComment;
    private LocalDateTime createTime;
    private String content;
}
