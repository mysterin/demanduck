package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description 评论类型
 * @date 2023/9/4 19:00
 */
public enum CommentType {
    DEMAND_COMMENT,
    MISSION_COMMENT,
    FLAW_COMMENT,
    ;

    /**
     * 字符串转枚举
     */
    public static CommentType parse(String name) {
        for (CommentType type : CommentType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
