package com.nib.demanduck.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/29 10:11
 */
@Slf4j
public class ThreadLocalUtils {

    private static ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }

    public static Long getUserId() {
        return userIdThreadLocal.get();
    }

    public static void removeUserId() {
        userIdThreadLocal.remove();
    }

}

