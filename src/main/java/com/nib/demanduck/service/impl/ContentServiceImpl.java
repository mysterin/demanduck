package com.nib.demanduck.service.impl;

import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.entity.Content;
import com.nib.demanduck.mapper.ContentMapper;
import com.nib.demanduck.service.ContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 内容表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Override
    public void saveContent(Long businessId, EntityType type, String content) {
        // 先查询是否存在
        Content contentEntity = this.lambdaQuery()
                .eq(Content::getBusinessId, businessId)
                .eq(Content::getType, type)
                .one();
        if (Objects.isNull(contentEntity)) {
            contentEntity = new Content();
            contentEntity.setBusinessId(businessId);
            contentEntity.setType(type.name());
            contentEntity.setContent(content);
            this.save(contentEntity);
        } else {
            contentEntity.setContent(content);
            this.updateById(contentEntity);
        }
    }

    @Override
    public Content getByBusinessId(Long businessId, EntityType type) {
        return this.lambdaQuery().eq(Content::getBusinessId, businessId).eq(Content::getType, type).one();
    }
}
