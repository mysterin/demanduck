package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description 用户角色 priority越小权限越大
 * @date 2023/8/28 21:15
 */
public enum UserRoleEnum {
    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(UserRoleConstant.SYSTEM, UserRoleConstant.ADMIN, 1),
    /**
     * 系统普通成员
     */
    SYSTEM_MEMBER(UserRoleConstant.SYSTEM, UserRoleConstant.MEMBER, 2),
    /**
     * 公司管理员
     */
    COMPANY_ADMIN(UserRoleConstant.COMPANY, UserRoleConstant.ADMIN, 3),
    /**
     * 公司普通成员
     */
    COMPANY_MEMBER(UserRoleConstant.COMPANY, UserRoleConstant.MEMBER, 4),
    /**
     * 项目管理员
     */
    PROJECT_ADMIN(UserRoleConstant.PROJECT, UserRoleConstant.ADMIN, 5),
    /**
     * 项目普通成员
     */
    PROJECT_MEMBER(UserRoleConstant.PROJECT, UserRoleConstant.MEMBER, 6),
    ;
    private String level;
    private String role;
    private Integer priority;

    UserRoleEnum(String level, String role, Integer priority) {
        this.level = level;
        this.role = role;
        this.priority = priority;
    }

    public String getLevel() {
        return level;
    }

    public String getRole() {
        return role;
    }

    public Integer getPriority() {
        return priority;
    }

    public static UserRoleEnum getByLevelAndRole(String level, String role) {
        for (UserRoleEnum value : UserRoleEnum.values()) {
            if (value.getLevel().equals(level) && value.getRole().equals(role)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否有权限
     * @param userRoleEnum
     * @return
     */
    public boolean hasPermission(UserRoleEnum userRoleEnum) {
        return this.getPriority() <= userRoleEnum.getPriority();
    }
}
