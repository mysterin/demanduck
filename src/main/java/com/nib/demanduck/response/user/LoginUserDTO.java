package com.nib.demanduck.response.user;

import lombok.Data;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 20:16
 */
@Data
public class LoginUserDTO {
    /**
     * 用户 ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;
    /**
     * token
     */
    private String token;
}
