package com.nib.demanduck.annotation;

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
public @interface RedisCache {
    /**
     * 支持格式化
     * @return
     */
    String key();
    long ttl() default 60L;

    /**
     * 缓存类型
     * STRING, LIST, SET, ZSET, HASH
     * @return
     */
    String type() default "STRING";

    /**
     * 对应缓存索引属性名
     * @return
     */
    String[] indexNames() default {};

    /**
     * 返回 list 类型时，指定 list 中的类型
     * @return
     */
    Class returnListType() default Object.class;

}
