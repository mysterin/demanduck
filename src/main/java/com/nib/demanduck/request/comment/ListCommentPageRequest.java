package com.nib.demanduck.request.comment;

import com.nib.demanduck.constant.CommentType;
import com.nib.demanduck.request.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/9/19 16:41
 */
@Data
public class ListCommentPageRequest extends PageRequest {
    @NotNull(message = "项目id不能为空")
    private Long projectId;
    @NotNull(message = "业务id不能为空")
    private Long businessId;
    @NotNull(message = "评论类型不能为空")
    private CommentType type;
}
