package com.nib.demanduck;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemanduckApplication {

    public static void main(String[] args) {
        log.info("启动参数: {}", JSON.toJSONString(args));
        SpringApplication.run(DemanduckApplication.class, args);
    }

}
