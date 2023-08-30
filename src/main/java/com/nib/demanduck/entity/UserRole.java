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
 * 用户角色表
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-30 10:05:51
 */
@Getter
@Setter
@TableName("nib_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色所在层次，SYSTEM=系统层次，COMPANY=公司层次，PROJECT=项目层次
     */
    @TableField("level")
    private String level;

    /**
     * 角色, ADMIN=管理员, MEMBER=普通成员
     */
    @TableField("role")
    private String role;

    /**
     * 公司 ID(level=COMPANY)、项目 ID(level=PROJECT)
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 是否删除，0=未删除，1=已删除
     */
      @TableField(value = "deleted", fill = FieldFill.INSERT)
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
