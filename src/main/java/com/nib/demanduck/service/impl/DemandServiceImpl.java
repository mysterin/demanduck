package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.mapper.DemandMapper;
import com.nib.demanduck.service.DemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 需求表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {

}
