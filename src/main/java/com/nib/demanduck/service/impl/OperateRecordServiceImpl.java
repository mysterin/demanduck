package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.OperateRecord;
import com.nib.demanduck.mapper.OperateRecordMapper;
import com.nib.demanduck.service.OperateRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作记录表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2024-09-25
 */
@Service
public class OperateRecordServiceImpl extends ServiceImpl<OperateRecordMapper, OperateRecord> implements OperateRecordService {

    /**
     * 保存操作记录
     * @param companyId
     * @param projectId
     * @param businessId
     * @param type
     * @param userId
     * @param content
     */
    @Override
    public void saveOperateRecord(Long companyId, Long projectId, Long businessId, String type, Long userId, String content) {
        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setCompanyId(companyId);
        operateRecord.setProjectId(projectId);
        operateRecord.setBusinessId(businessId);
        operateRecord.setType(type);
        operateRecord.setUserId(userId);
        operateRecord.setContent(content);
        this.save(operateRecord);
    }
}
