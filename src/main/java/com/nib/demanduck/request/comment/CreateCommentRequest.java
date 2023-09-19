package com.nib.demanduck.request.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/13 17:44
 */
@Data
public class CreateCommentRequest {
    @NotBlank(message = "评论内容不能为空")
    private String content;
    /**
     * 回复评论的id
     */
    private Long repliedCommentId;
}
