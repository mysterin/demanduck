package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description 用户角色
 * @date 2023/8/28 21:15
 */
public enum RoleEnum {
    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(1),
    /**
     * 公司管理员
     */
    COMPANY_ADMIN(1 << 1),
    /**
     * 公司普通成员
     */
    COMPANY_MEMBER(1 << 2),
    /**
     * 项目管理员
     */
    PROJECT_ADMIN(1 << 3),
    /**
     * 项目普通成员
     */
    PROJECT_MEMBER(1 << 4),

    /**
     * 系统管理员和公司管理员
     */
    SYS_COM_ADMIN(SYSTEM_ADMIN.code | COMPANY_ADMIN.code),
    /**
     * 系统管理员和公司管理员和项目管理员
     */
    SYS_COM_PRO_ADMIN(SYSTEM_ADMIN.code | COMPANY_ADMIN.code | PROJECT_ADMIN.code),
    SYS_COM_PRO_MEMBER(SYSTEM_ADMIN.code | COMPANY_ADMIN.code | PROJECT_ADMIN.code | PROJECT_MEMBER.code),
    COM_PRO_ADMIN(COMPANY_ADMIN.code | PROJECT_ADMIN.code),
    ;

    private int code;

    RoleEnum(int code) {
        this.code = code;
    }

    public static RoleEnum getByRole(String role) {
        for (RoleEnum value : RoleEnum.values()) {
            if (value.name().equals(role)) {
                return value;
            }
        }
        return null;
    }

    public boolean isIn(RoleEnum[] roleEnum) {
        for (RoleEnum role : roleEnum) {
            if ((this.code & role.code) > 0) {
                return true;
            }
        }
        return false;
    }

}
