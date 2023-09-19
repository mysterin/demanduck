package com.nib.demanduck.controller;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.CommentType;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Comment;
import com.nib.demanduck.request.comment.DemandCommentRequest;
import com.nib.demanduck.request.comment.ListCommentPageRequest;
import com.nib.demanduck.request.comment.ListReplyCommentPageRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.response.comment.CommentDTO;
import com.nib.demanduck.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评论回复表 前端控制器
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 需求下评论
     */
    @PostMapping("/demandComment")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.DEMAND)
    public Response demandComment(@RequestBody @Validated DemandCommentRequest request) {
        Comment comment = new Comment();
        comment.setType(CommentType.DEMAND_COMMENT.name());
        comment.setBusinessId(request.getDemandId());
        comment.setRepliedCommentId(request.getRepliedCommentId());
        commentService.saveComment(comment, request.getContent());
        return Response.success();
    }

    /**
     * 任务下评论
     */
    @PostMapping("/missionComment")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.MISSION)
    public Response missionComment(@RequestBody @Validated DemandCommentRequest request) {
        Comment comment = new Comment();
        comment.setType(CommentType.MISSION_COMMENT.name());
        comment.setBusinessId(request.getDemandId());
        comment.setRepliedCommentId(request.getRepliedCommentId());
        commentService.saveComment(comment, request.getContent());
        return Response.success();
    }

    /**
     * 缺陷下评论
     */
    @PostMapping("/flawComment")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.FLAW)
    public Response flawComment(@RequestBody @Validated DemandCommentRequest request) {
        Comment comment = new Comment();
        comment.setType(CommentType.FLAW_COMMENT.name());
        comment.setBusinessId(request.getDemandId());
        comment.setRepliedCommentId(request.getRepliedCommentId());
        commentService.saveComment(comment, request.getContent());
        return Response.success();
    }

    /**
     * 分页查询评论列表
     */
    @PostMapping("/listComment")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response<CommentDTO> listComment(@RequestBody @Validated ListCommentPageRequest request) {
        Comment comment = new Comment();
        comment.setProjectId(request.getProjectId());
        comment.setBusinessId(request.getBusinessId());
        comment.setType(request.getType().name());
        return Response.success(commentService.listComment(comment, request.getPageNo(), request.getPageSize()));
    }

    /**
     * 分页查询回复的评论列表
     */
    @PostMapping("/listReplyComment")
    @UserPermission(value = RoleEnum.SYS_COM_PRO_MEMBER, entityType = EntityType.PROJECT)
    public Response<CommentDTO> listReplyComment(@RequestBody @Validated ListReplyCommentPageRequest request) {
        Comment comment = new Comment();
        comment.setProjectId(request.getProjectId());
        comment.setRootCommentId(request.getRootCommentId());
        return Response.success(commentService.listReplyComment(comment, request.getPageNo(), request.getPageSize()));
    }


}
