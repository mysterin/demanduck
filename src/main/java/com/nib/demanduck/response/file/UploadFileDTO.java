package com.nib.demanduck.response.file;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 * @Description
 * @date 2023/11/12 11:14
 */
@Data
@Accessors(chain = true)
public class UploadFileDTO {
    private String url;
}
