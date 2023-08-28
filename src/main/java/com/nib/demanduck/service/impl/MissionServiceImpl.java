package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.mapper.MissionMapper;
import com.nib.demanduck.service.MissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

}
