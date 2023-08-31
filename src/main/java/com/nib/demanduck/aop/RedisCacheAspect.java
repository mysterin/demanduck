package com.nib.demanduck.aop;

import com.nib.demanduck.annotation.RedisCache;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import com.nib.demanduck.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author linxiaobin
 * @Description 拦截方法读缓存
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
        Object result = null;
        Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Class returnType = methodSignature.getReturnType();
            // 获取 key
            String key = realCacheKey(joinPoint, redisCache, methodSignature);

            // 读缓存
            log.debug("读取缓存, key={}", key);
            String type = redisCache.type();
            String val;
            if (RedisConstant.STRING.equals(type)) {
                val = redisUtils.get(key);
                if (Objects.nonNull(val)) {
                    // 返回是 list
                    if (returnType.isAssignableFrom(List.class)) {
                        return redisUtils.parseArray(val, redisCache.returnListType());
                    } else {
                        return redisUtils.parseObject(val, returnType);
                    }
                }
            } else {
                log.error("不支持的缓存类型, key={}, type={}", key, type);
            }

            // 执行方法
            log.debug("缓存不存在, 执行方法, key={}", key);
            result = joinPoint.proceed();

            // 写缓存
            setCache(result, key, redisCache.ttl(), type);
        }
        return result;
    }

    private void setCache(Object result, String key, long ttl, String type) {
        if (RedisConstant.STRING.equals(type)) {
            redisUtils.set(key, result, ttl);
        } else {
            log.error("不支持的缓存类型, key={}, type={}", key, type);
        }
    }

    private String realCacheKey(ProceedingJoinPoint joinPoint, RedisCache redisCache, MethodSignature methodSignature) {
        String key = redisCache.key();
        String[] indexNames = redisCache.indexNames();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        // 获取 key，替换对应变量的值
        Map<String, Object> map = new HashMap<>();
        Object[] indexValues = new Object[indexNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            map.put(parameterName, args[i]);
        }
        for (int i = 0; i < indexNames.length; i++) {
            indexValues[i] = map.get(indexNames[i]);
        }
        key = RedisKeyConstant.getKey(key, indexValues);
        return key;
    }

}
