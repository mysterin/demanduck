package com.nib.demanduck.constant;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 18:05
 */
public class RedisKeyConstant {
    /**
     * 用户角色缓存
     * {0} companyId
     * {1} userId
     */
    public static final String USER_ROLE_KEY = "USER:ROLE:{0}:{1}";
    /**
     * 用户 token:id 缓存
     * {0} token
     */
    public static final String SESSION_TOKEN_ID_KEY = "SESSION:TOKEN:{0}";
    /**
     * 用户 id:token 缓存
     * {0} userId
     */
    public static final String SESSION_ID_TOKEN_KEY = "SESSION:ID:{0}";

    public static String getKey(String key, Object...params) {
        if (Objects.isNull(params)) {
            return key;
        }
        // 转成字符串，不然 long 类型格式化会带上逗号
        String[] paramStr = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            paramStr[i] = String.valueOf(params[i]);
        }
        return MessageFormat.format(key, paramStr);
    }
}
