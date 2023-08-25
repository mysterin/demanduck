package com.nib.demanduck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author linxiaobin
 * @Description redis 锁配置
 * @date 2023/8/25 10:10
 */
@Configuration
public class RedisLockConfig {

    /**
     * 分布式锁
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "DISTRIBUTED:LOCK", 10 * 1000L);
    }
}
