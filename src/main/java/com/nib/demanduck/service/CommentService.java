package com.nib.demanduck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nib.demanduck.response.comment.CommentDTO;

/**
 * <p>
 * 评论回复表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface CommentService extends IService<Comment> {
    void saveComment(Comment comment, String content);

    IPage<CommentDTO> listComment(Comment comment, long pageNo, long pageSize);
    IPage<CommentDTO> listReplyComment(Comment comment, long pageNo, long pageSize);
}
