package com.nib.demanduck.util;

import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import org.springframework.util.Assert;

/**
 * @author linxiaobin
 * @Description 断言工具类
 * @date 2023/10/12 14:15
 */
public class AssertUtils extends Assert {

    public static void notNull(Object object, ServiceException e) {
        if (object == null) {
            throw e;
        }
    }

    public static void notNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new ServiceException(errorCode);
        }
    }
}
