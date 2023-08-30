package com.nib.demanduck.aop;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.annotation.RedisCache;
import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseCompanyRequest;
import com.nib.demanduck.api.request.BaseProjectRequest;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.UserRoleService;
import com.nib.demanduck.util.RedisUtils;
import com.nib.demanduck.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author linxiaobin
 * @Description 拦截判断是否要缓存
 * @date 2023/8/30 14:17
 */
@Aspect
@Component
@Slf4j
public class RedisCacheAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Around("@annotation(redisCache)")
    public Object around(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {
        String key = redisCache.key();
        long ttl = redisCache.ttl();
        String type = redisCache.type();
        Object result = null;
        Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = methodSignature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            Class returnType = methodSignature.getReturnType();

            // 获取 key，替换对应变量的值
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                String parameterName = parameterNames[i];
                map.put(parameterName, args[i]);
            }
            key = RedisKeyConstant.getKey(key, map);

            // 读缓存
            log.debug("读取缓存, key={}", key);
            String val;
            if (RedisConstant.STRING.equals(type)) {
                val = redisUtils.get(key);
                if (Objects.nonNull(val)) {
                    return RedisConstant.PLACEHOLDER.equals(val) ? null : JSON.parseObject(val, returnType);
                }
            } else {
                log.error("不支持的缓存类型, key={}, type={}", key, type);
            }

            // 执行方法
            log.debug("缓存不存在, 执行方法, key={}", key);
            result = joinPoint.proceed();

            // 设置缓存
            val = Objects.isNull(result) ? RedisConstant.PLACEHOLDER : JSON.toJSONString(result);
            if (RedisConstant.STRING.equals(type)) {
                redisUtils.set(key, val, ttl);
            } else {
                log.error("不支持的缓存类型, key={}, type={}", key, type);
            }
        }
        return result;
    }

}
