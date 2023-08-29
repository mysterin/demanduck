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
 * 缺陷表
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-29 06:21:42
 */
@Getter
@Setter
@TableName("nib_flaw")
public class Flaw implements Serializable {

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
     * 缺陷标题
     */
    @TableField("title")
    private String title;

    /**
     * 优先级, HIGH=高, MIDDLE=中, LOW=低
     */
    @TableField("priority")
    private String priority;

    /**
     * 状态, OPEN=新缺陷, PROCESSING=处理中, PROCESSED=处理完成, CLOSE=关闭, REFUSE=已拒绝
     */
    @TableField("status")
    private String status;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 是否删除，0=未删除，1=已删除
     */
    @TableField("deleted")
    @TableLogic
    private Byte deleted;

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
