package com.nib.demanduck.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.nib.demanduck.response.config.StsTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author linxiaobin
 * @Description 阿里云 sts 工具类
 * @date 2023/10/18 20:03
 */
@Component
@Slf4j
public class StsUtils {

    @Value("${demanduck.aliyun.accessKeyId")
    private String accessKeyId;
    @Value("${demanduck.aliyun.accessKeySecret")
    private String accessKeySecret;
    @Value("${demanduck.aliyun.oss.endpoint")
    private String endpoint;
    @Value("${demanduck.aliyun.oss.regionId")
    private String regionId;
    @Value("${demanduck.aliyun.oss.bucketName")
    private String bucketName;
    @Value("${demanduck.aliyun.oss.roleArn")
    private String roleArn;
    @Value("${demanduck.aliyun.oss.roleSessionName")
    private String roleSessionName;
    @Value("${demanduck.aliyun.oss.duration}")
    private Long duration;


    /**
     * 获取 sts token
     * @return
     */
    public StsTokenDTO getStsToken() {
        try {
            String policy = "{\n" +
                    "    \"Version\": \"1\", \n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Action\": [\n" +
                    "                \"oss:PutObject\"\n" +
                    "                \"oss:GetObject\"\n" +
                    "            ], \n" +
                    "            \"Resource\": [\n" +
                    "                \"acs:oss:*:*:" + bucketName + "/*\" \n" +
                    "            ], \n" +
                    "            \"Effect\": \"Allow\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(duration);
            AssumeRoleResponse acsResponse = client.getAcsResponse(request);
            StsTokenDTO stsTokenDTO = new StsTokenDTO()
                    .setRegion(regionId)
                    .setAccessKeyId(acsResponse.getCredentials().getAccessKeyId())
                    .setAccessKeySecret(acsResponse.getCredentials().getAccessKeySecret())
                    .setStsToken(acsResponse.getCredentials().getSecurityToken())
                    .setExpiration(acsResponse.getCredentials().getExpiration())
                    .setBucket(bucketName);
            return stsTokenDTO;
        } catch (Exception e) {
            log.error("获取sts token失败", e);
        }
        return null;
    }
}
