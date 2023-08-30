package com.nib.demanduck.constant;

import java.util.Map;
import java.util.Objects;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 18:05
 */
public class RedisKeyConstant {
    public static final String USER_ROLE_KEY = "USER:ROLE:{companyId}:{projectId}:{userId}";

    public static String getKey(String key, Map<String, Object> params) {
        if (Objects.isNull(params)) {
            return key;
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String value = Objects.isNull(entry.getValue()) ? "" : entry.getValue().toString();
            key = key.replace("{" + entry.getKey() + "}", value);
        }
        return key;
    }
}
