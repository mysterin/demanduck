package com.nib.demanduck.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author linxiaobin
 * @Description 自定义 ID 生成器，目的是处理 js 端 long 类型精度丢失问题，生成的 ID 小于 9007199254740992
 * @date 2023/8/18 18:02
 */
@Component
@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {

    /**
     * 最大 ID 值，这个值是 js 端能够精确表示的最大值
     */
    private static final long MAX_ID_VALUE = 9007199254740991L;
    private final CustomSequence sequence;

    public CustomIdGenerator() {
        this.sequence = new CustomSequence(null);
    }

    @Override
    public Number nextId(Object entity) {
        long id = sequence.nextId();
        if (id > MAX_ID_VALUE) {
            log.error("ID 生成器生成的 ID 超过了 js 端能够精确表示的最大值，id: {}", id);
            throw new RuntimeException("ID 生成器生成的 ID 超过了 js 端能够精确表示的最大值");
        }
        return id;
    }

}
