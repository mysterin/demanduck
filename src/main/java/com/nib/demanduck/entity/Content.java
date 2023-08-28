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
 * 内容表
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Getter
@Setter
@TableName("nib_content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 业务记录 ID
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 类型, DEMAND=需求, MISSION=任务, COMMENT=评论
     */
    @TableField("type")
    private String type;

    /**
     * 文本内容
     */
    @TableField("content")
    private String content;

    /**
     * 是否删除，0=未删除，1=已删除
     */
    @TableField("deleted")
    @TableLogic
    private Byte deleted;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;

    /**
     * 更新人
     */
    @TableField("update_user")
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
