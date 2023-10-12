package com.nib.demanduck.request.role;

import com.nib.demanduck.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/29 18:07
 */
@Data
public class SaveRoleRequest extends BaseRequest {
    @NotNull(message = "邮箱不能为空")
    private List<String> emailList;
    @NotBlank(message = "角色不能为空")
    private String role;
    private Long companyId;
}
