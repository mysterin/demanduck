package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description 用户角色 priority越小权限越大
 * @date 2023/8/28 21:15
 */
public enum RoleEnum {
    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(RoleConstant.SYSTEM_ADMIN, 1),
    /**
     * 公司管理员
     */
    COMPANY_ADMIN(RoleConstant.COMPANY_ADMIN, 10),
    /**
     * 公司普通成员
     */
    COMPANY_MEMBER(RoleConstant.COMPANY_MEMBER, 11),
    ;
    private String role;
    private Integer priority;

    RoleEnum(String role, Integer priority) {
        this.role = role;
        this.priority = priority;
    }

    public String getRole() {
        return role;
    }

    public Integer getPriority() {
        return priority;
    }

    public static RoleEnum getByRole(String role) {
        for (RoleEnum value : RoleEnum.values()) {
            if (value.getRole().equals(role)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否有权限
     * @param roleEnum
     * @return
     */
    public boolean hasPermission(RoleEnum roleEnum) {
        return this.getPriority() <= roleEnum.getPriority();
    }
}
