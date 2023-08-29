package com.nib.demanduck.annotation;

import com.nib.demanduck.constant.UserRoleEnum;

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
    UserRoleEnum value();
}
