package com.nib.demanduck.controller;

import com.nib.demanduck.response.Response;
import com.nib.demanduck.response.config.StsTokenDTO;
import com.nib.demanduck.util.StsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linxiaobin
 * @Description 配置相关接口
 * @date 2023/10/23 17:55
 */
@RestController
@Slf4j
public class ConfigController {

    @Autowired
    private StsUtils stsUtils;

    /**
     * 获取阿里云 sts token
     */
    @PostMapping("/getStsToken")
    public Response<StsTokenDTO> getStsToken() {
        return null;
    }
}
