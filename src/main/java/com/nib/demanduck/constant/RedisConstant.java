package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 20:14
 */
public class RedisConstant {
    /**
     * 过期时间
     */
    public static final long TEN_SECOND = 10;
    public static final long ONE_MINUTE = 60;
    public static final long TEN_MINUTE = 60 * 10;
    public static final long HALF_HOUR = 60 * 30;
    public static final long ONE_HOUR = 60 * 60;
    public static final long ONE_DAY = 60 * 60 * 24;
    public static final long ONE_WEEK = 60 * 60 * 24 * 7;
    public static final long ONE_MONTH = 60 * 60 * 24 * 30;

    /**
     * 占位符
     */
    public static final String PLACEHOLDER = "#";

    /**
     * 缓存类型
     */
    public static final String STRING = "STRING";
    public static final String LIST = "LIST";
    public static final String SET = "SET";
    public static final String ZSET = "ZSET";
    public static final String HASH = "HASH";


}
