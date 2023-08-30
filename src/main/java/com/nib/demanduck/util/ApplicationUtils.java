package com.nib.demanduck.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 17:29
 */
@Component
public class ApplicationUtils {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取代理对象
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
