package com.nib.demanduck.annotation;

import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;

import java.lang.annotation.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 21:11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserPermission {
    /**
     * 角色
     * @return
     */
    RoleEnum[] value();

    /**
     * 实体类型
     * @return
     */
    EntityType entityType() default EntityType.PROJECT;
}
