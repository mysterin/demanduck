package com.nib.demanduck.util;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.constant.RedisConstant;
import com.nib.demanduck.constant.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * @author linxiaobin
 * @Description redis 工具类
 * @date 2023/8/24 16:33
 */
@Component
@Slf4j
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    /**
     * 设置缓存, null 会设置占位符
     * @param key
     * @param value
     * @param ttl 过期时间，单位秒
     */
    public void set(String key, Object value, long ttl) {
        redisTemplate.opsForValue().set(key, toJson(value), ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置 hash 缓存
     * @param key
     * @param hashKey
     * @param value
     * @param ttl
     */
    public void put(String key, String hashKey, Object value, long ttl) {
        redisTemplate.opsForHash().put(key, hashKey, JSON.toJSONString(value));
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置 list 缓存
     * @param key
     * @param value
     * @param ttl
     */
    public void leftPush(String key, Object value, long ttl) {
        redisTemplate.opsForList().leftPush(key, JSON.toJSONString(value));
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置 list 缓存
     * @param key
     * @param value
     * @param ttl
     */
    public void rightPush(String key, Object value, long ttl) {
        redisTemplate.opsForList().rightPush(key, JSON.toJSONString(value));
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置 set 缓存
     * @param key
     * @param value
     * @param ttl
     */
    public void sadd(String key, Object value, long ttl) {
        redisTemplate.opsForSet().add(key, JSON.toJSONString(value));
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置 zset 缓存
     * @param key
     * @param value
     * @param score
     * @param ttl
     */
    public void zadd(String key, Object value, double score, long ttl) {
        redisTemplate.opsForZSet().add(key, JSON.toJSONString(value), score);
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 查询缓存
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 查询缓存
     * @param key
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = redisTemplate.opsForValue().get(key);
        return JSON.parseObject(value, clazz);
    }

    /**
     * 查询 hash 缓存
     * @param key
     * @param hashKey
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T get(String key, String hashKey, Class<T> clazz) {
        String value = (String) redisTemplate.opsForHash().get(key, hashKey);
        return JSON.parseObject(value, clazz);
    }

    /**
     * list 左出队
     * @param key
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T leftPop(String key, Class<T> clazz) {
        String value = (String) redisTemplate.opsForList().leftPop(key);
        return JSON.parseObject(value, clazz);
    }

    /**
     * list 右出队
     * @param key
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T rightPop(String key, Class<T> clazz) {
        String value = (String) redisTemplate.opsForList().rightPop(key);
        return JSON.parseObject(value, clazz);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    public void del(String key, Object...params) {
        key = RedisKeyConstant.getKey(key, params);
        redisTemplate.delete(key);
    }

    /**
     * 加锁操作
     * @param key
     * @param waitSecond 等待时间，单位秒
     * @param supplier
     * @return
     */
    public <T> T doLock(String key, long waitSecond, Supplier<T> supplier) {
        // key 规范要大写
        key = key.toUpperCase();
        Lock lock = redisLockRegistry.obtain(key);
        try {
            boolean success = lock.tryLock(waitSecond, TimeUnit.SECONDS);
            log.debug("获取锁, key={}, success={}", key, success);
            if (!success) {
                return null;
            }
            Thread.sleep(10*1000);
            T result = supplier.get();
            return result;
        } catch (InterruptedException e) {
            log.error("获取锁失败, key={}, timeout={}", key, waitSecond, e);
        } finally {
            lock.unlock();
        }
        return null;
    }

    public <T> T parseObject(String val, Class<T> clazz) {
        return RedisConstant.PLACEHOLDER.equals(val) ? null : JSON.parseObject(val, clazz);
    }

    public <T> List<T> parseArray(String val, Class<T> clazz) {
        return RedisConstant.PLACEHOLDER.equals(val) ? new ArrayList<>() : JSON.parseArray(val, clazz);
    }

    public String toJson(Object obj) {
        return Objects.isNull(obj) ? RedisConstant.PLACEHOLDER : JSON.toJSONString(obj);
    }
}
