package com.nib.demanduck.response.role;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/10/9 18:25
 */
@Data
@Accessors(chain = true)
public class RoleDTO {
    private Long roleId;
    private Long companyId;
    private Long userId;
    private String username;
    private String email;
    private String role;
}
