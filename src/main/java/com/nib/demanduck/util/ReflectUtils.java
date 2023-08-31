package com.nib.demanduck.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/31 11:33
 */
public class ReflectUtils {
    /**
     * 获取对象的属性值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public static Long getLongFieldValue(Object obj, String fieldName) {
        if (Objects.isNull(obj)) {
            return null;
        }
        Object fieldValue = getFieldValue(obj, fieldName);
        if (fieldValue == null) {
            return null;
        }
        return Long.valueOf(fieldValue.toString());
    }
}
