package com.nib.demanduck.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/11/17 20:52
 */
@Configuration
@ConfigurationProperties("demanduck.aliyun")
@Data
public class AliyunProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private Sts sts;
    private Oss oss;


    @Data
    public class Sts {
        private String endpoint;
    }

    @Data
    public class Oss {
        private String regionId;
        private String endpoint;
        private String bucketName;
        private String roleArn;
        private String roleSessionName;
        private Long duration;
        private String region;
    }
}
