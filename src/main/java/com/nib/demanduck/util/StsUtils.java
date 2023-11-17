package com.nib.demanduck.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.properties.AliyunProperties;
import com.nib.demanduck.response.config.StsTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;

/**
 * @author linxiaobin
 * @Description 阿里云 sts 工具类
 * @date 2023/10/18 20:03
 */
@Component
@Slf4j
public class StsUtils {

    @Autowired
    private AliyunProperties aliyunProperties;


    /**
     * 获取 sts token
     * https://help.aliyun.com/zh/oss/developer-reference/use-temporary-access-credentials-provided-by-sts-to-access-oss?spm=a2c4g.11186623.0.i7
     * @return
     */
    public StsTokenDTO getOssStsToken() {
        try {
            AliyunProperties.Oss oss = aliyunProperties.getOss();
            String policy = "{\n" +
                    "    \"Version\": \"1\", \n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Action\": [\n" +
                    "                \"oss:PutObject\",\n" +
                    "                \"oss:GetObject\"\n" +
                    "            ], \n" +
                    "            \"Resource\": [\n" +
                    "                \"acs:oss:*:*:" + oss.getBucketName() + "/*\" \n" +
                    "            ], \n" +
                    "            \"Effect\": \"Allow\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            DefaultProfile.addEndpoint("", "Sts", aliyunProperties.getSts().getEndpoint());
            DefaultProfile profile = DefaultProfile.getProfile(oss.getRegionId(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
            DefaultAcsClient client = new DefaultAcsClient(profile);
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(oss.getRoleArn());
            request.setRoleSessionName(oss.getRoleSessionName());
            request.setPolicy(policy);
            request.setDurationSeconds(oss.getDuration());
            AssumeRoleResponse acsResponse = client.getAcsResponse(request);
            StsTokenDTO stsTokenDTO = new StsTokenDTO()
                    .setRegion(oss.getRegion())
                    .setAccessKeyId(acsResponse.getCredentials().getAccessKeyId())
                    .setAccessKeySecret(acsResponse.getCredentials().getAccessKeySecret())
                    .setStsToken(acsResponse.getCredentials().getSecurityToken())
                    .setExpiration(acsResponse.getCredentials().getExpiration())
                    .setBucket(oss.getBucketName());
            return stsTokenDTO;
        } catch (Exception e) {
            log.error("获取sts token失败", e);
            throw new ServiceException(ErrorCode.STS_TOKEN_ERROR);
        }
    }

    /**
     * 生成签名 URL
     * @param objectName
     * https://help.aliyun.com/zh/oss/developer-reference/authorize-access-1?spm=a2c4g.11186623.0.i23#p-bpg-g75-6jc
     * @return
     */
    public String generatePresignedUrl(String objectName) {
        OSS ossClient = null;
        try {
            AliyunProperties.Oss oss = aliyunProperties.getOss();
            StsTokenDTO ossStsToken = getOssStsToken();
            ossClient = new OSSClientBuilder().build(oss.getEndpoint(), ossStsToken.getAccessKeyId(), ossStsToken.getAccessKeySecret(), ossStsToken.getStsToken());
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(oss.getBucketName(), objectName, expiration);
            log.debug("生成签名 URL 成功, url: {}", url);
            return url.getPath();
        } catch (Exception e) {
            log.error("生成签名 URL 失败", e);
            throw new ServiceException(ErrorCode.SYSTEM_ERROR);
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
