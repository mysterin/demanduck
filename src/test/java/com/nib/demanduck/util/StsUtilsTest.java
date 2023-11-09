package com.nib.demanduck.util;

import com.nib.demanduck.DemanduckApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/11/9 18:59
 */
@Slf4j
class StsUtilsTest extends DemanduckApplicationTests {

    @Autowired
    private StsUtils stsUtils;

    @Test
    void generatePresignedUrl() {
        String objectName = "project/logo/212635B0D883E4C853C17A222171D4D6.png";
        String url = stsUtils.generatePresignedUrl(objectName);
        log.debug("url: {}", url);
    }
}