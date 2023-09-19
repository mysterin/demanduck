package com.nib.demanduck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nib.demanduck.constant.CommentType;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.entity.*;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.mapper.CommentMapper;
import com.nib.demanduck.response.comment.CommentDTO;
import com.nib.demanduck.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Function;

/**
 * <p>
 * 评论回复表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private DemandService demandService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private FlawService flawService;
    @Autowired
    private ContentService contentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(Comment comment, String content) {
        populateProperty(comment);
        if (Objects.isNull(comment.getId())) {
            this.save(comment);
        } else {
            this.updateById(comment);
        }
        // 保存内容
        contentService.saveContent(comment.getId(), EntityType.COMMENT, content);
    }

    @Override
    public IPage<CommentDTO> listComment(Comment comment, long pageNo, long pageSize) {
        return lambdaQuery().eq(Comment::getProjectId, comment.getProjectId())
                .eq(Comment::getBusinessId, comment.getBusinessId())
                .eq(Comment::getType, comment.getType())
                .orderByDesc(Comment::getId)
                .page(new Page<>(pageNo, pageSize))
                .convert(transferDTOAndFillContent());
    }

    private Function<Comment, CommentDTO> transferDTOAndFillContent() {
        return comment -> {
            Content content = contentService.getByBusinessId(comment.getId(), EntityType.COMMENT);
            CommentDTO commentDTO = new CommentDTO()
                    .setCommentId(comment.getId())
                    .setCompanyId(comment.getCompanyId())
                    .setProjectId(comment.getProjectId())
                    .setType(comment.getType())
                    .setBusinessId(comment.getBusinessId())
                    .setRootCommentId(comment.getRootCommentId())
                    .setRepliedCommentId(comment.getRepliedCommentId())
                    .setChildComment(comment.getChildComment())
                    .setContent(content.getContent());
            return commentDTO;
        };
    }

    @Override
    public IPage<CommentDTO> listReplyComment(Comment comment, long pageNo, long pageSize) {
        return lambdaQuery().eq(Comment::getProjectId, comment.getProjectId())
                .eq(Comment::getRootCommentId, comment.getRootCommentId())
                .orderByDesc(Comment::getId)
                .page(new Page<>(pageNo, pageSize))
                .convert(transferDTOAndFillContent());
    }

    /**
     * 根据评论类型, 设置公司 ID 和项目 ID
     * 填充根评论 id
     * @param comment
     */
    public void populateProperty(Comment comment) {
        CommentType commentType = CommentType.parse(comment.getType());
        // 设置公司 ID 和项目 ID
        Long companyId;
        Long projectId;
        switch (commentType) {
            case DEMAND_COMMENT:
                Demand demand = demandService.getById(comment.getBusinessId());
                companyId = demand.getCompanyId();
                projectId = demand.getProjectId();
                break;
            case MISSION_COMMENT:
                Mission mission = missionService.getById(comment.getBusinessId());
                companyId = mission.getCompanyId();
                projectId = mission.getProjectId();
                break;
            case FLAW_COMMENT:
                Flaw flaw = flawService.getById(comment.getBusinessId());
                companyId = flaw.getCompanyId();
                projectId = flaw.getProjectId();
                break;
            default:
                throw new ServiceException(ErrorCode.INVALID_PARAM);
        }
        comment.setCompanyId(companyId);
        comment.setProjectId(projectId);

        // 是回复评论
        if (Objects.nonNull(comment.getRepliedCommentId())) {
            Comment repliedComment = getById(comment.getRepliedCommentId());
            // 父评论设置为有子评论
            if (!repliedComment.getChildComment()) {
                repliedComment.setChildComment(true);
                updateById(repliedComment);
            }

            // 设置根评论 id
            Long rootCommentId = Objects.isNull(repliedComment.getRootCommentId()) ? repliedComment.getId() : repliedComment.getRootCommentId();
            comment.setRootCommentId(rootCommentId);
        }
    }

}
