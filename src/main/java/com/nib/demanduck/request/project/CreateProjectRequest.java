package com.nib.demanduck.request.project;

import com.nib.demanduck.request.BaseRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 13:53
 */
@Data
public class CreateProjectRequest extends BaseRequest {
    /**
     * 公司id
     */
    @NotNull(message = "公司id不能为空")
    private Long companyId;
    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    @Range(min = 3, max = 25, message = "项目名称长度为3-25个字符")
    private String name;
    /**
     * 项目logo
     */
    @NotBlank(message = "项目logo不能为空")
    private String logo;
}
