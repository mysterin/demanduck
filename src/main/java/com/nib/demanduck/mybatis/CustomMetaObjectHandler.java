package com.nib.demanduck.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nib.demanduck.util.ThreadLocalUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/18 18:49
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "deleted", () -> Boolean.FALSE, Boolean.class);
        strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        strictInsertFill(metaObject, "createUser", () -> ThreadLocalUtils.getUserId(), Long.class);
        strictInsertFill(metaObject, "updateUser", () -> ThreadLocalUtils.getUserId(), Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        strictInsertFill(metaObject, "updateUser", () -> ThreadLocalUtils.getUserId(), Long.class);
    }
}
