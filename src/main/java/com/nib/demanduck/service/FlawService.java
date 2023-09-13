package com.nib.demanduck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.entity.Flaw;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 缺陷表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface FlawService extends IService<Flaw> {
    void saveFlaw(Flaw flaw);
    IPage<Flaw> listFlawByDemandId(Long demandId, long pageNo, long pageSize);
}
