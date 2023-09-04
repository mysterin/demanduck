package com.nib.demanduck.service;

import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内容表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface ContentService extends IService<Content> {

    void saveContent(Long businessId, EntityType type, String content);
}
