package com.nib.demanduck.service.impl;

import com.nib.demanduck.constant.CommentType;
import com.nib.demanduck.entity.Comment;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.mapper.CommentMapper;
import com.nib.demanduck.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nib.demanduck.service.DemandService;
import com.nib.demanduck.service.FlawService;
import com.nib.demanduck.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    @Override
    public void saveComment(Comment comment) {
        populateProperty(comment);
        if (Objects.isNull(comment.getId())) {
            this.save(comment);
        } else {
            this.updateById(comment);
        }
    }

    /**
     * 根据评论类型, 设置公司 ID 和项目 ID
     * 填充根评论 id
     * @param comment
     */
    public void populateProperty(Comment comment) {
        CommentType commentType = CommentType.parse(comment.getType());
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

        if (Objects.nonNull(comment.getRepliedCommentId())) {
            Comment repliedComment = getById(comment.getRepliedCommentId());

        }
    }

}
