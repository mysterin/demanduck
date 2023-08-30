package com.nib.demanduck.exception;

import lombok.Getter;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 18:01
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(1000, "系统错误"),
    INVALID_PARAM(1001, "参数错误"),
    INVALID_TOKEN(1002, "无效的 token"),
    INVALID_USER(1003, "无效的用户"),
    INVALID_PASSWORD(1004, "密码错误"),
    USER_EXIST(1005, "用户已存在"),
    USER_NOT_EXIST(1006, "用户不存在"),
    PASSWORD_ERROR(1007, "密码错误"),
    USER_NOT_LOGIN(1008, "用户未登录"),
    USER_PERMISSION_ERROR(1009, "用户无权限"),
    ;
    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
