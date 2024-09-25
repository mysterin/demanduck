package com.nib.demanduck.service;

import com.nib.demanduck.entity.OperateRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作记录表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2024-09-25
 */
public interface OperateRecordService extends IService<OperateRecord> {

    void saveOperateRecord(Long companyId, Long projectId, Long businessId, String type, Long userId, String content);
}
