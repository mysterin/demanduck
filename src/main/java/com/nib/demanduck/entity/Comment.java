package com.nib.demanduck.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论回复表
 * </p>
 *
 * @author linxiaobin
 * @since 2023-09-15
 */
@Getter
@Setter
@TableName("nib_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 公司 ID
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 项目 ID
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 类型, DEMAND_COMMENT=需求下评论, MISSION_COMMENT=任务下评论, FLAW_COMMENT=缺陷下评论
     */
    @TableField("type")
    private String type;

    /**
     * 被回复的业务 ID(需求 ID、任务 ID、缺陷 ID)
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 根评论 ID
     */
    @TableField("root_comment_id")
    private Long rootCommentId;

    /**
     * 被回复的具体评论 ID
     */
    @TableField("replied_comment_id")
    private Long repliedCommentId;

    /**
     * 是否有子评论，0=没有，1=有
     */
    @TableField("child_comment")
    private Boolean childComment;

    /**
     * 是否删除，0=未删除，1=已删除
     */
      @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    /**
     * 创建人
     */
      @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新人
     */
      @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
      @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
