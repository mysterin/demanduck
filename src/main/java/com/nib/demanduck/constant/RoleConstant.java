package com.nib.demanduck.constant;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/29 14:19
 */
public class RoleConstant {

    /**
     * 常用角色组合
     */
    public static final RoleEnum[] SYS_COM_ADMIN = new RoleEnum[]{RoleEnum.SYSTEM_ADMIN, RoleEnum.COMPANY_ADMIN};
    public static final RoleEnum[] SYS_COM_PRO_ADMIN = {RoleEnum.SYSTEM_ADMIN, RoleEnum.COMPANY_ADMIN, RoleEnum.PROJECT_ADMIN};
}
