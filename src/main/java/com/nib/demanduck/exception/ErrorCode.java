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
    USER_PASSWORD_ERROR(1007, "账户或密码错误"),
    USER_NOT_LOGIN(1008, "用户未登录"),
    USER_PERMISSION_ERROR(1009, "用户无权限"),
    VALID_CODE_ERROR(1010, "验证码错误"),
    STS_TOKEN_ERROR(1011, "获取 sts token 失败"),
    NOT_INIT_TERM_ASSOCIATION(1012, "词条关联未初始化"),
    ;
    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ErrorCode msg(String msg) {
        this.msg = msg;
        return this;
    }
}
