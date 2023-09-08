package com.nib.demanduck.request.user;

import com.nib.demanduck.request.BaseRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 19:41
 */
@Data
public class LoginUserRequest extends BaseRequest {
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度必须在8-20位之间")
    private String password;
}
