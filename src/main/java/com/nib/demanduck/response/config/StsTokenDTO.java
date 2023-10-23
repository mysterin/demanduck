package com.nib.demanduck.response.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/10/23 17:55
 */
@Data
@Accessors(chain = true)
public class StsTokenDTO {
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String stsToken;
    private String stsTokenExpireTime;
    private String bucket;
}
