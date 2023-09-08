package com.nib.demanduck.service;

import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.entity.AssignUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.entity.Mission;

import java.util.List;

/**
 * <p>
 * 分配用户表 服务类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
public interface AssignUserService extends IService<AssignUser> {

   void assignDemand(Long demandId, List<Long> userIds);
   void assignMission(Long missionId, List<Long> userIds);
   void assignFlaw(Long flawId, List<Long> userIds);

}
