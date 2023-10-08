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
public class EmailValidCodeRequest extends BaseRequest {
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 场景
     */
    @NotBlank(message = "场景不能为空")
    private String scene;
}
